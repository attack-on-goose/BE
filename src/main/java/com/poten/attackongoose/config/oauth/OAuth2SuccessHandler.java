package com.poten.attackongoose.config.oauth;

import com.poten.attackongoose.config.jwt.TokenProvider;
import com.poten.attackongoose.domain.RefreshToken;
import com.poten.attackongoose.domain.user.UserAccount;
import com.poten.attackongoose.repository.RefreshTokenRepository;
import com.poten.attackongoose.service.user.UserAccountService;
import com.poten.attackongoose.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/";
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomAuthorizationRequestRepository authorizationRequestRepository;
    private final UserAccountService userAccountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserAccount userAccount = userAccountService.getUserAccountByEmail((String) oAuth2User.getAttributes().get("email"));

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String oldRefreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());

        String newRefreshToken = tokenProvider.generateToken(userAccount, REFRESH_TOKEN_DURATION);
        saveRefreshToken(userAccount.getEmail(), userAccount.getId(), oldRefreshToken, newRefreshToken);
        addRefreshTokenToCookie(request, response, newRefreshToken);

        String accessToken = tokenProvider.generateToken(userAccount, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void saveRefreshToken(String email, long userAccountId, String oldRefreshToken, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findById(oldRefreshToken)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(RefreshToken.of(email, userAccountId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
}
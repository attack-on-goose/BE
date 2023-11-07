package com.poten.attackongoose.service;

import com.poten.attackongoose.config.oauth.OAuth2Attributes;
import com.poten.attackongoose.config.oauth.userinfo.OAuth2UserInfoProvider;
import com.poten.attackongoose.domain.PrincipalDetails;
import com.poten.attackongoose.domain.UserAccount;
import com.poten.attackongoose.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserAccountRepository userAccountRepository;
    private final OAuth2UserInfoProvider oAuth2UserInfoProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String socialLoginType = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attributes attributes = oAuth2UserInfoProvider
                .getAttributes(socialLoginType, userNameAttributeName, oAuth2User.getAttributes());

        UserAccount userAccount = save(attributes);

        return new PrincipalDetails(userAccount, attributes);
    }

    private UserAccount save(OAuth2Attributes attributes) {
        String email = attributes.getEmail();
        String name = attributes.getName();

        return userAccountRepository.findByEmail(email)
                .orElse(userAccountRepository.save(UserAccount.of(email, name)));

    }
}

package com.poten.attackongoose.service.token;

import com.poten.attackongoose.config.jwt.TokenProvider;
import com.poten.attackongoose.domain.user.UserAccount;
import com.poten.attackongoose.service.user.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;

@Transactional
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final UserAccountService userAccountService;

    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new EntityNotFoundException("Unexpected Token");
        }

        String email = tokenProvider.getSubject(refreshToken);
        UserAccount userAccount = userAccountService.getUserAccountByEmail(email);

        return tokenProvider.generateToken(userAccount, Duration.ofHours(2));
    }
}

package com.poten.attackongoose.controller.token;

import com.poten.attackongoose.dto.request.TokenRequest;
import com.poten.attackongoose.dto.response.TokenResponse;
import com.poten.attackongoose.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<TokenResponse> createAccessToken(@RequestBody TokenRequest tokenRequest) {
        String requestToken = tokenRequest.getRequestToken();
        String newAccessToken = tokenService.createNewAccessToken(requestToken);
        TokenResponse tokenResponse = TokenResponse.of(newAccessToken);

        return ResponseEntity.ok()
                .body(tokenResponse);
    }
}

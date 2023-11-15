package com.poten.attackongoose.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResponse {
    private String accessToken;

    public static TokenResponse of(String accessToken) {
        return new TokenResponse(accessToken);
    }
}

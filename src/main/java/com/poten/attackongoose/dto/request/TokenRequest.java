package com.poten.attackongoose.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRequest {
    private String requestToken;

    public static TokenRequest of(String requestToken) {
        return new TokenRequest(requestToken);
    }
}

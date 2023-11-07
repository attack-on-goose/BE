package com.poten.attackongoose.config.oauth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2Attributes {

    private String name;
    private String email;
    private String provider;
    private String providerId;
    private Map<String, Object> attributes;

    public static OAuth2Attributes of(String name, String email, String provider, String providerId, Map<String, Object> attributes) {
        return new OAuth2Attributes(name, email, provider, providerId, attributes);
    }
}

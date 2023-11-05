package com.poten.attackongoose.config.oauth.userinfo.impl;

import com.poten.attackongoose.config.oauth.OAuth2Attributes;
import com.poten.attackongoose.config.oauth.userinfo.OAuth2UserInfo;
import com.poten.attackongoose.constant.SocialLoginType;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {
    @Override
    public String getProvider() {
        return SocialLoginType.NAVER.name();
    }

    @Override
    public OAuth2Attributes getAttributes(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> responseAttribute = (Map<String, Object>) attributes.get("response");

        return OAuth2Attributes.of(
                (String) responseAttribute.get("name"),
                (String) responseAttribute.get("email"),
                SocialLoginType.NAVER.name(),
                (String) responseAttribute.get("id"),
                responseAttribute
        );
    }
}

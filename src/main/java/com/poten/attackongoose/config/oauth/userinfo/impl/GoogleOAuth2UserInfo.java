package com.poten.attackongoose.config.oauth.userinfo.impl;

import com.poten.attackongoose.config.oauth.OAuth2Attributes;
import com.poten.attackongoose.config.oauth.userinfo.OAuth2UserInfo;
import com.poten.attackongoose.constant.SocialLoginType;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {
    @Override
    public String getProvider() {
        return SocialLoginType.GOOGLE.name();
    }

    @Override
    public OAuth2Attributes getAttributes(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.of(
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                SocialLoginType.GOOGLE.name(),
                (String) attributes.get("sub"),
                attributes
        );
    }
}

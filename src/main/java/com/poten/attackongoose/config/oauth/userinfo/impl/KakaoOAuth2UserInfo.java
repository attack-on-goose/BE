package com.poten.attackongoose.config.oauth.userinfo.impl;

import com.poten.attackongoose.config.oauth.OAuth2Attributes;
import com.poten.attackongoose.config.oauth.userinfo.OAuth2UserInfo;
import com.poten.attackongoose.constant.SocialLoginType;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo  {
    @Override
    public String getProvider() {
        return SocialLoginType.KAKAO.name();
    }

    @Override
    public OAuth2Attributes getAttributes(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccountAttribute = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profileAttribute = (Map<String, Object>) kakaoAccountAttribute.get("profile");

        return OAuth2Attributes.of(
                (String) profileAttribute.get("nickname"),
                (String) kakaoAccountAttribute.get("email"),
                SocialLoginType.KAKAO.name(),
                (String) attributes.get("id"),
                attributes
        );
    }
}

package com.poten.attackongoose.config.oauth.userinfo;

import com.poten.attackongoose.config.oauth.OAuth2Attributes;

import java.util.Map;

public interface OAuth2UserInfo {
    public String getProvider();
    public OAuth2Attributes getAttributes(String userNameAttributeName, Map<String, Object> attributes);
}

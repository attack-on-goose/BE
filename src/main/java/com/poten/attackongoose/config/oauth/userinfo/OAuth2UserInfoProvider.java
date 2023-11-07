package com.poten.attackongoose.config.oauth.userinfo;

import com.poten.attackongoose.config.oauth.OAuth2Attributes;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuth2UserInfoProvider {

    private final Map<String, OAuth2UserInfo> oAuth2UserInfoMap;

    public OAuth2UserInfoProvider(Set<OAuth2UserInfo> providers) {
        this.oAuth2UserInfoMap = providers.stream()
                .collect(Collectors.toMap(OAuth2UserInfo::getProvider, Function.identity()));
    }

    public OAuth2UserInfo getOAuth2Info(String socialLoginType) {
        return Optional.ofNullable(oAuth2UserInfoMap.get(socialLoginType))
                .orElseThrow(() -> new EntityNotFoundException("지원하지 않는 소셜 로그인 타입입니다."));
    }

    public OAuth2Attributes getAttributes(String socialLoginType, String userNameAttributeName, Map<String, Object> attributes) {
        return getOAuth2Info(socialLoginType.toUpperCase(Locale.ENGLISH)).getAttributes(userNameAttributeName, attributes);
    }
}

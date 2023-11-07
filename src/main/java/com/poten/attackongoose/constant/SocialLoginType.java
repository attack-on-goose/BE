package com.poten.attackongoose.constant;

import java.util.Locale;

public enum SocialLoginType {
    GOOGLE,
    NAVER,
    KAKAO;

    public static SocialLoginType fromName(String type) {
        return SocialLoginType.valueOf(type.toUpperCase(Locale.ENGLISH));
    }
}

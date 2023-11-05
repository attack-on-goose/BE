package com.poten.attackongoose.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(timeToLive = 30)
public class RefreshToken {

    @Id
    private String email;
    private long userAccountId;
    private String refreshToken;

    public RefreshToken update(String newRefreshToken) {
        refreshToken = newRefreshToken;
        return this;
    }

    public static RefreshToken of(String email, long userAccountId, String refreshToken) {
        return new RefreshToken(email, userAccountId, refreshToken);
    }

    private RefreshToken(String email, long userAccountId, String refreshToken) {
        this.email = email;
        this.userAccountId = userAccountId;
        this.refreshToken = refreshToken;
    }
}

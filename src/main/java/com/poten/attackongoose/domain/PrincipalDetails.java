package com.poten.attackongoose.domain;

import com.poten.attackongoose.config.oauth.OAuth2Attributes;
import com.poten.attackongoose.domain.user.UserAccount;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class PrincipalDetails implements OAuth2User, UserDetails {

    private final UserAccount userAccount;
    private OAuth2Attributes oAuth2Attributes;

    public PrincipalDetails(UserAccount userAccount, OAuth2Attributes oAuth2Attributes) {
        this.userAccount = userAccount;
        this.oAuth2Attributes = oAuth2Attributes;
    }

    public PrincipalDetails(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getName() {
        return userAccount.getEmail();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userAccount.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.poten.attackongoose.service;

import com.poten.attackongoose.domain.PrincipalDetails;
import com.poten.attackongoose.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserAccountService userAccountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountService.getUserAccountByEmail(email);

        return new PrincipalDetails(userAccount);
    }
}

package com.poten.attackongoose.service;

import com.poten.attackongoose.domain.UserAccount;
import com.poten.attackongoose.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public UserAccount getUserAccountById(Long id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다 - id: " + id));
    }

    @Transactional(readOnly = true)
    public UserAccount getUserAccountByEmail(String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다 - email: " + email));
    }
}

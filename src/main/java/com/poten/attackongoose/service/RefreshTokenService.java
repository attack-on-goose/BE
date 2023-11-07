package com.poten.attackongoose.service;

import com.poten.attackongoose.domain.RefreshToken;
import com.poten.attackongoose.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public RefreshToken getRefreshToken(String email) {
        return refreshTokenRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("Refresh Token을 찾을 수 없습니다."));
    }
}

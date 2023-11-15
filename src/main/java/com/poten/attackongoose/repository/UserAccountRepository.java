package com.poten.attackongoose.repository;

import com.poten.attackongoose.domain.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findById(Long id);
    Optional<UserAccount> findByEmail(String email);
}

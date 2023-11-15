package com.poten.attackongoose.controller.user;

import com.poten.attackongoose.domain.user.UserAccount;
import com.poten.attackongoose.repository.UserAccountRepository;
import com.poten.attackongoose.service.user.UserAccountService;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserAccountService userAccountService;
    private UserAccountRepository userAccountRepository;

    //토큰을 받고, 토큰의 정합성 검증, 정합 시, data 호출

    //현재 Validate 확인을 위해서는 TokenProvider에 접근해야만 해서 의존성이 높아 보임

    @PatchMapping("/api/users={userId}")
    public ResponseEntity<UserAccount> getUserInfo(
        @PathVariable long userId, @RequestBody String changeNickName) {

        UserAccount user = userAccountRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다 - id: " + userId));
        user.setNickName(changeNickName);
        userAccountRepository.save(user);

        return ResponseEntity.ok().body(user);
    }
}

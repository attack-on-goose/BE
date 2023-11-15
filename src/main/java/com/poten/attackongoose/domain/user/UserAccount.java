package com.poten.attackongoose.domain.user;

import com.poten.attackongoose.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
})
public class UserAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DTO에서 userId로 변용 예정

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;
    @Column
    private String nickName;

    public static UserAccount of(String email, String name) {
        return new UserAccount(email, name);
    }
    public UserAccount(String email, String name) {
        this.email = email;
        this.name = name;
    }
}

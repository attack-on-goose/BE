package com.poten.attackongoose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AttackOnGooseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttackOnGooseApplication.class, args);
    }

}

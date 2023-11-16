package com.poten.attackongoose.repository.question;

import com.poten.attackongoose.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestiontRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);
}

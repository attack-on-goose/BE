package com.poten.attackongoose.domain.question;

import com.poten.attackongoose.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Question")
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuestionId", nullable = false)
    private Long questionId;

    @Column(name = "CategoryId", nullable = false)
    private Long categoryId;

    @Column(name = "resultDataId", nullable = false)
    private Long resultDataId;

    @Column(name = "totalresultId", nullable = false)
    private Long totalResultId;

    @Column(name = "Question", nullable = false, length = 255)
    private String question;

    @Column(name = "Question_answer")
    private Long questionAnswer;

    @Column(name = "OpenDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date openDate;

    public static Question of(Long categoryId, Long resultDataId, Long totalResultId, String question, Date openDate) {
        return new Question(categoryId, resultDataId, totalResultId, question, openDate);
    }

    public Question(Long categoryId, Long resultDataId, Long totalResultId, String question, Date openDate) {
        this.categoryId = categoryId;
        this.resultDataId = resultDataId;
        this.totalResultId = totalResultId;
        this.question = question;
        this.openDate = openDate;
    }
}

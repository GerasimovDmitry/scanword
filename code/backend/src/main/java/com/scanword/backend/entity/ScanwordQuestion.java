package com.scanword.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "scanword_question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanwordQuestion {
    @Id
    @Column(name = "scanword_uuid", nullable = false)
    private UUID scanwordUUID;

    @Column(name = "question_uuid", nullable = false)
    private UUID questionUUID;

    @Column(name = "answer_x", nullable = false)
    private Integer answerX;

    @Column(name = "answer_y", nullable = false)
    private Integer answerY;

    @Column(name = "answer_x0", nullable = false)
    private Integer answerX0;

    @Column(name = "answer_y0", nullable = false)
    private Integer answerY0;

    @Column(name = "question_x", nullable = false)
    private Integer questionX;

    @Column(name = "question_y", nullable = false)
    private Integer questionY;

    @Column(name = "orientation", nullable = false)
    private String orientation;
}

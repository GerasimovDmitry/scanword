package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionModel {
    private String text;

    private String answer;

    private String url;

    private String type;

    private UUID scanwordId;

    private UUID questionId;

    private UUID userId;

    private Integer answerX;

    private Integer answerY;

    private Integer answerX0;

    private Integer answerY0;

    private Integer questionX;

    private Integer questionY;

    private String orientation;

    private Boolean isPassed = false;
}

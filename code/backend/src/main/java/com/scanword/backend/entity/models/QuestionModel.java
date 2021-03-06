package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionModel {
    private String text;

    private String answer;

    private String url;

    private String type;

    private UUID id;
}

package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionScanwordModel {
    private String text;

    private String answer;

    private String url;

    private String type;

    private UUID id;

    private String orientation;

    private Boolean isPassed = false;

    private String location;
}

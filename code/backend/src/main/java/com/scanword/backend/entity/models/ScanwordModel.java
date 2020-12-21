package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.List;

@Data
public class ScanwordModel {
    private Integer width;
    private Integer height;
    private String name;
    private List<QuestionModel> grid;
}

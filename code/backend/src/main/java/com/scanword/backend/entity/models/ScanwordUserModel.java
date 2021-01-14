package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ScanwordUserModel {

    private UUID id;
    private Integer width;
    private Integer height;
    private String name;
    private UUID dictionaryId;
    private String dictionaryName;
    private List<QuestionScanwordModel> questions;
    private int countHintsUsed;
}

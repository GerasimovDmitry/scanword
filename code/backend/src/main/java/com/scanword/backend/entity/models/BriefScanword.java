package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.UUID;

@Data
public class BriefScanword {
    private String name;
    private UUID id;
    private String score;
}

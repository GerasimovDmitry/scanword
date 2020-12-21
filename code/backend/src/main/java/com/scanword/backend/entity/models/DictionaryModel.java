package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.UUID;

@Data
public class DictionaryModel {
    private String name;
    private UUID id;
}

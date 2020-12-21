package com.scanword.backend.entity.models;

import lombok.Data;

import java.util.UUID;

@Data
public class UserProfile {
    private String login;
    private Boolean isAdmin = false;
}

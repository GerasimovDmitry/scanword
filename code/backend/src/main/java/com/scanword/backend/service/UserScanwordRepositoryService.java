package com.scanword.backend.service;

import com.scanword.backend.repository.UserScanwordQuestionRepository;
import com.scanword.backend.repository.UserScanwordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserScanwordRepositoryService {
    private UserScanwordRepository repository;

    @Autowired
    public UserScanwordRepositoryService(UserScanwordRepository repository) {
        this.repository = repository;
    }
}

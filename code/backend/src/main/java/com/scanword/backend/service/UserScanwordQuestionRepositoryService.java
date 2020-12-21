package com.scanword.backend.service;

import com.scanword.backend.repository.ScanwordRepository;
import com.scanword.backend.repository.UserScanwordQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserScanwordQuestionRepositoryService {
    private UserScanwordQuestionRepository repository;

    @Autowired
    public UserScanwordQuestionRepositoryService(UserScanwordQuestionRepository repository) {
        this.repository = repository;
    }
}

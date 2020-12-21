package com.scanword.backend.service;

import com.scanword.backend.repository.QuestionRepository;
import com.scanword.backend.repository.ScanwordQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScanwordQuestionRepositoryService {
    private ScanwordQuestionRepository repository;

    @Autowired
    public ScanwordQuestionRepositoryService(ScanwordQuestionRepository repository) {
        this.repository = repository;
    }
}

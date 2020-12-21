package com.scanword.backend.service;

import com.scanword.backend.repository.ScanwordQuestionRepository;
import com.scanword.backend.repository.ScanwordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScanwordRepositoryService {
    private ScanwordRepository repository;

    @Autowired
    public ScanwordRepositoryService(ScanwordRepository repository) {
        this.repository = repository;
    }
}


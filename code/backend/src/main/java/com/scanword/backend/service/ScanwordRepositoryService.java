package com.scanword.backend.service;

import com.scanword.backend.config.Constants;
import com.scanword.backend.entity.Scanword;
import com.scanword.backend.entity.models.ScanwordModel;
import com.scanword.backend.repository.ScanwordQuestionRepository;
import com.scanword.backend.repository.ScanwordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ScanwordRepositoryService {
    private ScanwordRepository repository;
    private QuestionRepositoryService questionRepositoryService;
    private ScanwordQuestionRepositoryService scanwordQuestionRepositoryService;
    private UserScanwordQuestionRepositoryService userScanwordQuestionRepositoryService;

    @Autowired
    public ScanwordRepositoryService(ScanwordRepository repository,
                                     @Lazy QuestionRepositoryService questionRepositoryService,
                                     @Lazy ScanwordQuestionRepositoryService scanwordQuestionRepositoryService,
                                     @Lazy UserScanwordQuestionRepositoryService userScanwordQuestionRepositoryService) {
        this.repository = repository;
        this.questionRepositoryService = questionRepositoryService;
        this.scanwordQuestionRepositoryService = scanwordQuestionRepositoryService;
        this.userScanwordQuestionRepositoryService = userScanwordQuestionRepositoryService;
    }

    public void checkName(String name)throws Exception {
        if (repository.getScanwordByName(name) != null) {
            throw new Exception();
        }
    }

    public void saveScanwordByAdmin(ScanwordModel scanword) {
        scanword.setId(UUID.randomUUID());
        UUID userId = Constants.userId;
        Scanword sc = new Scanword();
        sc.setHeight(scanword.getHeight());
        sc.setWidth(scanword.getWidth());
        sc.setDictionaryUUID(scanword.getDictionaryId());
        sc.setUuid(scanword.getId());
        sc.setName(scanword.getName());
        repository.saveAndFlush(sc);

        questionRepositoryService.saveQuestions(scanword.getQuestions());
        userScanwordQuestionRepositoryService.saveQuestions(scanword.getId(), userId, scanword.getQuestions());
        scanwordQuestionRepositoryService.saveQuestions(scanword);
    }
}


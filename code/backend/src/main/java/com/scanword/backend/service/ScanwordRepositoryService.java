package com.scanword.backend.service;

import com.scanword.backend.config.Constants;
import com.scanword.backend.entity.Scanword;
import com.scanword.backend.entity.models.BriefScanword;
import com.scanword.backend.entity.models.ScanwordModel;
import com.scanword.backend.repository.ScanwordQuestionRepository;
import com.scanword.backend.repository.ScanwordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ScanwordRepositoryService {
    private ScanwordRepository repository;
    private QuestionRepositoryService questionRepositoryService;
    private ScanwordQuestionRepositoryService scanwordQuestionRepositoryService;
    private UserScanwordQuestionRepositoryService userScanwordQuestionRepositoryService;
    private UserScanwordRepositoryService userScanwordRepositoryService;

    @Autowired
    public ScanwordRepositoryService(ScanwordRepository repository,
                                     @Lazy QuestionRepositoryService questionRepositoryService,
                                     @Lazy ScanwordQuestionRepositoryService scanwordQuestionRepositoryService,
                                     @Lazy UserScanwordQuestionRepositoryService userScanwordQuestionRepositoryService,
                                     @Lazy UserScanwordRepositoryService userScanwordRepositoryService) {
        this.repository = repository;
        this.questionRepositoryService = questionRepositoryService;
        this.scanwordQuestionRepositoryService = scanwordQuestionRepositoryService;
        this.userScanwordQuestionRepositoryService = userScanwordQuestionRepositoryService;
        this.userScanwordRepositoryService = userScanwordRepositoryService;
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
        userScanwordRepositoryService.saveUserScanword(userId, scanword);
    }

    public List<BriefScanword> getBriefScanwords() {
        List<Scanword> scanwords = repository.findAll();
        List<BriefScanword> result = new ArrayList<>();
        for (Scanword s : scanwords) {
            BriefScanword bs = new BriefScanword();
            bs.setId(s.getUuid());
            bs.setName(s.getName());
            //Текущий прогресс: 20/100 20%
            String score = "Текущий прогресс: ";

            int currentScore = userScanwordRepositoryService.getUserScanwordByUserIdAndScanwordId(Constants.userId, s.getUuid()).getScore();
            int maxScore = userScanwordQuestionRepositoryService.getCountOfQuestionsByScanwordUUID(s.getUuid());
            score = score + currentScore;
            score = score + "/";
            score = score + maxScore;
            score = score + " " + Math.round((double)currentScore/(double)maxScore) + "%";
            bs.setScore(score);
            result.add(bs);
        }
        return result;
    }
}


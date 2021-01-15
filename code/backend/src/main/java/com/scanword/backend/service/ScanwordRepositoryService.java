package com.scanword.backend.service;

import com.scanword.backend.config.Constants;
import com.scanword.backend.entity.*;
import com.scanword.backend.entity.models.*;
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
    private DictionaryRepositoryService dictionaryRepositoryService;

    @Autowired
    public ScanwordRepositoryService(ScanwordRepository repository,
                                     @Lazy QuestionRepositoryService questionRepositoryService,
                                     @Lazy ScanwordQuestionRepositoryService scanwordQuestionRepositoryService,
                                     @Lazy UserScanwordQuestionRepositoryService userScanwordQuestionRepositoryService,
                                     @Lazy UserScanwordRepositoryService userScanwordRepositoryService,
                                     @Lazy DictionaryRepositoryService dictionaryRepositoryService) {
        this.repository = repository;
        this.questionRepositoryService = questionRepositoryService;
        this.scanwordQuestionRepositoryService = scanwordQuestionRepositoryService;
        this.userScanwordQuestionRepositoryService = userScanwordQuestionRepositoryService;
        this.userScanwordRepositoryService = userScanwordRepositoryService;
        this.dictionaryRepositoryService = dictionaryRepositoryService;
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

    public ScanwordUserModel getScanwordForUser(UUID scanwordId) {
        ScanwordUserModel scanwordUserModel = new ScanwordUserModel();
        List<QuestionScanwordModel> questionScanwordModels = new ArrayList<>();
        Scanword scanword = repository.findByUuid(scanwordId);

        scanwordUserModel.setId(scanwordId);
        scanwordUserModel.setDictionaryId(scanword.getDictionaryUUID());
        scanwordUserModel.setHeight(scanword.getHeight());
        scanwordUserModel.setWidth(scanword.getWidth());
        scanwordUserModel.setName(scanword.getName());
        scanwordUserModel.setDictionaryName(dictionaryRepositoryService.getDictionaryById(scanword.getDictionaryUUID()).getName());

        UserScanword userScanword = userScanwordRepositoryService.getUserScanwordByUserIdAndScanwordId(Constants.userId, scanwordId);
        scanwordUserModel.setCountHintsUsed(userScanword.getCountHintsUsed());

        List<UserScanwordQuestion> userScanwordQuestions = userScanwordQuestionRepositoryService.getEntityByIds(Constants.userId, scanwordId);
        for (UserScanwordQuestion q: userScanwordQuestions) {
            QuestionScanwordModel m = new QuestionScanwordModel();
            m.setId(q.getQuestionUUID());
            m.setIsPassed(q.getIsPassed());
            ScanwordQuestion sq = scanwordQuestionRepositoryService.getScanwordQuestionByQuestionId(q.getQuestionUUID());
            m.setLocation(sq.getLocation());
            m.setOrientation(sq.getOrientation());
            QuestionModel question = questionRepositoryService.getQuestionForScanword(q.getQuestionUUID());
            m.setAnswer(question.getAnswer());
            m.setType(question.getType());
            m.setUrl(question.getUrl());
            m.setText(question.getText());
            questionScanwordModels.add(m);
        }
        scanwordUserModel.setQuestions(questionScanwordModels);

        return scanwordUserModel;
    }

    public ScanwordModel getScanwordForAdmin(UUID scanwordId) {
        ScanwordModel scanwordModel = new ScanwordModel();
        List<QuestionScanwordModel> questionScanwordModels = new ArrayList<>();
        Scanword scanword = repository.findByUuid(scanwordId);

        scanwordModel.setId(scanwordId);
        scanwordModel.setDictionaryId(scanword.getDictionaryUUID());
        scanwordModel.setHeight(scanword.getHeight());
        scanwordModel.setWidth(scanword.getWidth());
        scanwordModel.setName(scanword.getName());
        scanwordModel.setDictionaryName(dictionaryRepositoryService.getDictionaryById(scanword.getDictionaryUUID()).getName());

        List<UserScanwordQuestion> userScanwordQuestions = userScanwordQuestionRepositoryService.getEntityByIds(Constants.userId, scanwordId);
        for (UserScanwordQuestion q: userScanwordQuestions) {
            QuestionScanwordModel m = new QuestionScanwordModel();
            m.setId(q.getQuestionUUID());
            m.setIsPassed(q.getIsPassed());
            ScanwordQuestion sq = scanwordQuestionRepositoryService.getScanwordQuestionByQuestionId(q.getQuestionUUID());
            m.setLocation(sq.getLocation());
            m.setOrientation(sq.getOrientation());
            QuestionModel question = questionRepositoryService.getQuestionForScanword(q.getQuestionUUID());
            m.setAnswer(question.getAnswer());
            m.setType(question.getType());
            m.setUrl(question.getUrl());
            m.setText(question.getText());
            questionScanwordModels.add(m);
        }
        scanwordModel.setQuestions(questionScanwordModels);

        return scanwordModel;
    }
}


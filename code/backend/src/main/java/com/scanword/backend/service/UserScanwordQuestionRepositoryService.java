package com.scanword.backend.service;

import com.scanword.backend.entity.Question;
import com.scanword.backend.entity.UserScanwordQuestion;
import com.scanword.backend.entity.models.QuestionScanwordModel;
import com.scanword.backend.repository.ScanwordRepository;
import com.scanword.backend.repository.UserScanwordQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserScanwordQuestionRepositoryService {
    private UserScanwordQuestionRepository repository;
    private QuestionRepositoryService questionRepositoryService;

    @Autowired
    public UserScanwordQuestionRepositoryService(UserScanwordQuestionRepository repository,
                                                 @Lazy QuestionRepositoryService questionRepositoryService) {
        this.repository = repository;
        this.questionRepositoryService = questionRepositoryService;
    }

    public void saveQuestions(UUID id, UUID userId, List<QuestionScanwordModel> questions) {
        List<UserScanwordQuestion> saveList = new ArrayList<>();
        for (QuestionScanwordModel q : questions) {
            UserScanwordQuestion userScanwordQuestion = new UserScanwordQuestion();
/*            if (repository.findByQuestionUUID(q.getId()) != null) {
                Question cloneQuestion = new Question();
                cloneQuestion.setUrl(q.getUrl());
                cloneQuestion.setText(q.getText());
                cloneQuestion.setAnswer(q.getAnswer());
                cloneQuestion.setType(q.getType());
                cloneQuestion.setUuid(UUID.randomUUID());
                questionRepositoryService.saveQuestion(cloneQuestion);
            }*/
            userScanwordQuestion.setQuestionUUID(q.getId());
            userScanwordQuestion.setUserUUID(userId);
            userScanwordQuestion.setIsPassed(q.getIsPassed());
            userScanwordQuestion.setScanwordUUID(id);
            saveList.add(userScanwordQuestion);
        }
        repository.saveAll(saveList);
    }

    public boolean isExist(UUID id) {
        return repository.findByQuestionUUID(id) == null;
    }
}

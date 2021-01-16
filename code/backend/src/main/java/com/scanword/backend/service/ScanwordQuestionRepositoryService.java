package com.scanword.backend.service;

import com.scanword.backend.entity.ScanwordQuestion;
import com.scanword.backend.entity.models.QuestionScanwordModel;
import com.scanword.backend.entity.models.ScanwordModel;
import com.scanword.backend.entity.models.ScanwordUserModel;
import com.scanword.backend.repository.QuestionRepository;
import com.scanword.backend.repository.ScanwordQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ScanwordQuestionRepositoryService {
    private ScanwordQuestionRepository repository;

    @Autowired
    public ScanwordQuestionRepositoryService(ScanwordQuestionRepository repository) {
        this.repository = repository;
    }

    public void saveQuestions(ScanwordModel scanword) {
        List<ScanwordQuestion> saveList = new ArrayList<>();
        List<QuestionScanwordModel> questions = scanword.getQuestions();

        for (QuestionScanwordModel q : questions) {
            ScanwordQuestion question = new ScanwordQuestion();
            question.setLocation(q.getLocation());
            question.setOrientation(q.getOrientation());
            question.setQuestionUUID(q.getId());
            question.setScanwordUUID(scanword.getId());
            saveList.add(question);
        }

        repository.saveAll(saveList);
    }

    public void updateQuestions(ScanwordUserModel scanword) {
        List<ScanwordQuestion> saveList = new ArrayList<>();
        List<QuestionScanwordModel> questions = scanword.getQuestions();

        for (QuestionScanwordModel q : questions) {
            ScanwordQuestion question = new ScanwordQuestion();
            question.setLocation(q.getLocation());
            question.setOrientation(q.getOrientation());
            question.setQuestionUUID(q.getId());
            question.setScanwordUUID(scanword.getId());
            saveList.add(question);
        }

        repository.saveAll(saveList);
    }

    public ScanwordQuestion getScanwordQuestionByQuestionId(UUID id) {
        return repository.findByQuestionUUID(id);
    }
}

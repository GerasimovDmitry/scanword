package com.scanword.backend.service;

import com.scanword.backend.entity.Question;
import com.scanword.backend.repository.MediaRepository;
import com.scanword.backend.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionRepositoryService {
    private QuestionRepository repository;

    @Autowired
    public QuestionRepositoryService(QuestionRepository repository) {
        this.repository = repository;
    }
}

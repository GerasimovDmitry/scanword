package com.scanword.backend.service;

import com.scanword.backend.entity.Question;
import com.scanword.backend.repository.MediaRepository;
import com.scanword.backend.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class QuestionRepositoryService {
    private QuestionRepository repository;

    @Autowired
    public QuestionRepositoryService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<Question> getAll() {
        return repository.findAll();
    }

    public Question getQuestion(UUID id) {
        return repository.findByUUID(id);
    }

    public Question createQuestion(Question question) {
        return repository.saveAndFlush(question);
    }

    public void deleteQuestion(Question question) {
        repository.delete(question);
    }

    public Question editQuestion(Question question) {
        return repository.saveAndFlush(question);
    }
}

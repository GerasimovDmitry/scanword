package com.scanword.backend.controller;

import com.scanword.backend.entity.Question;
import com.scanword.backend.entity.models.QuestionModel;
import com.scanword.backend.service.QuestionRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/question")
public class QuestionController {
    private QuestionRepositoryService questionRepositoryService;

    QuestionController(QuestionRepositoryService questionRepositoryService) {
        this.questionRepositoryService = questionRepositoryService;
    }

    @GetMapping("/all")
    public List<Question> getQuestions() {
        return questionRepositoryService.getAll();
    }

    @PostMapping()
    public Question getQuestion(@RequestParam UUID questionId) {
        return questionRepositoryService.getQuestion(questionId);
    }

    @PutMapping("/edit")
    public Question editQuestion(@RequestBody Question question) {
        return questionRepositoryService.editQuestion(question);
    }

    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question) {
        return questionRepositoryService.createQuestion(question);
    }

    @DeleteMapping("/delete")
    public void deleteQuestion(@RequestBody Question question) {
        questionRepositoryService.deleteQuestion(question);
    }
}

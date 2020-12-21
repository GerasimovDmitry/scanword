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

    QuestionController(QuestionRepositoryService questionRepositoryService1) {

        this.questionRepositoryService = questionRepositoryService1;
    }

    @PostMapping("/hint")
    public String getHint(@RequestParam UUID questionId) {
        return null;
    }

    @GetMapping("/all")
    public List<Question> getQuestions() {
        return null;
    }

    @PostMapping("/all/dictionary")
    public List<Question> getQuestionsByScanword(@RequestParam UUID dictionaryId) {
        return null;
    }

    @PostMapping("/delete")
    public void removeMediaQuestion(@RequestParam UUID questionId) {
    }

    @PostMapping("/edit")
    public Question editMediaQuestion(@RequestParam UUID questionId) {
        return null;
    }
}

package com.scanword.backend.controller;

import com.scanword.backend.entity.models.QuestionModel;
import com.scanword.backend.service.QuestionRepositoryService;
import com.scanword.backend.service.ScanwordQuestionRepositoryService;
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
    private ScanwordQuestionRepositoryService scanwordQuestionRepositoryService;

    QuestionController(QuestionRepositoryService questionRepositoryService) {
        this.questionRepositoryService = questionRepositoryService;
    }


    //For tab Questions
    @GetMapping("/all")
    public List<QuestionModel> getQuestions() {
        return questionRepositoryService.getAll();
    }

    //For modal in create scanword tab
    @GetMapping("/all/modal")
    public List<QuestionModel> getQuestionsForModal(@RequestParam UUID dictionaryId) {
        return questionRepositoryService.getQuestionsForModal(dictionaryId);
    }

    @PostMapping()
    public QuestionModel getQuestion(@RequestParam UUID questionId) {
        return questionRepositoryService.getQuestion(questionId);
    }

    @PutMapping("/edit")
    public QuestionModel editQuestion(@RequestBody QuestionModel question) {
        return questionRepositoryService.editQuestion(question);
    }

    @PostMapping("/create")
    public QuestionModel createQuestion(@RequestBody QuestionModel question) {
        return questionRepositoryService.createQuestion(question);
    }

    @DeleteMapping("/delete")
    public void deleteQuestion(@RequestBody QuestionModel question) {
        questionRepositoryService.deleteQuestion(question);
    }

}

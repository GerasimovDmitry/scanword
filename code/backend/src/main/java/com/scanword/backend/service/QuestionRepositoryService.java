package com.scanword.backend.service;

import com.scanword.backend.entity.Question;
import com.scanword.backend.entity.enums.ExtensionEnum;
import com.scanword.backend.entity.models.QuestionModel;
import com.scanword.backend.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class QuestionRepositoryService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionRepositoryService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionModel> getAll() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionModel> questionModels = new ArrayList<QuestionModel>();
        QuestionModel model = new QuestionModel();
        for (Question question : questions) {
            model.setQuestionId(question.getUuid());
            model.setText(question.getText());
            model.setType(getQuestionType(question.getUrl()));
            model.setAnswer(question.getAnswer());
        }
        return questionModels;
    }

    private String getQuestionType(String url) {
        String extension = ExtensionEnum.getExtension(url);
        return url == null ? "text" : (ExtensionEnum.isPic(extension) ? "picture" : "sound");
    }

    public QuestionModel getQuestion(UUID id) {
        Question question = questionRepository.findByUUID(id);
        QuestionModel model = new QuestionModel();
        model.setQuestionId(question.getUuid());
        model.setText(question.getText());
        model.setType(getQuestionType(question.getUrl()));
        return model;
    }

    public QuestionModel createQuestion(QuestionModel questionModel) {
        Question question = new Question();
        question.setText(questionModel.getText());
        question.setAnswer(questionModel.getAnswer());
        question.setUuid(questionModel.getQuestionId());
        question.setUrl(questionModel.getUrl());
        questionRepository.saveAndFlush(question);
        return questionModel; //?
    }

    public void deleteQuestion(QuestionModel questionModel) {
        Question question = new Question();
        question.setText(questionModel.getText());
        question.setAnswer(questionModel.getAnswer());
        question.setUuid(questionModel.getQuestionId());
        question.setUrl(questionModel.getUrl());
        questionRepository.delete(question);
    }

    public QuestionModel editQuestion(QuestionModel questionModel) {
        Question question = questionRepository.findByUUID(questionModel.getQuestionId());
        question.setUrl(questionModel.getUrl());
        question.setAnswer(questionModel.getAnswer());
        question.setText(questionModel.getText());
        questionRepository.saveAndFlush(question);
        return questionModel; //?
    }
}

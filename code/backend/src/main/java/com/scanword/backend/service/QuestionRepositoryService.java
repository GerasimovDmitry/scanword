package com.scanword.backend.service;

import com.scanword.backend.entity.Question;
import com.scanword.backend.entity.enums.ExtensionEnum;
import com.scanword.backend.entity.models.DictionaryItem;
import com.scanword.backend.entity.models.QuestionModel;
import com.scanword.backend.entity.models.QuestionScanwordModel;
import com.scanword.backend.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class QuestionRepositoryService {
    private QuestionRepository questionRepository;
    private DictionaryRepositoryService dictionaryRepositoryService;
    private UserScanwordQuestionRepositoryService userScanwordQuestionRepositoryService;

    @Autowired
    public QuestionRepositoryService(QuestionRepository questionRepository,
                                     @Lazy DictionaryRepositoryService dictionaryRepositoryService,
                                     @Lazy UserScanwordQuestionRepositoryService userScanwordQuestionRepositoryService) {
        this.questionRepository = questionRepository;
        this.dictionaryRepositoryService = dictionaryRepositoryService;
        this.userScanwordQuestionRepositoryService = userScanwordQuestionRepositoryService;
    }

    //For Questions tab
    public List<QuestionModel> getAll() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionModel> questionModels = new ArrayList<QuestionModel>();
        for (Question question : questions) {
            QuestionModel model = new QuestionModel();
            model.setId(question.getUuid());
            model.setText(question.getText());
            model.setType(getQuestionType(question.getUrl()));
            model.setAnswer(question.getAnswer());
            model.setUrl(question.getUrl());
            if (!model.getType().equals("text")) {
                questionModels.add(model);
            }
        }
        return questionModels;
    }

    public String getQuestionType(String url) {
        if (url == null) {
            return "text";
        }
        String extension = ExtensionEnum.getExtension(url);
        return url == null ? "text" : (ExtensionEnum.isPic(extension) ? "image" : "sound");
    }

    public QuestionModel getQuestionModelById(UUID id) {
        Question question = questionRepository.findByUUID(id);
        QuestionModel model = new QuestionModel();
        model.setId(question.getUuid());
        model.setText(question.getText());
        model.setType(getQuestionType(question.getUrl()));
        return model;
    }

    public Question getQuestionById(UUID id) {
        return questionRepository.findByUUID(id);
    }

    public QuestionModel getQuestionForScanword(UUID id) {
        Question question = questionRepository.findByUUID(id);
        QuestionModel model = new QuestionModel();
        model.setId(question.getUuid());
        model.setText(question.getText());
        model.setType(getQuestionType(question.getUrl()));
        model.setAnswer(question.getAnswer());
        model.setUrl(question.getUrl());
        return model;
    }

    public QuestionModel createQuestion(QuestionModel questionModel) {
        Question question = new Question();
        question.setText(questionModel.getText());
        question.setAnswer(questionModel.getAnswer());
        question.setUuid(questionModel.getId());
        question.setUrl(questionModel.getUrl());
        question.setType(getQuestionType(questionModel.getUrl()));
        question.setUuid(UUID.randomUUID());
        questionRepository.saveAndFlush(question);
        return questionModel;
    }

    public void deleteQuestion(QuestionModel questionModel) {
        Question question = new Question();
        question.setText(questionModel.getText());
        question.setAnswer(questionModel.getAnswer());
        question.setUuid(questionModel.getId());
        question.setUrl(questionModel.getUrl());
        questionRepository.delete(question);
    }

    public QuestionModel editQuestion(QuestionModel questionModel) {
        Question question = questionRepository.findByUUID(questionModel.getId());
        question.setUrl(questionModel.getUrl());
        question.setAnswer(questionModel.getAnswer());
        question.setText(questionModel.getText());
        questionRepository.saveAndFlush(question);
        return questionModel;
    }

    public List<QuestionModel> getQuestionsForModal(UUID dictionaryId) {
        List<QuestionModel> questionModels = getAll();
        /*  for (Question question : questions) {
            QuestionModel model = new QuestionModel();
            model.setId(question.getUuid());
            model.setText(question.getText());
            model.setType(getQuestionType(question.getUrl()));
            model.setAnswer(question.getAnswer());
            model.setUrl(question.getUrl());
            if (!model.getType().equals("text")) {
                questionModels.add(model);
            }
        }*/
        List<QuestionModel> repeatable = new ArrayList<>();
        for (QuestionModel q : questionModels) {
            System.out.println(q.getId());
            if (!userScanwordQuestionRepositoryService.isExist(q.getId())) {
                repeatable.add(q);
            }
        }
        questionModels.removeAll(repeatable);

        List<QuestionModel> dictQuestions = createQuestionsFromDictionary(dictionaryId);
        questionModels.addAll(dictQuestions);
        return questionModels;
    }

    private List<QuestionModel> createQuestionsFromDictionary(UUID dictionaryId) {
        List<DictionaryItem> items = this.dictionaryRepositoryService.getItemsById(dictionaryId);
        List<QuestionModel> models = new ArrayList<>();
        for (DictionaryItem item : items) {
            QuestionModel model = new QuestionModel();
            model.setId(UUID.randomUUID());
            model.setAnswer(item.getAnswer());
            model.setText(item.getText());
            model.setType("text");
            model.setUrl(null);
            models.add(model);
        }
        return models;
    }

    public void saveQuestions(List<QuestionScanwordModel> questions) {
        List<Question> savedQuestions = new ArrayList<>();
        List<QuestionModel> currentQuestions = getAll();
        for (QuestionScanwordModel question : questions) {
            Question q = new Question();
            if (questionRepository.findByUUID(question.getId()) == null) {
                q.setType(question.getType());
                q.setUuid(question.getId());
                q.setAnswer(question.getAnswer());
                q.setText(question.getText());
                q.setUrl(question.getUrl());
                savedQuestions.add(q);
            }
        }
        questionRepository.saveAll(savedQuestions);
    }

    public void saveQuestion(Question cloneQuestion) {
        questionRepository.saveAndFlush(cloneQuestion);
    }

    public void deleteQuestionById(UUID questionId) {
        this.questionRepository.deleteById(questionId);
    }
}

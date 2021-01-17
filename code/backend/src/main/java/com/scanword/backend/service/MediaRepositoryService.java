package com.scanword.backend.service;

import com.scanword.backend.entity.Media;
import com.scanword.backend.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MediaRepositoryService {
    private MediaRepository repository;
    private QuestionRepositoryService questionRepositoryService;

    @Autowired
    public MediaRepositoryService(MediaRepository repository, @Lazy QuestionRepositoryService questionRepositoryService) {
        this.repository = repository;
        this.questionRepositoryService = questionRepositoryService;
    }

    public List<Media> getAllImages () {
        return  repository.getAllImages();
    }

    public List<Media> getAllSounds () {
        return  repository.getAllSounds();
    }

    public void saveFile (Media media) {
        repository.saveAndFlush(media);
    }

    public void deleteFileByName (String name) {
        questionRepositoryService.deleteQuestionByUrl(name);
        repository.deleteMediaByUrl(name);
    }
    public List<Media> getFileByName (String name) {
        return repository.getMediaByUrl(name);
    }
}

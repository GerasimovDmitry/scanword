package com.scanword.backend.controller;

import com.scanword.backend.entity.Dictionary;
import com.scanword.backend.entity.enums.ExtensionEnum;
import com.scanword.backend.entity.models.DictionaryItem;
import com.scanword.backend.entity.models.DictionaryModel;
import com.scanword.backend.service.DictionaryRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/dictionary")
public class DictionaryController {
    private DictionaryRepositoryService dictionaryRepositoryService;

    public DictionaryController(DictionaryRepositoryService dictionaryRepositoryService) {
        this.dictionaryRepositoryService = dictionaryRepositoryService;
    }

    @GetMapping("/all")
    public List<DictionaryModel> getDictionaries() {
        return dictionaryRepositoryService.getAll();
    }

    @PostMapping("/list")
    public List<DictionaryItem> getDictionaryItems(@RequestParam("id") UUID dictUUID) {
        return dictionaryRepositoryService.getItemsById(dictUUID);
    }

    @PostMapping(value="/upload", produces = "text/plain;charset=UTF-8")
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestBody MultipartFile file) {
        try {
            dictionaryRepositoryService.checkName(name);

        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Словарь с таким именем уже существует");
        }
        String extension = ExtensionEnum.getExtension(name);
        if (!file.isEmpty() && extension.toLowerCase().equals("dict")) {
            if (dictionaryRepositoryService.getFileByName(name).isEmpty()) {
                try {
                    String relativeWebPath = "src/main/resources/dictionaries";
                    String absoluteFilePath = Paths.get(relativeWebPath).toAbsolutePath().toString();
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(absoluteFilePath,name)));
                    stream.write(bytes);
                    stream.close();
                    Dictionary savedDictionary = new Dictionary();
                    savedDictionary.setName(ExtensionEnum.cutOffExtension(name));
                    savedDictionary.setUrl(name);
                    dictionaryRepositoryService.saveFile(savedDictionary);
                    return "Вы удачно загрузили " + name + " в " + name + " !";
                } catch (Exception e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Вам не удалось загрузить " + name, e);
                }
            } else throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Словарь с таким именем уже существует");
        } else throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Вам не удалось загрузить " + name + ", потому что файл пустой или имеет некорректное расширение.");
    }

    @PostMapping(value="/add", produces = "text/plain;charset=UTF-8")
    public @ResponseBody String addNewEmptyDictionary(@RequestParam("name") String name) {
        try {
            dictionaryRepositoryService.checkName(name);

        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Словарь с таким именем уже существует");
        }
        String relativeWebPath = "src/main/resources/dictionaries";
        String absoluteFilePath = Paths.get(relativeWebPath).toAbsolutePath().toString();
        File dictonaryFile = new File(absoluteFilePath,name + ".dict");
        if (dictionaryRepositoryService.getFileByName(name).isEmpty()) {
            try {
                dictonaryFile.createNewFile();
                relativeWebPath = "target/classes/dictionaries";
                absoluteFilePath = Paths.get(relativeWebPath).toAbsolutePath().toString();
                dictonaryFile = new File(absoluteFilePath,name + ".dict");
                try {
                    dictonaryFile.createNewFile();
                    Dictionary savedDictionary = new Dictionary();
                    savedDictionary.setName(name);
                    savedDictionary.setUrl(name + ".dict");
                    savedDictionary = dictionaryRepositoryService.saveFile(savedDictionary);
                    return "Вы удачно загрузили " + name + " в " + name + ".dict" + " !";
                } catch (IOException e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Вам не удалось загрузить " + name, e);
                }
            } catch (IOException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Вам не удалось загрузить " + name, e);
            }
        } else throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Словарь с таким именем уже существует");
    }

    @PostMapping(value="/delete", produces = "text/plain;charset=UTF-8")
    public @ResponseBody String removeDictionary(@RequestParam("id") UUID dictUUID) {
        Dictionary dict = dictionaryRepositoryService.getDictionaryById(dictUUID);

        String relativeWebPath = "src/main/resources/dictionaries";
        String absoluteFilePath = Paths.get(relativeWebPath).toAbsolutePath().toString();

        File dictToDelete = new File(absoluteFilePath,dict.getUrl());

        if(dictToDelete.delete()){
            dictionaryRepositoryService.delete(dictUUID);
            return  dict.getUrl() + " удалено";
        }
        else throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "не удалось удалить " + dict.getUrl());
    }

    @PutMapping(value="/add/item", produces = "text/plain;charset=UTF-8")
    public String addDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody DictionaryItem item) {
        return dictionaryRepositoryService.setItem(dictUUID, item);
    }

    @PostMapping(value="/delete/item", produces = "text/plain;charset=UTF-8")
    public String removeDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody DictionaryItem item) {
        return dictionaryRepositoryService.deleteItem(dictUUID, item);
    }

    @PutMapping(value="/edit/item", produces = "text/plain;charset=UTF-8")
    public String editDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody List<DictionaryItem> items) {
         return dictionaryRepositoryService.editItem(dictUUID, items);
    }
}

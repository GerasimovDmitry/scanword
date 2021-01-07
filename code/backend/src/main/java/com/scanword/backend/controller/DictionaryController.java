package com.scanword.backend.controller;

import com.scanword.backend.entity.Dictionary;
import com.scanword.backend.entity.enums.ExtensionEnum;
import com.scanword.backend.entity.models.DictionaryItem;
import com.scanword.backend.entity.models.DictionaryModel;
import com.scanword.backend.service.DictionaryRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    @PostMapping(value="/upload")
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestBody MultipartFile file) {
        String extension = ExtensionEnum.getExtension(name);
        if (!file.isEmpty() && extension == "dict") {
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
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + ", потому что файл пустой или имеет некорректное расширение.";
        }
    }

    @DeleteMapping("/delete")
    public void removeDictionary(@RequestParam("id") UUID dictUUID) {
        dictionaryRepositoryService.delete(dictUUID);
    }

    @PutMapping("/add/item")
    public List<DictionaryItem> addDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody DictionaryItem item) {
        return dictionaryRepositoryService.setItem(dictUUID, item);
    }

    @DeleteMapping("/delete/item")
    public List<DictionaryItem> removeDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody DictionaryItem item) {
        return dictionaryRepositoryService.deleteItem(dictUUID, item);
    }

    @PutMapping("/edit/item")
    public DictionaryItem editDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody List<DictionaryItem> items) {
         return dictionaryRepositoryService.editItem(dictUUID, items);
    }
}

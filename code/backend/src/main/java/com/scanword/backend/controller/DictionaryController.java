package com.scanword.backend.controller;

import com.scanword.backend.entity.Dictionary;
import com.scanword.backend.entity.Question;
import com.scanword.backend.entity.User;
import com.scanword.backend.entity.models.DictionaryItem;
import com.scanword.backend.entity.models.DictionaryModel;
import com.scanword.backend.entity.models.UserModel;
import com.scanword.backend.entity.models.UserProfile;
import com.scanword.backend.service.DictionaryRepositoryService;
import com.scanword.backend.service.UserRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/list")
    public List<DictionaryItem> getDictionaryItems(@RequestParam("id") UUID dictUUID) {
        return dictionaryRepositoryService.getItemsById(dictUUID);
    }

    @PutMapping("/add/item")
    public List<DictionaryItem> addDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody DictionaryItem item) {
        return dictionaryRepositoryService.setItem(dictUUID, item);
    }

    @DeleteMapping("/delete/item")
    public List<DictionaryItem> removeDictionaryItem(@RequestParam("id") UUID dictUUID, @RequestBody DictionaryItem item) {
        return dictionaryRepositoryService.deleteItem(dictUUID, item);
    }

    @PostMapping("/edit/item")
    public DictionaryItem editMediaQuestion(@RequestParam("id") UUID dictUUID, @RequestBody DictionaryItem item) {
        return null;
    }
}

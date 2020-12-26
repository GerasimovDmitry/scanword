package com.scanword.backend.service;

import com.scanword.backend.entity.Dictionary;
import com.scanword.backend.entity.Media;
import com.scanword.backend.entity.models.DictionaryItem;
import com.scanword.backend.entity.models.DictionaryModel;
import com.scanword.backend.repository.DictionaryRepository;
import com.scanword.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DictionaryRepositoryService {
    private final DictionaryRepository repository;

    @Autowired
    public DictionaryRepositoryService(DictionaryRepository repository) {
        this.repository = repository;
    }

    public List<DictionaryModel> getAll() {
        List<Dictionary> dictionaries = repository.findAll();
        List<DictionaryModel> dictionaryModels = new ArrayList<DictionaryModel>();
        for (Dictionary dict : dictionaries) {
            DictionaryModel model = new DictionaryModel();
            model.setName(dict.getName());
            model.setId(dict.getUuid());
            dictionaryModels.add(model);
        }

        return  dictionaryModels;
    }

    public List<DictionaryItem> getItemsById(UUID dictUUID) {
        List<DictionaryItem> dictionaryItems = new ArrayList<DictionaryItem>();
        Dictionary dictionary = repository.findByUUID(dictUUID);
        try {
            File file = new ClassPathResource(
                    "dictionaries/" + dictionary.getUrl()).getFile();
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            String answer = line.substring(0, line.indexOf(' '));
            String text = line.substring(line.indexOf(' ') + 1);
            DictionaryItem item = new DictionaryItem();
            item.setAnswer(answer);
            item.setText(text);
            dictionaryItems.add(item);
            line = reader.readLine();
            while (line != null) {
                item = new DictionaryItem();
                answer = line.substring(0, line.indexOf(' '));
                text = line.substring(line.indexOf(' ') + 1);
                item.setAnswer(answer);
                item.setText(text);
                dictionaryItems.add(item);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionaryItems;
    }

    public List<DictionaryItem> setItem(UUID dictUUID, DictionaryItem item) {
        List<DictionaryItem> dictionaryItems = getItemsById(dictUUID);
        Dictionary dictionary = repository.findByUUID(dictUUID);
        if (dictionaryItems.contains(item)){
            return dictionaryItems;
        }
        else {
            try {
                File file = new ClassPathResource(
                        "dictionaries/" + dictionary.getUrl()).getFile();
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter out = new BufferedWriter(fw);
                writeToDictionary(item, out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return getItemsById(dictUUID);
        }
    }

    public List<DictionaryItem> deleteItem(UUID dictUUID, DictionaryItem item) {
        List<DictionaryItem> dictionaryItems = getItemsById(dictUUID);
        Dictionary dictionary = repository.findByUUID(dictUUID);
        if (dictionaryItems.contains(item)){
            dictionaryItems.remove(item);
            try {
                File file = new ClassPathResource(
                        "dictionaries/" + dictionary.getUrl()).getFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(fw);
                for (DictionaryItem dictionaryItem:dictionaryItems){
                    writeToDictionary(dictionaryItem, out);
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return getItemsById(dictUUID);
        }
        return dictionaryItems;
    }

    private void writeToDictionary(DictionaryItem item, BufferedWriter out) throws IOException {
        String line = item.getAnswer() + " " + item.getText();
        out.write(line);
        out.newLine();
        out.flush();
    }

    public void saveFile (Dictionary dictionary) {
        repository.saveAndFlush(dictionary);
    }
}

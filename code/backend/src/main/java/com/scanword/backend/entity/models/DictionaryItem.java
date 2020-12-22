package com.scanword.backend.entity.models;

import lombok.Data;

@Data
public class DictionaryItem {
    private String answer;
    private String text;

    @Override
    public boolean equals(Object obj) {
        return this.answer.equals(((DictionaryItem)obj).answer)
                && this.text.equals(((DictionaryItem)obj).text);
    }
}

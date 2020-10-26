package com.example.matrimony_demo.model;

import java.io.Serializable;

public class LanguageModel implements Serializable {
    int LanguageID;
    String FirstName;

    public int getLanguageID() {
        return LanguageID;
    }

    public void setLanguageID(int languageID) {
        LanguageID = languageID;
    }

    public String getName() {
        return FirstName;
    }

    public void setName(String firstName) {
        FirstName = firstName;
    }

    @Override
    public String toString() {
        return "LanguageModel{" +
                "LanguageID=" + LanguageID +
                ", FirstName='" + FirstName + '\'' +
                '}';
    }
}

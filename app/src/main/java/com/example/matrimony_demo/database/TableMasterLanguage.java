package com.example.matrimony_demo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.matrimony_demo.model.LanguageModel;

import java.util.ArrayList;

public class TableMasterLanguage extends MyDatabase {

    public static final String TABLE_NAME = "TableMasterLanguage";
    public static final String LANGUAGE_ID = "LanguageID";
    public static final String FIRST_NAME = "FirstName";

    public TableMasterLanguage(Context context) {
        super(context);
    }

    public ArrayList<LanguageModel> getLanguages() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<LanguageModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        LanguageModel languageModel1 = new LanguageModel();
        languageModel1.setName("Select One");
        list.add(0, languageModel1);
        for (int i = 0; i < cursor.getCount(); i++) {
            LanguageModel languageModel = new LanguageModel();
            languageModel.setLanguageID(cursor.getInt(cursor.getColumnIndex(LANGUAGE_ID)));
            languageModel.setName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
            list.add(languageModel);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
}
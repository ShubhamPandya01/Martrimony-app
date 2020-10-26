package com.example.matrimony_demo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matrimony_demo.model.CityModel;

import java.util.ArrayList;

public class TableMasterCity extends MyDatabase {

    public static final String TABLE_NAME = "TableMasterCity";
    public static final String CITY_ID = "CityID";
    public static final String FIRST_NAME = "FirstName";

    public TableMasterCity(Context context) {
        super(context);
    }

    public ArrayList<CityModel> getCityList() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CityModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        CityModel cityModel1 = new CityModel();
        cityModel1.setName("Select One");
        list.add(0, cityModel1);
        for (int i = 0; i < cursor.getCount(); i++) {
            CityModel cityModel = new CityModel();
            cityModel.setCityID(cursor.getInt(cursor.getColumnIndex(CITY_ID)));
            cityModel.setName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
            list.add(cityModel);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public CityModel getCityById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        CityModel model = new CityModel();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CITY_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        model.setName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
        model.setCityID(cursor.getInt(cursor.getColumnIndex(CITY_ID)));
        cursor.close();
        db.close();
        return model;
    }
}
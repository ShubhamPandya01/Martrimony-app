package com.example.matrimony_demo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matrimony_demo.model.UserModel;
import com.example.matrimony_demo.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class TableUser extends MyDatabase {

    public static final String TABLE_NAME = "TableUser";
    public static final String USER_ID = "UserId";
    public static final String FIRST_NAME = "FirstName";
    public static final String MIDDLE_NAME = "MiddleName";
    public static final String LAST_NAME = "LastName";
    public static final String EMAIL = "Email";
    public static final String GENDER = "Gender";
    public static final String HOBBIES = "Hobbies";
    public static final String DATE_OF_BIRTH = "DateOfBirth";
    public static final String MOBILE_NUMBER = "MobileNumber";
    public static final String LANGUAGE_ID = "LanguageID";
    public static final String CITY_ID = "CityID";
    public static final String IS_FAVOURITE = "IsFavourite";

    /* QUERY COLUMN */
    public static final String CITY = "City";
    public static final String LANGUAGE = "Language";
    public static final String AGE = "Age";

    public TableUser(Context context) {
        super(context);
    }

    public ArrayList<UserModel> getUserList() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<UserModel> list = new ArrayList<>();
        String query =
                "SELECT " +
                        " UserId," +
                        " TableUser.FirstName as FirstName," +
                        " MiddleName," +
                        " LastName," +
                        " Gender," +
                        " Email," +
                        " IsFavourite," +
                        " DateOfBirth," +
                        " MobileNumber," +
                        " TableMasterLanguage.LanguageID," +
                        " TableMasterCity.CityID," +
                        " TableMasterLanguage.FirstName as Language," +
                        " TableMasterCity.FirstName as City " +
                        " FROM " +
                        " TableUser " +
                        " INNER JOIN TableMasterLanguage ON TableUser.LanguageID = TableMasterLanguage.LanguageID" +
                        " INNER JOIN TableMasterCity ON TableUser.CityID = TableMasterCity.CityID";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            list.add(getCreatedModelUsingCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();


        return list;
    }

    public UserModel getCreatedModelUsingCursor(Cursor cursor) {
        UserModel model = new UserModel();
        model.setUserId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
        model.setCityID(cursor.getInt(cursor.getColumnIndex(CITY_ID)));
        model.setLanguageID(cursor.getInt(cursor.getColumnIndex(LANGUAGE_ID)));
        model.setGender(cursor.getInt(cursor.getColumnIndex(GENDER)));
        model.setDateOfBirth(Utils.getDateToDisplay(cursor.getString(cursor.getColumnIndex(DATE_OF_BIRTH))));
        model.setLastName(cursor.getString(cursor.getColumnIndex(LAST_NAME)));
        model.setFirstName(cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
        model.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
        model.setMobileNumber(cursor.getString(cursor.getColumnIndex(MOBILE_NUMBER)));
        model.setMiddleName(cursor.getString(cursor.getColumnIndex(MIDDLE_NAME)));
        model.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
        model.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
        model.setIsFavorite(cursor.getInt(cursor.getColumnIndex(IS_FAVOURITE)));
        return model;
    }

    private String getAge(int year, int month, int day) {
        Calendar DateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        DateOfBirth.set(year, month, day);
        int age = today.get(Calendar.YEAR) - DateOfBirth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < DateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageS;
    }

    public UserModel getUserById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        UserModel model = new UserModel();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        model = getCreatedModelUsingCursor(cursor);
        cursor.close();
        db.close();
        return model;
    }

    public ArrayList<UserModel> getUserListByGender(int gender) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<UserModel> list = new ArrayList<>();
        String query =
                "SELECT " +
                        " UserId," +
                        " TableUser.FirstName as FirstName," +
                        " MiddleName," +
                        " LastName," +
                        " Gender," +
                        " Email," +
                        " IsFavourite," +
                        " DateOfBirth," +
                        " MobileNumber," +
                        " TableMasterLanguage.LanguageID," +
                        " TableMasterCity.CityID," +
                        " TableMasterLanguage.FirstName as Language," +
                        " TableMasterCity.FirstName as City " +

                        "FROM " +
                        " TableUser " +
                        " INNER JOIN TableMasterLanguage ON TableUser.LanguageID = TableMasterLanguage.LanguageID" +
                        " INNER JOIN TableMasterCity ON TableUser.CityID = TableMasterCity.CityID" +

                        " WHERE " +
                        " Gender = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(gender)});
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            list.add(getCreatedModelUsingCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<UserModel> getFavoriteUserList() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<UserModel> list = new ArrayList<>();
        String query =
                "SELECT " +
                        " UserId," +
                        " TableUser.FirstName as FirstName," +
                        " MiddleName," +
                        " LastName," +
                        " Gender," +
                        " Email," +
                        " IsFavourite," +
                        " DateOfBirth," +
                        " MobileNumber," +
                        " TableMasterLanguage.LanguageID," +
                        " TableMasterCity.CityID," +
                        " TableMasterLanguage.FirstName as Language," +
                        " TableMasterCity.FirstName as City " +

                        "FROM " +
                        " TableUser " +
                        " INNER JOIN TableMasterLanguage ON TableUser.LanguageID = TableMasterLanguage.LanguageID" +
                        " INNER JOIN TableMasterCity ON TableUser.CityID = TableMasterCity.CityID" +

                        " WHERE " +
                        " IsFavourite = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(1)});
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            list.add(getCreatedModelUsingCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public long insertUser(String FirstName, String MiddleName, String LastName, int gender, String hobbies,
                           String DateOfBirth, String MobileNumber, String email, int languageID, int cityID, int IsFavourite) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIRST_NAME, FirstName);
        cv.put(MIDDLE_NAME, MiddleName);
        cv.put(LAST_NAME, LastName);
        cv.put(GENDER, gender);
        cv.put(HOBBIES, hobbies);
        cv.put(DATE_OF_BIRTH, DateOfBirth);
        cv.put(MOBILE_NUMBER, MobileNumber);
        cv.put(LANGUAGE_ID, languageID);
        cv.put(EMAIL, email);
        cv.put(CITY_ID, cityID);
        cv.put(IS_FAVOURITE, IsFavourite);
        long lastInsertedID = db.insert(TABLE_NAME, null, cv);
        db.close();
        return lastInsertedID;
    }

    public int updateUserById(String FirstName, String MiddleName, String LastName, int gender, String hobbies,
                              String DateOfBirth, String MobileNumber, String email, int languageID, int cityID, int IsFavourite, int userId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIRST_NAME, FirstName);
        cv.put(MIDDLE_NAME, MiddleName);
        cv.put(LAST_NAME, LastName);
        cv.put(GENDER, gender);
        cv.put(HOBBIES, hobbies);
        cv.put(DATE_OF_BIRTH, DateOfBirth);
        cv.put(MOBILE_NUMBER, MobileNumber);
        cv.put(EMAIL, email);
        cv.put(LANGUAGE_ID, languageID);
        cv.put(CITY_ID, cityID);
        cv.put(IS_FAVOURITE, IsFavourite);
        int lastUpdatedId = db.update(TABLE_NAME, cv, USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
        return lastUpdatedId;
    }

    public int deleteUserById(int userId) {
        SQLiteDatabase db = getWritableDatabase();
        int deletedUserID = db.delete(TABLE_NAME, USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
        return deletedUserID;
    }

    public int updateFavoriteStatus(int IsFavourite, int userId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IS_FAVOURITE, IsFavourite);
        int lastUpdatedId = db.update(TABLE_NAME, cv, USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
        return lastUpdatedId;
    }
}
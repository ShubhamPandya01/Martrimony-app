package com.example.matrimony_demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date getDateFromString(String dateToConvert) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateToDisplay(String dateToConvert) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        String convertedDate = "";
        try {
            Date date = format.parse(dateToConvert);
            convertedDate = dateFormat2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String getFormattedDateToInsert(String dateToConvert) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String convertedDate = "";
        try {
            Date date = dateFormat.parse(dateToConvert);
            convertedDate = dateFormat2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }
}

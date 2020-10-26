package com.example.matrimony_demo.model;

import java.io.Serializable;

public class CityModel implements Serializable {

    int CityID;
    String FirstName;

    public int getCityID() {
        return CityID;
    }

    public void setCityID(int cityID) {
        CityID = cityID;
    }

    public String getName() {
        return FirstName;
    }

    public void setName(String firstName) {
        FirstName = firstName;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "CityID=" + CityID +
                ", FirstName='" + FirstName + '\'' +
                '}';
    }
}
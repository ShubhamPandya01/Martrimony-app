package com.example.matrimony_demo.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    int UserId;
    String FirstName;
    String MiddleName;
    String Email;
    int Gender;
    String LastName;
    String Hobbies;
    String DateOfBirth;
    String MobileNumber;
    int LanguageID;
    int CityID;
    String Language;
    String City;
    int IsFavourite;

    public void setIsFavorite(int isFavourite) {
        IsFavourite = isFavourite;
    }

    public int getIsFavorite() {
        return IsFavourite;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getHobbies() {
        return Hobbies;
    }

    public void setHobbies(String hobbies) {
        Hobbies = hobbies;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public int getLanguageID() {
        return LanguageID;
    }

    public void setLanguageID(int languageID) {
        LanguageID = languageID;
    }

    public int getCityID() {
        return CityID;
    }

    public void setCityID(int cityID) {
        CityID = cityID;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "UserId=" + UserId +
                ", FirstName='" + FirstName + '\'' +
                ", MiddleName='" + MiddleName + '\'' +
                ", Email='" + Email + '\'' +
                ", Gender=" + Gender +
                ", LastName='" + LastName + '\'' +
                ", Hobbies='" + Hobbies + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", MobileNumber='" + MobileNumber + '\'' +
                ", LanguageID=" + LanguageID +
                ", CityID=" + CityID +
                ", Language='" + Language + '\'' +
                ", City='" + City + '\'' +
                ", IsFavourite=" + IsFavourite +
                '}';
    }
}
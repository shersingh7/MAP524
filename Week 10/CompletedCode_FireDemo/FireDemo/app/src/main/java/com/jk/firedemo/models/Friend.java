package com.jk.firedemo.models;

import java.util.Date;

/**
 * FireDemo Created by jkp on 2021-05-28.
 */
public class Friend {
    private String id;
    private String name;
    private String phoneNumber;
    private Date birthDate;

    public Friend(String name, String phoneNumber, Date birthDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public Friend(){
        this.name = "";
        this.phoneNumber = "";
        this.birthDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}

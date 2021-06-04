package com.jk.zzapp;

import java.io.Serializable;
import java.util.Date;

/**
 * ZZApp Created by jkp on 2021-06-02.
 */
public class User implements Serializable {
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private Date birthdate;
    private String password;

    public User() {
    }

    public User(String name, String email, String phoneNumber, String gender, Date birthdate, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthdate = birthdate;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                ", password='" + password + '\'' +
                '}';
    }
}

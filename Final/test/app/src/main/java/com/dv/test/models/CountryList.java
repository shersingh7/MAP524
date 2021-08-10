package com.dv.test.models;

import com.google.gson.annotations.SerializedName;

public class CountryList {
    @SerializedName("name")
    String name;

    @SerializedName("alpha2Code")
    String code;

    @SerializedName("capital")
    String capital;

    private String flag;

    public CountryList(String name, String capital, String code, String flag) {
        this.name = name;
        this.capital = capital;
        this.code = code;
        this.flag = flag;
    }

    public CountryList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "CountryList{" +
                "name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                ", code='" + code + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}

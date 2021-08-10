package com.dv.test.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_country")
public class CountryDB {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "country_name")
    private String countryName;

    @NonNull
    @ColumnInfo(name = "country_capital")
    private String countryCapital;

    @NonNull
    @ColumnInfo(name = "country_flag")
    private String countryFlag;

    @NonNull
    @ColumnInfo(name = "country_code")
    private String countryCode;

    @NonNull
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(@NonNull String countryCode) {
        this.countryCode = countryCode;
    }

    public CountryDB() {
    }

    public CountryDB(String countryName, String countryCapital, String countryFlag, String countryCode) {
        this.countryName = countryName;
        this.countryCapital = countryCapital;
        this.countryFlag = countryFlag;
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @NonNull
    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(@NonNull String countryCapital) {
        this.countryCapital = countryCapital;
    }

    @NonNull
    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(@NonNull String countryFlag) {
        this.countryFlag = countryFlag;
    }
}

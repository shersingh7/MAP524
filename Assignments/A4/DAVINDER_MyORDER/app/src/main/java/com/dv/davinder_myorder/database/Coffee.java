package com.dv.davinder_myorder.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_coffee")
public class Coffee {

    @NonNull
    @ColumnInfo(name = "coffee_type")
    private String coffeeType;

    @PrimaryKey
    @ColumnInfo(name = "coffee_id")
    private Integer coffeeID;

    @NonNull
    @ColumnInfo(name = "coffee_size")
    private String coffeeSize;

    @NonNull
    @ColumnInfo(name = "coffee_qty")
    private Integer coffeeQty;

    public Coffee() {
    }

    public Coffee(String coffeeType, Integer coffeeID, String coffeeSize, Integer coffeeQty) {
        this.coffeeType = coffeeType;
        this.coffeeID = coffeeID;
        this.coffeeSize = coffeeSize;
        this.coffeeQty = coffeeQty;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public String getCoffeeSize() {
        return coffeeSize;
    }

    public void setCoffeeSize(String coffeeSize) {
        this.coffeeSize = coffeeSize;
    }

    public Integer getCoffeeQty() {
        return coffeeQty;
    }

    public void setCoffeeQty(Integer coffeeQty) {
        this.coffeeQty = coffeeQty;
    }

    @Override
    public String toString() {
        return "coffee{" +
                "coffeeType='" + coffeeType + '\'' +
                ", coffeeSize='" + coffeeSize + '\'' +
                ", coffeeQty=" + coffeeQty +
                " coffeeID=" + coffeeID + '}';
    }

    public Integer getCoffeeID() {
        return coffeeID;
    }

    public void setCoffeeID(Integer coffeeID) {
        this.coffeeID = coffeeID;
    }
}

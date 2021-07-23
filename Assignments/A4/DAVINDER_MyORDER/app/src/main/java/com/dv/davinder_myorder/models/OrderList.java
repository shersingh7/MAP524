package com.dv.davinder_myorder.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderList{

    private String coffeeType;
    private String coffeeSize;
    private Integer coffeeQty;
    private Integer coffeeID;

    public OrderList(String coffeeType, String coffeeSize, Integer coffeeQty, Integer coffeeID) {
        this.coffeeType = coffeeType;
        this.coffeeSize = coffeeSize;
        this.coffeeQty = coffeeQty;
        this.coffeeID = coffeeID;
    }

    public OrderList() {
    }

    public Integer getCoffeeID() {
        return coffeeID;
    }

    public void setCoffeeID(Integer coffeeID) {
        this.coffeeID = coffeeID;
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
        return "OrderList{" +
                "coffeeType='" + coffeeType + '\'' +
                ", coffeeSize='" + coffeeSize + '\'' +
                ", coffeeQty=" + coffeeQty +
                '}';
    }

}

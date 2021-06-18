package com.dv.davinder_myorder.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderList implements Parcelable {

    private String coffeeType;
    private String coffeeSize;
    private Integer coffeeQty;

    public OrderList(String coffeeType, String coffeeSize, Integer coffeeQty) {
        this.coffeeType = coffeeType;
        this.coffeeSize = coffeeSize;
        this.coffeeQty = coffeeQty;
    }

    public OrderList() {
    }

    protected OrderList(Parcel in) {
        coffeeType = in.readString();
        coffeeSize = in.readString();
        if (in.readByte() == 0) {
            coffeeQty = null;
        } else {
            coffeeQty = in.readInt();
        }
    }

    public static final Creator<OrderList> CREATOR = new Creator<OrderList>() {
        @Override
        public OrderList createFromParcel(Parcel in) {
            return new OrderList(in);
        }

        @Override
        public OrderList[] newArray(int size) {
            return new OrderList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(coffeeType);
        dest.writeString(coffeeSize);
        if (coffeeQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(coffeeQty);
        }
    }
}

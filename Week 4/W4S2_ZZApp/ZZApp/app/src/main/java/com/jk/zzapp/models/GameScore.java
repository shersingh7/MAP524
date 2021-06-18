package com.jk.zzapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ZZApp Created by jkp on 2021-06-07.
 */
public class GameScore implements Parcelable {
    private String userEmail;
    private boolean result;
    private int correctNumber;
    private int numberOfAttempts;
    private int points;

    public GameScore(String userEmail, boolean result, int correctNumber, int numberOfAttempts, int points) {
        this.userEmail = userEmail;
        this.result = result;
        this.correctNumber = correctNumber;
        this.numberOfAttempts = numberOfAttempts;
        this.points = points;
    }

    public GameScore() {
    }

    protected GameScore(Parcel in) {
        userEmail = in.readString();
        result = in.readByte() != 0;
        correctNumber = in.readInt();
        numberOfAttempts = in.readInt();
        points = in.readInt();
    }

    public static final Creator<GameScore> CREATOR = new Creator<GameScore>() {
        @Override
        public GameScore createFromParcel(Parcel in) {
            return new GameScore(in);
        }

        @Override
        public GameScore[] newArray(int size) {
            return new GameScore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userEmail);
        dest.writeByte((byte) (result ? 1 : 0));
        dest.writeInt(correctNumber);
        dest.writeInt(numberOfAttempts);
        dest.writeInt(points);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(int correctNumber) {
        this.correctNumber = correctNumber;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "GameScore{" +
                "userEmail='" + userEmail + '\'' +
                ", result=" + result +
                ", correctNumber=" + correctNumber +
                ", numberOfAttempts=" + numberOfAttempts +
                ", points=" + points +
                '}';
    }

}

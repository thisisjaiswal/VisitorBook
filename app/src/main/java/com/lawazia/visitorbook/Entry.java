package com.lawazia.visitorbook;

import com.lawazia.yourid.YourInfo;

import java.io.Serializable;

public class Entry implements Serializable {
    private static final long serialVersionUID = 1L;

    int sheetId;
    int id;
    String yourName;
    String yourMobile;
    //private YourInfo yourInfo;

    public int getSheetId() {
        return sheetId;
    }
    public void setSheetId(int sheetId) {
        this.sheetId = sheetId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getYourName() {
        return yourName;
    }
    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    public String getYourMobile() {
        return yourMobile;
    }
    public void setYourMobile(String yourMobile) {
        this.yourMobile = yourMobile;
    }

    @Override
    public String toString() {
        return String.format(" | " + yourName);
    }
}

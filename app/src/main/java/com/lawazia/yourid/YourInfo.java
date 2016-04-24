package com.lawazia.yourid;


import java.io.Serializable;

public class YourInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String mobile;
    private String pin;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int yourId) {
        this.id = yourId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobileNo) {
        this.mobile = mobileNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

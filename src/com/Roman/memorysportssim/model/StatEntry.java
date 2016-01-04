package com.Roman.memorysportssim.model;

import java.util.Date;

public class StatEntry {

    private Date date;
    private int digits;
    private int success;
    private int memMillis;
    private int recallMillis;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getMemMillis() {
        return memMillis;
    }

    public void setMemMillis(int memMillis) {
        this.memMillis = memMillis;
    }

    public int getRecallMillis() {
        return recallMillis;
    }

    public void setRecallMillis(int recallMillis) {
        this.recallMillis = recallMillis;
    }
}

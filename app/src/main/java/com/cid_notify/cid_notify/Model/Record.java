package com.cid_notify.cid_notify.Model;

public class Record {
    private String phoneNum;
    private String number_info;
    private String time;
    private String date;
    private String to;

    public String getTo() {return to;}
    public String getNumber_info() {
        return number_info;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public String getTime(){
        return time.substring(0,5);
    }
    public String getFullTime(){return time;}
    public String getDate() {
        return date;
    }
    public void setNumber_info(String number_info) {
        this.number_info = number_info;
    }
}

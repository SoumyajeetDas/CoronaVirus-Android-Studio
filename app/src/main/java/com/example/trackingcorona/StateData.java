package com.example.trackingcorona;

public class StateData {
    String str_cnf, str_recovery, str_name, str_death, str_todaydeath, str_todayconfirmed, str_todayrecovered,str_lastupdatedtime;

    public StateData() {
    }

    public StateData(String str_cnf, String str_recovery, String str_name, String str_death, String str_todaydeath, String str_todayconfirmed, String str_todayrecovered,String str_lastupdatedtime) {
        this.str_cnf = str_cnf;
        this.str_recovery = str_recovery;
        this.str_name = str_name;
        this.str_death = str_death;
        this.str_todaydeath = str_todaydeath;
        this.str_todayconfirmed = str_todayconfirmed;
        this.str_todayrecovered = str_todayrecovered;
        this.str_lastupdatedtime = str_lastupdatedtime;
    }

    public String getStr_lastupdatedtime() {
        return str_lastupdatedtime;
    }

    public void setStr_lastupdatedtime(String str_lastupdatedtime) {
        this.str_lastupdatedtime = str_lastupdatedtime;
    }

    public String getStr_cnf() {
        return str_cnf;
    }

    public void setStr_cnf(String str_cnf) {
        this.str_cnf = str_cnf;
    }

    public String getStr_recovery() {
        return str_recovery;
    }

    public void setStr_recovery(String str_recovery) {
        this.str_recovery = str_recovery;
    }

    public String getStr_name() {
        return str_name;
    }

    public void setStr_name(String str_name) {
        this.str_name = str_name;
    }

    public String getStr_death() {
        return str_death;
    }

    public void setStr_death(String str_death) {
        this.str_death = str_death;
    }

    public String getStr_todaydeath() {
        return str_todaydeath;
    }

    public void setStr_todaydeath(String str_todaydeath) {
        this.str_todaydeath = str_todaydeath;
    }

    public String getStr_todayconfirmed() {
        return str_todayconfirmed;
    }

    public void setStr_todayconfirmed(String str_todayconfirmed) {
        this.str_todayconfirmed = str_todayconfirmed;
    }

    public String getStr_todayrecovered() {
        return str_todayrecovered;
    }

    public void setStr_todayrecovered(String str_todayrecovered) {
        this.str_todayrecovered = str_todayrecovered;
    }
}
package com.example.trackingcorona;

public class DistrictData {
    String dis_name,dis_cnf,dis_death,dis_recovered,dis_newcnf,dis_newdeath,dis_newrecovered;

    public DistrictData() {
    }

    public DistrictData(String dis_name, String dis_cnf, String dis_death, String dis_recovered, String dis_newcnf, String dis_newdeath, String dis_newrecovered) {
        this.dis_name = dis_name;
        this.dis_cnf = dis_cnf;
        this.dis_death = dis_death;
        this.dis_recovered = dis_recovered;
        this.dis_newcnf = dis_newcnf;
        this.dis_newdeath = dis_newdeath;
        this.dis_newrecovered = dis_newrecovered;
    }

    public String getDis_name() {
        return dis_name;
    }

    public void setDis_name(String dis_name) {
        this.dis_name = dis_name;
    }

    public String getDis_cnf() {
        return dis_cnf;
    }

    public void setDis_cnf(String dis_cnf) {
        this.dis_cnf = dis_cnf;
    }

    public String getDis_death() {
        return dis_death;
    }

    public void setDis_death(String dis_death) {
        this.dis_death = dis_death;
    }

    public String getDis_recovered() {
        return dis_recovered;
    }

    public void setDis_recovered(String dis_recovered) {
        this.dis_recovered = dis_recovered;
    }

    public String getDis_newcnf() {
        return dis_newcnf;
    }

    public void setDis_newcnf(String dis_newcnf) {
        this.dis_newcnf = dis_newcnf;
    }

    public String getDis_newdeath() {
        return dis_newdeath;
    }

    public void setDis_newdeath(String dis_newdeath) {
        this.dis_newdeath = dis_newdeath;
    }

    public String getDis_newrecovered() {
        return dis_newrecovered;
    }

    public void setDis_newrecovered(String dis_newrecovered) {
        this.dis_newrecovered = dis_newrecovered;
    }
}

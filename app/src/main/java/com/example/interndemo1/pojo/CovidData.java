package com.example.interndemo1.pojo;

public class CovidData {

    String loc, conInd, conFor, discharge, death, totalCon;

    public CovidData() { }

    public CovidData(String loc, String conInd, String conFor, String discharge, String death, String totalCon) {
        this.loc = loc;
        this.conInd = conInd;
        this.conFor = conFor;
        this.discharge = discharge;
        this.death = death;
        this.totalCon = totalCon;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getConInd() {
        return conInd;
    }

    public void setConInd(String conInd) {
        this.conInd = conInd;
    }

    public String getConFor() {
        return conFor;
    }

    public void setConFor(String conFor) {
        this.conFor = conFor;
    }

    public String getDischarge() {
        return discharge;
    }

    public void setDischarge(String discharge) {
        this.discharge = discharge;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getTotalCon() {
        return totalCon;
    }

    public void setTotalCon(String totalCon) {
        this.totalCon = totalCon;
    }
}

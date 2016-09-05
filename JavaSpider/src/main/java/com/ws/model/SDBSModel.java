package com.ws.model;

import com.ws.util.JsonUtils;

/**
 * Created by laowang on 16-9-2.
 */
public class SDBSModel {
    private int SDBSNO;
    private String Mole_Formula;
    private String Mole_Weight;
    private String Compound_Name;
    private String MS;
    private String CNMR;
    private String HNMR;
    private String IR;
    private String Raman;
    private String ESR;

    public int getSDBSNO() {
        return SDBSNO;
    }

    public void setSDBSNO(int SDBSNO) {
        this.SDBSNO = SDBSNO;
    }

    public String getMole_Formula() {
        return Mole_Formula;
    }

    public void setMole_Formula(String mole_Formula) {
        Mole_Formula = mole_Formula;
    }

    public String getMole_Weight() {
        return Mole_Weight;
    }

    public void setMole_Weight(String mole_Weight) {
        Mole_Weight = mole_Weight;
    }

    public String getCompound_Name() {
        return Compound_Name;
    }

    public void setCompound_Name(String compound_Name) {
        Compound_Name = compound_Name;
    }

    public String getMS() {
        return MS;
    }

    public void setMS(String MS) {
        this.MS = MS;
    }

    public String getCNMR() {
        return CNMR;
    }

    public void setCNMR(String CNMR) {
        this.CNMR = CNMR;
    }

    public String getHNMR() {
        return HNMR;
    }

    public void setHNMR(String HNMR) {
        this.HNMR = HNMR;
    }

    public String getIR() {
        return IR;
    }

    public void setIR(String IR) {
        this.IR = IR;
    }

    public String getRaman() {
        return Raman;
    }

    public void setRaman(String paman) {
        Raman = paman;
    }

    public String getESR() {
        return ESR;
    }

    public void setESR(String ESR) {
        this.ESR = ESR;
    }

    public String toJson(){
        return JsonUtils.toJson(this);
    }
}

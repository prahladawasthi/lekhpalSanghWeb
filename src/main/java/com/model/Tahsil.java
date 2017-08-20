package com.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "tahsil")
public class Tahsil implements Serializable {

    @Id
    private String id;
    @Field
    private String tahsilName;
    @Field
    private String SDMNumber;
    @Field
    private String tahsildaarNumber;
    @Field
    private String naibNahsildaarNumber;
    @Field
    private String districtID;
    @Field
    private String mandalID;

    public String getSDMNumber() {
        return SDMNumber;
    }

    public void setSDMNumber(String SDMNumber) {
        this.SDMNumber = SDMNumber;
    }

    public String getTahsildaarNumber() {
        return tahsildaarNumber;
    }

    public void setTahsildaarNumber(String tahsildaarNumber) {
        this.tahsildaarNumber = tahsildaarNumber;
    }

    public String getNaibNahsildaarNumber() {
        return naibNahsildaarNumber;
    }

    public void setNaibNahsildaarNumber(String naibNahsildaarNumber) {
        this.naibNahsildaarNumber = naibNahsildaarNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTahsilName() {
        return tahsilName;
    }

    public void setTahsilName(String tahsilName) {
        this.tahsilName = tahsilName;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public String getMandalID() {
        return mandalID;
    }

    public void setMandalID(String mandalID) {
        this.mandalID = mandalID;
    }

}

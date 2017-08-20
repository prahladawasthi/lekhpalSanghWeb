package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "district")
public class District implements Serializable{

    @Id
    private String id;
    @Field
    private String districtName;
    @Field
    private String magistrateNumber;
    @Field
    private String mandalID;

    public String getMagistrateNumber() {
        return magistrateNumber;
    }

    public void setMagistrateNumber(String magistrateNumber) {
        this.magistrateNumber = magistrateNumber;
    }

    public String getMandalID() {
        return mandalID;
    }

    public void setMandalID(String mandalID) {
        this.mandalID = mandalID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

}

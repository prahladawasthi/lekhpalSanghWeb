package com.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tahsil")
public class Tahsil {

    @Id
    private String id;
    @Field
    private String tahsilName;
    @Field
    private String districtID;
    @Field
    private String mandalID;

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

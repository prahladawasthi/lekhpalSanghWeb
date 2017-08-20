package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "mandal")
public class Mandal implements Serializable{

    @Id
    private String id;
    @Field
    private String mandalName;
    @Field
    private String commissionerNumber;

    public String getCommissionerNumber() {
        return commissionerNumber;
    }

    public void setCommissionerNumber(String commissionerNumber) {
        this.commissionerNumber = commissionerNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }
}

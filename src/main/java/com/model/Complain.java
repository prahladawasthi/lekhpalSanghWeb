package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "complain")
public class Complain {

    @Id
    private String id;

    @Field
    private String complainFrom;
    @Field
    private String complainTo;
    @Field
    private String complainSubject;
    @Field
    private String description;
    @Field
    private String raisedDate;

    public String getComplainSubject() {
        return complainSubject;
    }

    public void setComplainSubject(String complainSubject) {
        this.complainSubject = complainSubject;
    }

    public String getRaisedDate() {
        return raisedDate;
    }

    public void setRaisedDate(String raisedDate) {
        this.raisedDate = raisedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplainFrom() {
        return complainFrom;
    }

    public void setComplainFrom(String complainFrom) {
        this.complainFrom = complainFrom;
    }

    public String getComplainTo() {
        return complainTo;
    }

    public void setComplainTo(String complainTo) {
        this.complainTo = complainTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

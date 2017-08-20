package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Document(collection = "user")
public class User implements Serializable{

    @Field
    Map<String, String> ratingProvider;
    @Id
    private String id;
    @Field
    @Size(min = 3, max = 30)
    private String email;
    @Field
    private String password;
    @Field
    @Size(min = 3, max = 30)
    private String firstName;
    @Field
    @Size(min = 3, max = 30)
    private String lastName;
    @Field
    private String designation;
    @Field
    private String rating;
    @Field
    private String mandalID;
    @Field
    private String districtID;
    @Field
    private String tahsilID;
    @Field
    private String areaAppointedAt;
    @Field
    @Size(min = 10, max = 10)
    private String mobile;
    @Field
    private String alternateMobile;
    @Field
    private String emergencyContact;
    @Field
    private String enabled;
    @Field
    private String confirmationToken;
    @Field
    private Date createdOn;
    @Field
    private Date lastLogin;
    @Field
    private String resetToken;
    @Field
    private String userRole;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Map<String, String> getRatingProvider() {
        return ratingProvider;
    }

    public void setRatingProvider(Map<String, String> ratingProvider) {
        this.ratingProvider = ratingProvider;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMandalID() {
        return mandalID;
    }

    public void setMandalID(String mandalID) {
        this.mandalID = mandalID;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public String getTahsilID() {
        return tahsilID;
    }

    public void setTahsilID(String tahsilID) {
        this.tahsilID = tahsilID;
    }

    public String getAreaAppointedAt() {
        return areaAppointedAt;
    }

    public void setAreaAppointedAt(String areaAppointedAt) {
        this.areaAppointedAt = areaAppointedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

}
package com.uas.mobile.model;

import com.google.gson.annotations.SerializedName;

public class ModelEmployeeResponse {

    @SerializedName("employeeId")
    private Integer employeeId;

    @SerializedName("name")
    private ModelNameResponse name;

    @SerializedName("location")
    private ModelLocationResponse location;

    @SerializedName("email")
    private String email;

    @SerializedName("registered")
    private ModelEmployeeResponse registered;

    @SerializedName("date")
    private String date;

    @SerializedName("phone")
    private String phone;

    @SerializedName("cell")
    private String cell;

    @SerializedName("picture")
    private ModelEmployeeResponse picture;

    @SerializedName("large")
    private String large;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public ModelNameResponse getName() {
        return name;
    }

    public void setName(ModelNameResponse name) {
        this.name = name;
    }

    public ModelLocationResponse getLocation() {
        return location;
    }

    public void setLocation(ModelLocationResponse location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ModelEmployeeResponse getRegistered() {
        return registered;
    }

    public void setRegistered(ModelEmployeeResponse registered) {
        this.registered = registered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public ModelEmployeeResponse getPicture() {
        return picture;
    }

    public void setPicture(ModelEmployeeResponse picture) {
        this.picture = picture;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}

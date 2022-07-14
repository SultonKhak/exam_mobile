package com.uas.mobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelUserResponse {

    @SerializedName("nim")
    private String nim;

    @SerializedName("nama")
    private String nama;

    @SerializedName("employeeId")
    private Integer employeeId;

    @SerializedName("employees")
    private List<ModelEmployeeResponse> modelEmployeeResponses;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public List<ModelEmployeeResponse> getModelEmployeeResponses() {
        return modelEmployeeResponses;
    }

    public void setModelEmployeeResponses(List<ModelEmployeeResponse> modelEmployeeResponses) {
        this.modelEmployeeResponses = modelEmployeeResponses;
    }
}

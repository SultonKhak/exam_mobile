package com.uas.mobile.model;

import com.google.gson.annotations.SerializedName;

public class ModelLocationResponse {

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("coordinates")
    private ModelCoordinatesResponse coordinates;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ModelCoordinatesResponse getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ModelCoordinatesResponse coordinates) {
        this.coordinates = coordinates;
    }
}

package com.example.travy.model;

public class Site {

    private String placeId;

    public Site(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        return "Site{" +
                "placeId='" + placeId + '\'' +
                '}';
    }

}

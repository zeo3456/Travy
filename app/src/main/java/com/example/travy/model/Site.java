package com.example.travy.model;

public class Site {
    private String placeId;
    private String memo;

    public Site(String placeId,String memo){
        this.placeId = placeId;
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        memo = memo;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

}

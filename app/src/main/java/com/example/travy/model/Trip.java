package com.example.travy.model;

import java.util.ArrayList;

public class Trip {
    private int id;

    private String title;
    private ArrayList<Site> siteList;
    public Trip(int id,String title) {
        this.title = title;
        this.siteList = new ArrayList<Site>();
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Trip" + " id = " + id + ", title = " + title;
    }
}

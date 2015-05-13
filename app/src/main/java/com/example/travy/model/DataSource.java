package com.example.travy.model;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    static List<Trip> triplist = new ArrayList<Trip>();

    public static boolean getSize() {
        return triplist.isEmpty();
    }

    public static void clearTrip() {
        triplist = new ArrayList<Trip>();
    }

    public static void removeTrip(int pos) {
        triplist.remove(pos);
    }

    public static String getTripName(int pos) {
        return triplist.get(pos).getTitle();
    }

    public static void addtrip(Trip e) {
        triplist.add(e);
    }

    public List<Trip> findAllTrip() {
        return triplist;
    }

    //remove function actually doesn't work, rebuild needed
    public void remove(Trip trip) {
        triplist.remove(trip);
    }

}

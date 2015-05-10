package com.example.travy.model;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    static List<Trip> triplist = new ArrayList<Trip>();
    public static boolean getSize(){
        return triplist.isEmpty();
    }
    public static void clearTrip(){
        triplist = new ArrayList<Trip>();
    }

    public static void addtrip(Trip e){
        triplist.add(e);
    }
    public List<Trip> findAllTrip(){

//        Trip t1 = new Trip(1, "New York City");
//        Trip t2 = new Trip(2, "Beijing");
//
//        triplist.add(t1);
//        triplist.add(t2);
        return triplist;
    }
    //remove function actually doesn't work, rebuild needed
    public void remove(Trip trip){
        triplist.remove(trip);
    }
}

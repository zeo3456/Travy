package com.example.travy.model;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    List<Trip> triplist = new ArrayList<Trip>();
    public List<Trip> findAllTrip(){

        Trip t1 = new Trip(1, "New York City");
        Trip t2 = new Trip(2, "Beijing");

        triplist.add(t1);
        triplist.add(t2);
        return triplist;
    }
    //remove function actually doesn't work, rebuild needed
    public void remove(Trip trip){
        triplist.remove(trip);
    }
}

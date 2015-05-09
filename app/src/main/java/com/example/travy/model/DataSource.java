package com.example.travy.model;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public List<Trip> findAllTrip(){
        List<Trip> triplist = new ArrayList<Trip>();
        Trip t1 = new Trip(1, "New York City");
        Trip t2 = new Trip(2, "Beijing");

        triplist.add(t1);
        triplist.add(t2);
        return triplist;
    }

}

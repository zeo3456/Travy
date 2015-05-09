package com.example.travy;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;
import android.widget.ArrayAdapter;

import com.example.travy.model.DataSource;
import com.example.travy.model.Trip;

import java.util.List;

public class TripActivity extends ListActivity {

    private DataSource dataSource;
    List<Trip> tripList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_trip);
        dataSource = new DataSource();
        refreshDisplay();

    }

    private void refreshDisplay(){
        tripList = dataSource.findAllTrip();
        ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this,android.R.layout.simple_list_item_1,tripList);
        setListAdapter(adapter);
    }

}

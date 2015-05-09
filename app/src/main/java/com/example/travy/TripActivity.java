package com.example.travy;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.travy.model.DataSource;
import com.example.travy.model.Trip;

import java.util.List;

public class TripActivity extends ListActivity {
    private static final int MENU_DELETE_ID = 1002;
    private int currentNoteId;
    private DataSource dataSource;
    List<Trip> tripList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        registerForContextMenu(getListView());
        setContentView(R.layout.activity_trip);
        dataSource = new DataSource();
        refreshDisplay();

    }

    private void refreshDisplay() {
        tripList = dataSource.findAllTrip();
        ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this, android.R.layout.simple_list_item_1, tripList);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //get the reference to the list item that is selected
        Trip trip = tripList.get(position);
        Intent intent = new Intent(this, TripDetailActivity.class);
        startActivityForResult(intent, 1001);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) menuInfo;
        currentNoteId = (int) info.id;//indicate which item i want to delete
        menu.add(0, MENU_DELETE_ID, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_DELETE_ID) {
            //DELETE
            Trip trip = tripList.get(currentNoteId);
            dataSource.remove(trip);
            refreshDisplay();
        }
        return super.onContextItemSelected(item);
    }

    public void trip_detail() {

    }

}

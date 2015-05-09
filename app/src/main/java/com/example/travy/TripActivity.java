package com.example.travy;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

import com.example.travy.model.DataSource;
import com.example.travy.model.Trip;

import java.util.List;

public class TripActivity extends ListActivity {

    private DataSource dataSource;
    List<Trip> tripList;
    ListView listView;
    private static final String TAG = "NEW BUTTON TEST";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_trip);
        dataSource = new DataSource();
        refreshDisplay();

        registerForContextMenu(getListView());
    }

    public void showPopWindow(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.new_trip, null);
        Button button_close = (Button) popupView.findViewById(R.id.button_close);
        Button button_add = (Button) popupView.findViewById(R.id.button_add);
        final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        button_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        button_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();
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
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "DELETE");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0)
            return true;
        else
            return false;
    }

}

package com.example.travy;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.travy.model.DataSource;
import com.example.travy.model.Site;
import com.example.travy.model.SiteSource;
import com.example.travy.model.Trip;
import com.example.travy.model.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TripActivity extends ListActivity {

    private DataSource dataSource;
    List<Trip> tripList;
    ListView listView;
    private static final String TAG = "NEW BUTTON TEST";
    public static String SelectedTripName;
    public static String deletedTrip;
    public static int posPos = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_trip);
        dataSource = new DataSource();
        refreshDisplay();

        registerForContextMenu(getListView());

        setupUI(findViewById(R.id.layout));

    }

    public void showPopWindow(View view) {
//        final boolean[] goOn = {false};
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//        alert.setTitle("Trip You wanna add ?");
//        alert.setMessage("For example, Moon, the Milky Way ...");
//
//// Set an EditText view to get user input
//        final EditText input = new EditText(this);
//        alert.setView(input);
//
//        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                 goOn[0] = true;
//                // Do something with value!
//                dialog.cancel();
//            }
//        });
//
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // Canceled.
//                dialog.cancel();
//            }
//        });
//
//        alert.show();
        final LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.new_trip, null);
        setupUI(popupView);
        final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        Button button_add = (Button) popupView.findViewById(R.id.button_add);
        Button button_close = (Button) popupView.findViewById(R.id.button_close);

        button_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.setContentView(layoutInflater.inflate(R.layout.new_trip, null, false));
                final EditText userInput = (EditText) popupView.findViewById(R.id.TPname);
                String input = userInput.getText().toString();
                if (input.trim().isEmpty()) {
                    input = "untitled";
                }
                try {
                    Utility.AddToFile(Utility.GetFilePlace("TripUser.txt"), User.getID(LoginActivity.getCurrentUser()) + " " + input);
                    Trip t = new Trip(Integer.valueOf(User.getID(LoginActivity.getCurrentUser())), input);
                    DataSource.addtrip(t);
                    refreshDisplay();
                    Utility.GetFilePlace(User.getID(LoginActivity.getCurrentUser()) + input + ".txt");
//                     Log.i("WTF!", "!!!");
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                popupWindow.dismiss();
            }
        });

        button_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setWidth(800);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, -100);
        popupWindow.update();

    }

    private void refreshDisplay() {
        tripList = dataSource.findAllTrip();
        ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this, android.R.layout.simple_list_item_1, tripList);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//        SiteSource st = new SiteSource();
        if (SiteSource.getSize() != 0) {
            SiteSource.clearSite();
        }
        //get the reference to the list item that is selected
        Trip trip = tripList.get(position);
        SelectedTripName = trip.getTitle();
//        if(SiteSource.getSize()!=0){
//            SiteSource.clearSite();
//        }
        //load Sites
        String fileName = User.getID(LoginActivity.getCurrentUser()) + SelectedTripName + ".txt";
        File file = Utility.GetFilePlace(fileName);
        String allInfo = "";
        try {
            allInfo = Utility.GetAllInfoFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] EachTrip = allInfo.split(" & ");
        for (int i = 0; i < EachTrip.length; i++) {
            if (EachTrip[i].trim().isEmpty()) continue;
            String[] EachTripInfo = EachTrip[i].split(" ");
            String SiteName = EachTripInfo[0].trim();
            Site t = new Site(SiteName);
            SiteSource.addSite(t);
        }

        Intent intent = new Intent(this, TripDetailActivity.class);
        startActivityForResult(intent, 1001);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo pos = (AdapterView.AdapterContextMenuInfo) menuInfo;
        deletedTrip = DataSource.getTripName(pos.position);
        posPos = pos.position;

        menu.add(1, 1, 1, "Delete");

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem clickedItem) {
                DataSource.removeTrip(posPos);
                try {
                    Utility.DeleteFromFile(Utility.GetFilePlace("TripUser.txt"), User.getID(LoginActivity.getCurrentUser()), deletedTrip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Utility.GetFilePlace(User.getID(LoginActivity.getCurrentUser()) + deletedTrip + ".txt").exists()) {
                    Utility.GetFilePlace(User.getID(LoginActivity.getCurrentUser()) + deletedTrip + ".txt").delete();
                }
                refreshDisplay();
                return true;
            }
        });

    }

    public void toUserInfo(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(TripActivity.this);
                    return false;
                }
            });
        }
    }
}

package com.example.travy;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    ActionBar actionBar;
    Fragment listFragmentTab = new ListFragmentTab();
    Fragment mapFragmentTab = new MapFragmentTab();

    public static FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.addTab(actionBar.newTab().setText("Map").setTabListener(new TabListener(mapFragmentTab)));
        actionBar.addTab(actionBar.newTab().setText("List").setTabListener(new TabListener(listFragmentTab)));

    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
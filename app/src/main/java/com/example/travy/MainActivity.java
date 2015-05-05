package com.example.travy;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;

public class MainActivity extends Activity {

    ActionBar.Tab listTab, mapTab;
    Fragment listFragmentTab = new ListFragmentTab();
    Fragment mapFragmentTab = new MapFragmentTab();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        listTab = actionBar.newTab().setText("List");
        listTab.setTabListener(new TabListener(listFragmentTab));

        mapTab = actionBar.newTab().setText("Map");
        mapTab.setTabListener(new TabListener(mapFragmentTab));

        actionBar.addTab(listTab);
        actionBar.addTab(mapTab);
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
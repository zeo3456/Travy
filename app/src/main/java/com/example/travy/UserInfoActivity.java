package com.example.travy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class UserInfoActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_userinfo);

        TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(LoginActivity.getCurrentUser());
    }

    public void toTrip(View view) {
        Intent intent = new Intent(this, TripActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

    public void signOut(View view) {
        Intent intent = new Intent(this, FirstActivity.class);
        LoginActivity.currentUser = "";
        startActivity(intent);
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, TripActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

}
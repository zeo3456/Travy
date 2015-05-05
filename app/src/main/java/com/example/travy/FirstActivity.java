package com.example.travy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.example.travy.Model.Backend;
import com.example.travy.Model.User;
import java.util.List;

public class FirstActivity extends Activity {

    private static final String TAG = "FirstActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_first);
    }

    public void signup(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /* API Methods */
    private void login(String email, String password) {
        final Context currContext = this;
        Log.d(TAG, "Attempting to login with email: " + email + " password: " + password);
        Backend.login(email, password, new com.example.travy.Model.Backend.BackendCallback() {
            @Override
            public void onRequestCompleted(Object result) {
                final User user = (User) result;
                Log.d(TAG, "Login success. User: " + user.toString());

                runOnUiThread(new Runnable() {
                    public void run() {
                        //Must check db for user with existing backendId.  If doesn't already exist, then save
                        //.find is how to query for objects saved with Sugar
                        List<User> users = User.find(User.class, "backend_id = ?", new Integer(user.backendId).toString());
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(currContext);
                        SharedPreferences.Editor editor = prefs.edit();
                        if (users.size() == 0) {
                            user.save();
                            //This is NOT the backend id, this is the sugar id
                            editor.putString("loggedInId", Long.toString(user.getId()));
                            editor.commit();
                        } else {
                            User currUser = users.get(0);
                            currUser.authToken = user.authToken;
                            currUser.tokenExpiration = user.tokenExpiration;
                            currUser.save();
                            //This is NOT the backend id, this is the sugar id
                            editor.putString("loggedInId", Long.toString(currUser.getId()));
                            editor.commit();
                        }
                        Intent intent = new Intent(currContext, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onRequestFailed(final String message) {
                //NOTE: parameter validation and filtering is handled by the backend, just show the
                //returned error message to the user
                Log.d(TAG, "Received error from Backend: " + message);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

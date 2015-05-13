package com.example.travy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travy.model.DataSource;
import com.example.travy.model.Trip;
import com.example.travy.model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SignupActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_signup);

        setupUI(findViewById(R.id.signUpParent));
    }

    public void back(View view) {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

    public void signup(View view) {
        boolean NotExistSoGoOn = true;
        Intent intent = new Intent(this, TripActivity.class);
        final Intent intent2 = new Intent(this, LoginActivity.class);
        EditText inputOfAdd = (EditText) findViewById(R.id.emailaddress);

        EditText inputOfPW = (EditText) findViewById(R.id.password);
        EditText inputOfFirstName = (EditText) findViewById(R.id.firstname);
        EditText inputOfLastName = (EditText) findViewById(R.id.lastname);

        String myEmail = inputOfAdd.getText().toString();
        String myName = inputOfFirstName.getText().toString() + " " + inputOfLastName.getText().toString();
        String myPW = inputOfPW.getText().toString();
        if (inputOfFirstName.getText().toString().isEmpty() || inputOfLastName.getText().toString().isEmpty() || myPW.isEmpty() || myEmail.isEmpty()) {
            NotExistSoGoOn = false;
            new AlertDialog.Builder(this)
                    .setTitle("Invalid Field")
                    .setMessage("You must fill in all fields, or feel my wrath!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        int id = 0;

        if (Utility.FindExistUser(Utility.GetFilePlace("UserName.txt"), myEmail)) {
            NotExistSoGoOn = false;
            new AlertDialog.Builder(this)
                    .setTitle("Exist User")
                    .setMessage("Seems you already registered. Wanna Log in now?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(intent2);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


        if (NotExistSoGoOn) {
            id = (int) (Math.random() * 100);
//        try {
//            myPW = SimpleCrypto.encrypt("travy", myPW);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            String info = myEmail + " " + myPW + " " + myName + " " + id;

            //add to the user list
            File file2;
            try {
                file2 = Utility.GetFilePlace("UserName.txt");
                Scanner in2 = new Scanner(file2);
                String OriginalNames = "";
                while (in2.hasNext()) {
                    OriginalNames += (in2.next() + " ");

                }
                OriginalNames += myEmail + " ";
                FileWriter filewriter = new FileWriter(file2);
                BufferedWriter out = new BufferedWriter(filewriter);
                out.write(OriginalNames);
                out.close();
            } catch (IOException e) {
                Log.w("!!!!", e.getMessage(), e);
                Toast.makeText(view.getContext(), e.getMessage() + " Unable to write to external storage.",
                        Toast.LENGTH_LONG).show();
            }


            //add the user
            File file;
            try {
                file = Utility.GetFilePlace("LogIn.txt");
                Utility.AddToFile(file, info);
                User.addUser(myEmail, id, myName, myPW);
                LoginActivity.currentUser = myEmail;
                DataSource.clearTrip();
            } catch (IOException e) {
                Log.w("!!!!", e.getMessage(), e);
                Toast.makeText(view.getContext(), e.getMessage() + " Unable to write to external storage.",
                        Toast.LENGTH_LONG).show();
            }


            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
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
                    hideSoftKeyboard(SignupActivity.this);
                    return false;
                }
            });
        }
    }

}

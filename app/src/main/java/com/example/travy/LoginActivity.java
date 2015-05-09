package com.example.travy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.travy.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginActivity extends Activity {
    public static String currentUser = "";
    public static String getCurrentUser() {
        return currentUser;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);
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

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        final Intent intent2 = new Intent(this, SignupActivity.class);
        final Intent intent3 = new Intent(this, FirstActivity.class);
        boolean ExistSoGoOn = true;
        EditText inputOfAdd   = (EditText)findViewById(R.id.LIemailaddress);
        EditText inputOfPW   = (EditText)findViewById(R.id.LIpassword);
        String myEmail = inputOfAdd.getText().toString();
        String myPW = inputOfPW.getText().toString();

        if(!Utility.FindExistUser(Utility.GetFilePlace("UserName.txt"), myEmail))
        {
            ExistSoGoOn = false;
            new AlertDialog.Builder(this)
                    .setTitle("Not Exist User")
                    .setMessage("Seems you haven't registered. Wanna sign up now?")
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
        currentUser = myEmail;
        boolean PWcorrect = true;
        if(ExistSoGoOn) {
            //load user information from database
            File file = Utility.GetFilePlace("LogIn.txt");
            Scanner in = null;
            try {
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String allinfo = "";
            while (in.hasNext()) {
                allinfo += in.next() + " ";
            }
            String [] EachUser = allinfo.split(" & ");
            for(int i = 0 ; i< EachUser.length;i++){
                if(EachUser[i].trim().isEmpty()) continue;
                String [] EachUserInfo = EachUser[i].split(" ");
                User.addUser(EachUserInfo[0], Integer.valueOf(EachUserInfo[4]), EachUserInfo[2] + " " + EachUserInfo[3], EachUserInfo[1]);
            }
            String correctPW = User.getPW(myEmail);
            if(!myPW.equals(correctPW)) {
                PWcorrect = false;
                new AlertDialog.Builder(this)
                        .setTitle("PW Wrong")
                        .setMessage("Your password is wrong. Wanna try it again?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(intent3);
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

            if(PWcorrect) {


                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }

    }
}
package com.example.vhsince81;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences ref;

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        Thread timerThread = new Thread()
        {
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    ref = getSharedPreferences("login", MODE_PRIVATE);
                    boolean isLogged = ref.getBoolean("islogged",false);
                    String email  = ref.getString("Email","");

                    if (isLogged && (email.equals(getString(R.string.admin_mail)))) {
                        Intent intent = new Intent(SplashScreen.this, AdminHomePage.class);
                        startActivity(intent);
                        finish();
                    } else if (isLogged && (!email.equals(""))) {
                        Intent intent = new Intent(SplashScreen.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

        };
        timerThread.start();
    }
}

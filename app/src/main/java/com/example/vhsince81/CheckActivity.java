package com.example.vhsince81;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CheckActivity extends AppCompatActivity {


    boolean clicked = true;
    public void onCreate(Bundle instance)
    {
        super.onCreate(instance);
        setContentView(R.layout.check_activity);


        Button btn = (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  if (clicked) {
                    Intent i = new Intent(CheckActivity.this, SplashScreen.class);
                    startActivity(i);
                    finish();
                    clicked = false;
                 }
            }
        });

    }
}

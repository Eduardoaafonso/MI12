package com.example.eduardo.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

public class TutoConf extends AppCompatActivity {
    private static final String TAG = "ConfiguringTutorial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_conf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.conf_tb);
        setSupportActionBar(toolbar);

        View view = findViewById(R.id.next);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TutoConf.this, com.example.eduardo.myapplication.TutoGam.class);
                startActivity(it);

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
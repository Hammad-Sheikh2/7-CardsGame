package com.hammadshahzad.mcsmesterproject7cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Statictics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statictics);
	getSupportActionBar().hide();
    }
}
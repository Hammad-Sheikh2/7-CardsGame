package com.hammadshahzad.mcsmesterproject7cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	getSupportActionBar().hide();
    }

    public void btnAbout_Clicked(View view) {
        Intent activity = new Intent(MainActivity.this,AboutApplication.class);
        startActivity(activity);
    }

    public void BtnStats_Clicked(View view) {
        Intent activity = new Intent(MainActivity.this,Statictics.class);
        startActivity(activity);
    }

    public void BtnStart_Click(View view) {
        Intent activity = new Intent(MainActivity.this,Game.class);
        startActivity(activity);
        finish();
    }
}
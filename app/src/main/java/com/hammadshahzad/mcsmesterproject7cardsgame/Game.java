package com.hammadshahzad.mcsmesterproject7cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
	getSupportActionBar().hide();
    }
    public void BtnBack_Click(View view) {
        Intent activity = new Intent(Game.this,MainActivity.class);
        startActivity(activity);
        finish();
    }

    public void BtnRestart_Click(View view) {
    }
}
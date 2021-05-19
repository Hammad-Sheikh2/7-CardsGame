package com.hammadshahzad.mcsmesterproject7cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;



public class Game extends AppCompatActivity {
    Card onTopCard;
    ArrayList<Card> drawPile = new ArrayList<Card>();
    ArrayList<ArrayList<Card>> Players = new ArrayList<ArrayList<Card>>();
    ArrayList<Card> PlayableCards=new ArrayList<Card>();
    ArrayList<Boolean> IsPlayerInGame=new ArrayList<Boolean>();
    boolean isGameFirstTurn;
    boolean isTurnSkipped;
    int currentPlayer;
    int recentContinuousPlayed2s =0;
    String orderShape;
    boolean IsMeWin=false;
    boolean IsMineTurnInProgress=false;
    long RemainingTime=0;
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
    @Override
    protected void onStart() {
        super.onStart();
        //Initialization of Deck
        //Spades
        drawPile.add(new Card("Spades","2"));
        drawPile.add(new Card("Spades","3"));
        drawPile.add(new Card("Spades","4"));
        drawPile.add(new Card("Spades","5"));
        drawPile.add(new Card("Spades","6"));
        drawPile.add(new Card("Spades","7"));
        drawPile.add(new Card("Spades","8"));
        drawPile.add(new Card("Spades","9"));
        drawPile.add(new Card("Spades","10"));
        drawPile.add(new Card("Spades","J"));
        drawPile.add(new Card("Spades","Q"));
        drawPile.add(new Card("Spades","K"));
        drawPile.add(new Card("Spades","A"));
        //Clubs
        drawPile.add(new Card("Clubs","2"));
        drawPile.add(new Card("Clubs","3"));
        drawPile.add(new Card("Clubs","4"));
        drawPile.add(new Card("Clubs","5"));
        drawPile.add(new Card("Clubs","6"));
        drawPile.add(new Card("Clubs","7"));
        drawPile.add(new Card("Clubs","8"));
        drawPile.add(new Card("Clubs","9"));
        drawPile.add(new Card("Clubs","10"));
        drawPile.add(new Card("Clubs","J"));
        drawPile.add(new Card("Clubs","Q"));
        drawPile.add(new Card("Clubs","K"));
        drawPile.add(new Card("Clubs","A"));
        //Hearts
        drawPile.add(new Card("Hearts","2"));
        drawPile.add(new Card("Hearts","3"));
        drawPile.add(new Card("Hearts","4"));
        drawPile.add(new Card("Hearts","5"));
        drawPile.add(new Card("Hearts","6"));
        drawPile.add(new Card("Hearts","7"));
        drawPile.add(new Card("Hearts","8"));
        drawPile.add(new Card("Hearts","9"));
        drawPile.add(new Card("Hearts","10"));
        drawPile.add(new Card("Hearts","J"));
        drawPile.add(new Card("Hearts","Q"));
        drawPile.add(new Card("Hearts","K"));
        drawPile.add(new Card("Hearts","A"));
        //Diamonds
        drawPile.add(new Card("Diamonds","2"));
        drawPile.add(new Card("Diamonds","3"));
        drawPile.add(new Card("Diamonds","4"));
        drawPile.add(new Card("Diamonds","5"));
        drawPile.add(new Card("Diamonds","6"));
        drawPile.add(new Card("Diamonds","7"));
        drawPile.add(new Card("Diamonds","8"));
        drawPile.add(new Card("Diamonds","9"));
        drawPile.add(new Card("Diamonds","10"));
        drawPile.add(new Card("Diamonds","J"));
        drawPile.add(new Card("Diamonds","Q"));
        drawPile.add(new Card("Diamonds","K"));
        drawPile.add(new Card("Diamonds","A"));

        //Shuffle Cards
        ShuffleDrawPile();

        //Initialize 4 Players
        for (int i=0;i<4;i++)
            Players.add(new ArrayList<Card>());

        //Distribute 7 Card to Each Player At Start.
        for (int i=0;i<7;i++)
        {
            ArrayList<Card> temp;
            temp = Players.get(0);
            temp.add(drawPile.get(0));
            Players.set(0,temp);
            drawPile.remove(0);
            temp = Players.get(1);
            temp.add(drawPile.get(0));
            Players.set(1,temp);
            drawPile.remove(0);
            temp = Players.get(2);
            temp.add(drawPile.get(0));
            Players.set(2,temp);
            drawPile.remove(0);
            temp = Players.get(3);
            temp.add(drawPile.get(0));
            Players.set(3,temp);
            drawPile.remove(0);
        }

        //Put one Card on Top From Draw Pile
        onTopCard=drawPile.get(0);
        drawPile.remove(0);

        //Misc
        for (int i=0;i<4;i++)
        {
            IsPlayerInGame.add(true);
        }
        isTurnSkipped=false;
        isGameFirstTurn=true;
        //todo Randomly
        currentPlayer=0;

        //Showing onTop Card.
        ((ImageView)findViewById(R.id.ImgVTopCardShape)).setImageResource(GetShapeImage(onTopCard.Shape));
        ((TextView)findViewById(R.id.TVTopCardNumber)).setText(onTopCard.Number);

        //Showing All Players Card Count
        ((TextView)findViewById(R.id.TVPlayer1Cards)).setText(Players.get(0).size()+"");
        ((TextView)findViewById(R.id.TVPlayer2Cards)).setText(Players.get(1).size()+"");
        ((TextView)findViewById(R.id.TVPlayer3Cards)).setText(Players.get(2).size()+"");
        ((TextView)findViewById(R.id.TVPlayerMeCards)).setText(Players.get(3).size()+"");
	Turn();
    }
    void Turn(){
        
    }
}
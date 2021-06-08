package com.hammadshahzad.mcsmesterproject7cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


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

        new CountDownTimer(12100,4000){
            public void onTick(long millisUntilFinished){
                if((currentPlayer==3)){
                    cancel();
                    myTurn();
                }
                else {
                    Turn();
                }
            }
            public  void onFinish(){

            }
        }.start();
    }

    public void BtnBack_Click(View view) {
        Intent activity = new Intent(Game.this,MainActivity.class);
        startActivity(activity);
        finish();
    }

    public void BtnRestart_Click(View view) {
    }
    void Turn(){
        //Free Resource Of Playable Cards
        PlayableCards.clear();
        //Animate Current Player
        switch(currentPlayer) {
            case 0:
                findViewById(R.id.RLPlayer1Box).animate().scaleXBy(-0.3f).scaleYBy(-0.3f).setDuration(1500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.RLPlayer1Box).animate().scaleXBy(0.3f).scaleYBy(0.3f).setDuration(1500);
                    }
                });
                break;
            case 1:
                findViewById(R.id.RLPlayer2Box).animate().scaleXBy(-0.3f).scaleYBy(-0.3f).setDuration(1500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.RLPlayer2Box).animate().scaleXBy(0.3f).scaleYBy(0.3f).setDuration(1500);
                    }
                });
                break;
            case 2:
                findViewById(R.id.RLPlayer3Box).animate().scaleXBy(-0.3f).scaleYBy(-0.3f).setDuration(1500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.RLPlayer3Box).animate().scaleXBy(0.3f).scaleYBy(0.3f).setDuration(1500);
                    }
                });
                break;
        }
        //Turn
        //After Playing Card check currentPlayer remains in Game or not.
        new CountDownTimer(3000,1000){
            public void onTick(long millisUntilFinished){

            }
            public  void onFinish(){
                //Bot Turn
                if(IsPlayerInGame.get(currentPlayer)) {
                    //Check Special Cards first
                    if(!isGameFirstTurn) {
                        if((onTopCard.Number.equals("2"))&&(recentContinuousPlayed2s > 0)) {
                            //If Player Have 2 in his Cards then Play it.
                            for(int i=0; i < Players.get(currentPlayer).size();i++) {
                                if(Players.get(currentPlayer).get(i).Number.equals(onTopCard.Number)){
                                    Card tempCard=Players.get(currentPlayer).get(i);
                                    ArrayList<Card> temp;
                                    temp = Players.get(currentPlayer);
                                    temp.remove(temp.get(i));
                                    Players.set(currentPlayer,temp);
                                    if(Players.get(currentPlayer).size()==0){
                                        IsPlayerInGame.set(currentPlayer,false);
                                    }
                                    TurnCommonPart(tempCard);
                                    recentContinuousPlayed2s++;
                                    return;
                                }
                            }
                            //If not then Draw Card * recentContinousPlayed2s fromDrawPile and Turn Skipped.
                            for(int i = 0; i< recentContinuousPlayed2s * 2; i++) {
                                ArrayList<Card> temp;
                                temp = Players.get(currentPlayer);
                                temp.add(drawPile.get(0));
                                drawPile.remove(0);
                                Players.set(currentPlayer, temp);
                            }
                            currentPlayer=(currentPlayer+1)%4;
                            ChangeEachPlayerCardCount();
                            recentContinuousPlayed2s =0;
                            return;
                        }
                        if((onTopCard.Number.equals("8"))&&(!isTurnSkipped)){
                            for(int i=0; i < Players.get(currentPlayer).size();i++) {
                                if(Players.get(currentPlayer).get(i).Number.equals(onTopCard.Number)){
                                    Card tempCard=Players.get(currentPlayer).get(i);
                                    ArrayList<Card> temp;
                                    temp = Players.get(currentPlayer);
                                    temp.remove(i);
                                    Players.set(currentPlayer,temp);
                                    if(Players.get(currentPlayer).size()==0){
                                        IsPlayerInGame.set(currentPlayer,false);
                                    }
                                    TurnCommonPart(tempCard);
                                    isTurnSkipped=false;
                                    return;
                                }
                            }
                            //Special Card 8 used Only for Next One Player
                            currentPlayer=(currentPlayer+1)%4;
                            isTurnSkipped=true;
                            return;
                        }
                        if(onTopCard.Number.equals("J")){
                            //Get Playable Cards based on OrderShape.
                            ArrayList<Integer> index=new ArrayList<Integer>();
                            for(int i=0; i < Players.get(currentPlayer).size();i++) {
                                if(Players.get(currentPlayer).get(i).Shape.equals(orderShape)){
                                    PlayableCards.add(Players.get(currentPlayer).get(i));
                                    index.add(i);
                                }
                            }
                            //Draw Card If not Found.
                            if(PlayableCards.size()==0){
                                ArrayList<Card> temp;
                                temp = Players.get(currentPlayer);
                                temp.add(drawPile.get(0));
                                drawPile.remove(0);
                                Players.set(currentPlayer, temp);
                                currentPlayer=(currentPlayer+1)%4;
                                ChangeEachPlayerCardCount();
                                return;
                            }
                            //Randomly Select One Card from Playable Cards and Play it.
                            ((ImageView)findViewById(R.id.ImgOrderShape)).setImageResource(R.drawable.not_available_circle);
                            Random random = new Random();
                            int selected=random.nextInt(PlayableCards.size());
                            int previousPlayer=currentPlayer;
                            ArrayList<Card> temp;
                            temp = Players.get(currentPlayer);
                            temp.remove(index.get(selected));
                            Players.set(currentPlayer,temp);
                            if(Players.get(currentPlayer).size()==0){
                                IsPlayerInGame.set(currentPlayer,false);
                            }
                            TurnCommonPart(PlayableCards.get(selected));
                            //If Selected Card is 2 then recentContinuousPlayed2s++
                            if(PlayableCards.get(selected).Number.equals("2")) {
                                recentContinuousPlayed2s++;
                            }
                            //check is card 8
                            if(PlayableCards.get(selected).Number.equals("8")) {
                                isTurnSkipped=false;
                            }
                            //If Selected Card is J then Take Order.
                            if(PlayableCards.get(selected).Number.equals("J")) {
                                switch (Players.get(previousPlayer).get(0).Shape)
                                {
                                    case "Spades":
                                        orderShape="Spades";
                                        break;
                                    case "Hearts":
                                        orderShape="Hearts";
                                        break;
                                    case "Clubs":
                                        orderShape="Clubs";
                                        break;
                                    case "Diamonds":
                                        orderShape="Diamonds";
                                        break;
                                }
                                ((ImageView)findViewById(R.id.ImgOrderShape)).setImageResource(GetShapeImage(orderShape));
                            }
                            return;
                        }
                    }
                    //Just Play Normal Turn Skip Special Cards Part.
                    ArrayList<Integer> index=new ArrayList<Integer>();
                    for(int i=0; i < Players.get(currentPlayer).size();i++) {
                        if(Players.get(currentPlayer).get(i).Shape.equals(onTopCard.Shape)||Players.get(currentPlayer).get(i).Number.equals(onTopCard.Number)){
                            PlayableCards.add(Players.get(currentPlayer).get(i));
                            index.add(i);
                        }
                    }
                    //Draw Card If not Found.
                    if(PlayableCards.size()==0){
                        ArrayList<Card> temp;
                        temp = Players.get(currentPlayer);
                        temp.add(drawPile.get(0));
                        drawPile.remove(0);
                        Players.set(currentPlayer, temp);
                        currentPlayer=(currentPlayer+1)%4;
                        ChangeEachPlayerCardCount();
                        return;
                    }
                    //Randomly Select One Card from Playable Cards and Play it.
                    Random random = new Random();
                    int selected=random.nextInt(PlayableCards.size());
                    int previousPlayer=currentPlayer;
                    ArrayList<Card> temp = Players.get(currentPlayer);
                    boolean a= temp.remove(PlayableCards.get(selected));
                    Players.set(currentPlayer,temp);
                    if(Players.get(currentPlayer).size()==0){
                        IsPlayerInGame.set(currentPlayer,false);
                    }
                    TurnCommonPart(PlayableCards.get(selected));
                    //If Selected Card is 2 then recentContinuousPlayed2s++
                    if(PlayableCards.get(selected).Number.equals("2")) {
                        recentContinuousPlayed2s++;
                    }
                    //check is card 8
                    if(PlayableCards.get(selected).Number.equals("8")) {
                        isTurnSkipped=false;
                    }
                    //If Selected Card is J then Take Order.
                    if(PlayableCards.get(selected).Number.equals("J")) {
                        switch (Players.get(previousPlayer).get(0).Shape)
                        {
                            case "Spades":
                                orderShape="Spades";
                                break;
                            case "Hearts":
                                orderShape="Hearts";
                                break;
                            case "Clubs":
                                orderShape="Clubs";
                                break;
                            case "Diamonds":
                                orderShape="Diamonds";
                                break;
                        }
                        ((ImageView)findViewById(R.id.ImgOrderShape)).setImageResource(GetShapeImage(orderShape));
                    }
                    return;
                }
                else {
                    //Skip Player
                    currentPlayer=(currentPlayer+1)%4;
                }

            }
        }.start();
        isGameFirstTurn=false;
    }
    void myTurn(){
        //Free Resource Of Playable Cards
        PlayableCards.clear();
        //Animate Current Player
        findViewById(R.id.RLPlayerMeBox).animate().scaleXBy(-0.3f).scaleYBy(-0.3f).setDuration(1500).withEndAction(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.RLPlayerMeBox).animate().scaleXBy(0.3f).scaleYBy(0.3f).setDuration(1500);
            }
        });
        //Turn
        //After Playing Card check currentPlayer remains in Game or not.
        new CountDownTimer(3000,1000){
            public void onTick(long millisUntilFinished){

            }
            public  void onFinish(){
                //Bot Turn
                if(IsPlayerInGame.get(currentPlayer)) {
                    //Check Special Cards first
                    if(!isGameFirstTurn) {
                        if((onTopCard.Number.equals("2"))&&(recentContinuousPlayed2s > 0)) {
                            //If Player Have 2 in his Cards then Play it.
                            for(int i=0; i < Players.get(currentPlayer).size();i++) {
                                if(Players.get(currentPlayer).get(i).Number.equals(onTopCard.Number)){
                                    Card tempCard=Players.get(currentPlayer).get(i);
                                    ArrayList<Card> temp;
                                    temp = Players.get(currentPlayer);
                                    temp.remove(temp.get(i));
                                    Players.set(currentPlayer,temp);
                                    if(Players.get(currentPlayer).size()==0){
                                        IsPlayerInGame.set(currentPlayer,false);
                                    }
                                    TurnCommonPart(tempCard);
                                    recentContinuousPlayed2s++;
                                    new CountDownTimer(12100,4000){
                                        public void onTick(long millisUntilFinished){
                                            if((currentPlayer==3)){
                                                cancel();
                                                myTurn();
                                            }
                                            else {
                                                Turn();
                                            }
                                        }
                                        public  void onFinish(){

                                        }
                                    }.start();
                                    return;
                                }
                            }
                            //If not then Draw Card * recentContinousPlayed2s fromDrawPile and Turn Skipped.
                            for(int i = 0; i< recentContinuousPlayed2s * 2; i++) {
                                ArrayList<Card> temp;
                                temp = Players.get(currentPlayer);
                                temp.add(drawPile.get(0));
                                drawPile.remove(0);
                                Players.set(currentPlayer, temp);
                            }
                            currentPlayer=(currentPlayer+1)%4;
                            ChangeEachPlayerCardCount();
                            recentContinuousPlayed2s =0;
                            new CountDownTimer(12100,4000){
                                public void onTick(long millisUntilFinished){
                                    if((currentPlayer==3)){
                                        cancel();
                                        myTurn();
                                    }
                                    else {
                                        Turn();
                                    }
                                }
                                public  void onFinish(){

                                }
                            }.start();
                            return;
                        }
                        if((onTopCard.Number.equals("8"))&&(!isTurnSkipped)){
                            for(int i=0; i < Players.get(currentPlayer).size();i++) {
                                if(Players.get(currentPlayer).get(i).Number.equals(onTopCard.Number)){
                                    Card tempCard=Players.get(currentPlayer).get(i);
                                    ArrayList<Card> temp;
                                    temp = Players.get(currentPlayer);
                                    temp.remove(i);
                                    Players.set(currentPlayer,temp);
                                    if(Players.get(currentPlayer).size()==0){
                                        IsPlayerInGame.set(currentPlayer,false);
                                         TurnCommonPart(tempCard);
                                         return;
                                    }
                                    TurnCommonPart(tempCard);
                                    isTurnSkipped=false;
                                    new CountDownTimer(12100,4000){
                                        public void onTick(long millisUntilFinished){
                                            if((currentPlayer==3)){
                                                cancel();
                                                myTurn();
                                            }
                                            else {
                                                Turn();
                                            }
                                        }
                                        public  void onFinish(){

                                        }
                                    }.start();
                                    return;
                                }
                            }
                            //Special Card 8 used Only for Next One Player
                            currentPlayer=(currentPlayer+1)%4;
                            isTurnSkipped=true;
                            new CountDownTimer(12100,4000){
                                public void onTick(long millisUntilFinished){
                                    if((currentPlayer==3)){
                                        cancel();
                                        myTurn();
                                    }
                                    else {
                                        Turn();
                                    }
                                }
                                public  void onFinish(){

                                }
                            }.start();
                            return;
                        }
                        if(onTopCard.Number.equals("J")){
                            //Get Playable Cards based on OrderShape.
                            ArrayList<Integer> index=new ArrayList<Integer>();
                            for(int i=0; i < Players.get(currentPlayer).size();i++) {
                                if(Players.get(currentPlayer).get(i).Shape.equals(orderShape)){
                                    PlayableCards.add(Players.get(currentPlayer).get(i));
                                    index.add(i);
                                }
                            }
                            //Draw Card If not Found.
                            if(PlayableCards.size()==0){
                                ArrayList<Card> temp;
                                temp = Players.get(currentPlayer);
                                temp.add(drawPile.get(0));
                                drawPile.remove(0);
                                Players.set(currentPlayer, temp);
                                currentPlayer=(currentPlayer+1)%4;
                                ChangeEachPlayerCardCount();
                                new CountDownTimer(12100,4000){
                                    public void onTick(long millisUntilFinished){
                                        if((currentPlayer==3)){
                                            cancel();
                                            myTurn();
                                        }
                                        else {
                                            Turn();
                                        }
                                    }
                                    public  void onFinish(){

                                    }
                                }.start();
                                return;
                            }
                            //Randomly Select One Card from Playable Cards and Play it.
                            ((ImageView)findViewById(R.id.ImgOrderShape)).setImageResource(R.drawable.not_available_circle);
                            //todo Ye Part change hona
                            for(int i=0;i<PlayableCards.size();i++){
                                View view = getLayoutInflater().inflate(R.layout.card,null);
                                RelativeLayout relativeLayout=view.findViewById(R.id.RLMyCard);
                                ((ImageView)view.findViewById(R.id.ImgVCardShape)).setImageResource(GetShapeImage(PlayableCards.get(i).Shape));
                                ((TextView)view.findViewById(R.id.TVCardNumber)).setText(PlayableCards.get(i).Number);
                                int finalI = i;
                                relativeLayout.setOnClickListener(view1 -> {
                                    int selected=index.get(finalI);
                                    int previousPlayer=currentPlayer;
                                    ArrayList<Card> temp;
                                    temp = Players.get(currentPlayer);
                                    temp.remove(selected);
                                    Players.set(currentPlayer,temp);
                                    if(Players.get(currentPlayer).size()==0){
                                        IsPlayerInGame.set(currentPlayer,false);
                                        TurnCommonPart(PlayableCards.get(finalI));
                                        return;   
                                    }
                                    TurnCommonPart(PlayableCards.get(finalI));
                                    //If Selected Card is 2 then recentContinuousPlayed2s++
                                    if(PlayableCards.get(finalI).Number.equals("2")) {
                                        recentContinuousPlayed2s++;
                                    }
                                    //check is card 8
                                    if(PlayableCards.get(finalI).Number.equals("8")) {
                                        isTurnSkipped=false;
                                    }
                                    //If Selected Card is J then Take Order.
                                    if(PlayableCards.get(finalI).Number.equals("J")) {
                                        switch (Players.get(previousPlayer).get(0).Shape)
                                        {
                                            case "Spades":
                                                orderShape="Spades";
                                                break;
                                            case "Hearts":
                                                orderShape="Hearts";
                                                break;
                                            case "Clubs":
                                                orderShape="Clubs";
                                                break;
                                            case "Diamonds":
                                                orderShape="Diamonds";
                                                break;
                                        }
                                        ((ImageView)findViewById(R.id.ImgOrderShape)).setImageResource(GetShapeImage(orderShape));
                                    }
                                    RemoveAllMyCards();
                                    new CountDownTimer(12100,4000){
                                        public void onTick(long millisUntilFinished){
                                            if((currentPlayer==3)){
                                                cancel();
                                                myTurn();
                                            }
                                            else {
                                                Turn();
                                            }
                                        }
                                        public  void onFinish(){

                                        }
                                    }.start();
                                });
                                ((LinearLayout)findViewById(R.id.HSVListPlayableCards)).addView(view);
                            }
                            return;
                        }
                    }
                    //Just Play Normal Turn Skip Special Cards Part.
                    ArrayList<Integer> index=new ArrayList<Integer>();
                    for(int i=0; i < Players.get(currentPlayer).size();i++) {
                        if(Players.get(currentPlayer).get(i).Shape.equals(onTopCard.Shape)||Players.get(currentPlayer).get(i).Number.equals(onTopCard.Number)){
                            PlayableCards.add(Players.get(currentPlayer).get(i));
                            index.add(i);
                        }
                    }
                    //Draw Card If not Found.
                    if(PlayableCards.size()==0){
                        ArrayList<Card> temp;
                        temp = Players.get(currentPlayer);
                        temp.add(drawPile.get(0));
                        drawPile.remove(0);
                        Players.set(currentPlayer, temp);
                        currentPlayer=(currentPlayer+1)%4;
                        ChangeEachPlayerCardCount();
                        new CountDownTimer(12100,4000){
                            public void onTick(long millisUntilFinished){
                                if((currentPlayer==3)){
                                    cancel();
                                    myTurn();
                                }
                                else {
                                    Turn();
                                }
                            }
                            public  void onFinish(){

                            }
                        }.start();
                        return;
                    }
                    //Randomly Select One Card from Playable Cards and Play it.
                    //todo ye bi
                    for(int i=0;i<PlayableCards.size();i++){
                        View view = getLayoutInflater().inflate(R.layout.card,null);
                        RelativeLayout relativeLayout=view.findViewById(R.id.RLMyCard);
                        ((ImageView)view.findViewById(R.id.ImgVCardShape)).setImageResource(GetShapeImage(PlayableCards.get(i).Shape));
                        ((TextView)view.findViewById(R.id.TVCardNumber)).setText(PlayableCards.get(i).Number);
                        int finalI = i;
                        relativeLayout.setOnClickListener(view1 -> {
                            int selected=index.get(finalI);
                            int previousPlayer=currentPlayer;
                            ArrayList<Card> temp;
                            temp = Players.get(currentPlayer);
                            temp.remove(selected);
                            Players.set(currentPlayer,temp);
                            if(Players.get(currentPlayer).size()==0){
                                IsPlayerInGame.set(currentPlayer,false);
                                TurnCommonPart(PlayableCards.get(finalI));
                                return;
                            }
                            TurnCommonPart(PlayableCards.get(finalI));
                            //If Selected Card is 2 then recentContinuousPlayed2s++
                            if(PlayableCards.get(finalI).Number.equals("2")) {
                                recentContinuousPlayed2s++;
                            }
                            //check is card 8
                            if(PlayableCards.get(finalI).Number.equals("8")) {
                                isTurnSkipped=false;
                            }
                            //If Selected Card is J then Take Order.
                            if(PlayableCards.get(finalI).Number.equals("J")) {
                                switch (Players.get(previousPlayer).get(0).Shape)
                                {
                                    case "Spades":
                                        orderShape="Spades";
                                        break;
                                    case "Hearts":
                                        orderShape="Hearts";
                                        break;
                                    case "Clubs":
                                        orderShape="Clubs";
                                        break;
                                    case "Diamonds":
                                        orderShape="Diamonds";
                                        break;
                                }
                                ((ImageView)findViewById(R.id.ImgOrderShape)).setImageResource(GetShapeImage(orderShape));
                            }
                            RemoveAllMyCards();
                            new CountDownTimer(12100,4000){
                                public void onTick(long millisUntilFinished){
                                    if((currentPlayer==3)){
                                        cancel();
                                        myTurn();
                                    }
                                    else {
                                        Turn();
                                    }
                                }
                                public  void onFinish(){

                                }
                            }.start();
                        });
                        ((LinearLayout)findViewById(R.id.HSVListPlayableCards)).addView(view);
                    }
                    return;
                }
                else {
                    //Skip Player
                    currentPlayer=(currentPlayer+1)%4;
                    new CountDownTimer(12100,4000){
                        public void onTick(long millisUntilFinished){
                            if((currentPlayer==3)){
                                cancel();
                                myTurn();
                            }
                            else {
                                Turn();
                            }
                        }
                        public  void onFinish(){

                        }
                    }.start();
                }

            }
        }.start();
        isGameFirstTurn=false;
    }
    void RemoveAllMyCards(){
        for (int i = 0;i<PlayableCards.size();i++){
            LinearLayout layout= ((LinearLayout)findViewById(R.id.HSVListPlayableCards));
            layout.removeAllViewsInLayout();
        }
    }
    void TurnCommonPart(Card card){
        drawPile.add(onTopCard);
        onTopCard=card;
        ShuffleDrawPile();
        ChangeEachPlayerCardCount();
        //todo UI Setting
        ((ImageView)findViewById(R.id.ImgVTopCardShape)).setImageResource(GetShapeImage(onTopCard.Shape));
        ((TextView)findViewById(R.id.TVTopCardNumber)).setText(onTopCard.Number);
        currentPlayer=(currentPlayer+1)%4;
    }
    void ChangeEachPlayerCardCount(){
        ((TextView)findViewById(R.id.TVPlayer1Cards)).setText(String.valueOf(Players.get(0).size()));
        ((TextView)findViewById(R.id.TVPlayer2Cards)).setText(String.valueOf(Players.get(1).size()));
        ((TextView)findViewById(R.id.TVPlayer3Cards)).setText(String.valueOf(Players.get(2).size()));
        ((TextView)findViewById(R.id.TVPlayerMeCards)).setText(String.valueOf(Players.get(3).size()));
    }

    void ShuffleDrawPile(){
        for(int i=0;i<drawPile.size()-1;i++)
        {
            Card temp = drawPile.get(i);
            Random random = new Random();
            int randomIndex = i + random.nextInt(drawPile.size() - 1 - i);
            drawPile.set(i,drawPile.get(randomIndex));
            drawPile.set(randomIndex,temp);
        }
    }
    int GetShapeImage(String shape){
        switch (shape){
            case "Spades":
                return R.drawable.spades;
            case "Hearts":
                return R.drawable.like;
            case "Clubs":
                return R.drawable.clubs;
        }
        return R.drawable.diamond;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
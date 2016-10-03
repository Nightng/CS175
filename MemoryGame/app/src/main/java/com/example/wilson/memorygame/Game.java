package com.example.wilson.memorygame;


import android.app.AlertDialog;
import android.content.*;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;



public class Game extends AppCompatActivity {
    static final String STATE_SCORE = "playerScore";
    private ArrayList<ImageButton> l;
    private int score;
    private boolean match;
    private boolean flipOnce;
    private ImageButton ibPrevious;
    private ImageButton ibCurrent;
    private ImageButton ib1;
    TypedArray imgs;
    private boolean didStart;
    private boolean isBusy;
    private final int duration = 750;
    private final Handler handler = new Handler();
    private boolean allCardsVisible;
    private boolean isRearranged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        Log.i("lifecycle", "onCreate called");
        getSupportActionBar().setTitle("");
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(STATE_SCORE);
            if(score == 0) startNew();
            Log.i("lifecycle", "SAVED was called");
        }
        else {
            startNew();
            Log.i("lifecycle", "NEW GAME");
        }
        match = true;
        flipOnce = false;
        Resources res = getResources();
        imgs = res.obtainTypedArray(R.array.imagesArray);
        isBusy = false;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_startOver:
                startNew();
                return true;
            case R.id.action_shuffleCards:
                if(!allCardsVisible) shuffleCards();
                else setRandomImages(l);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startNew()
    {
        Log.i("Called:", "startNew()");
        flipAll();
        score = 0;
        l = new ArrayList<ImageButton>();
        addList(l);
        setRandomImages(l);
        TextView v = (TextView) findViewById(R.id.tvScore);
        v.setText("0");
        didStart = false;
        isRearranged = true;
    }

    public void askUser()
    {
        Log.i("Called:", "askUser()");
        new AlertDialog.Builder(this)
            .setMessage("Do you want to Resume Game or start a New Game?")
                .setCancelable(false)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startNew();
                    }
                })
                .setNegativeButton("Resume Game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
        .show();
    }

    public void addList(ArrayList<ImageButton> l)
    {
        Log.i("Called:", "addList()");
        l.add((ImageButton) findViewById(R.id.imageButton1));
        l.add((ImageButton) findViewById(R.id.imageButton2));
        l.add((ImageButton) findViewById(R.id.imageButton3));
        l.add((ImageButton) findViewById(R.id.imageButton4));
        l.add((ImageButton) findViewById(R.id.imageButton5));
        l.add((ImageButton) findViewById(R.id.imageButton6));
        l.add((ImageButton) findViewById(R.id.imageButton7));
        l.add((ImageButton) findViewById(R.id.imageButton8));
        l.add((ImageButton) findViewById(R.id.imageButton9));
        l.add((ImageButton) findViewById(R.id.imageButton10));
        l.add((ImageButton) findViewById(R.id.imageButton11));
        l.add((ImageButton) findViewById(R.id.imageButton12));
        l.add((ImageButton) findViewById(R.id.imageButton13));
        l.add((ImageButton) findViewById(R.id.imageButton14));
        l.add((ImageButton) findViewById(R.id.imageButton15));
        l.add((ImageButton) findViewById(R.id.imageButton16));
        l.add((ImageButton) findViewById(R.id.imageButton17));
        l.add((ImageButton) findViewById(R.id.imageButton18));
        l.add((ImageButton) findViewById(R.id.imageButton19));
        l.add((ImageButton) findViewById(R.id.imageButton20));
    }

    public void setRandomImages(ArrayList<ImageButton> l)
    {
        Log.i("Called:", "setRandomImages()");
        String[] img = getResources().getStringArray(R.array.imgArray);
        Integer[] arr1 = new Integer[10];
        Integer[] arr2 = new Integer[10];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = i;
            arr2[i] = i;
        }
        int j = 0;
        Collections.shuffle(Arrays.asList(arr1));
        Collections.shuffle(Arrays.asList(arr2));
        for(int i = 0; i < l.size(); i+=2){
            int resID = getResources().getIdentifier(img[arr1[j]], "drawable",  getPackageName());
            l.get(i).setImageResource(resID);
            l.get(i).setTag(resID);
            l.get(i).setVisibility(View.VISIBLE);
            j++;
        }
        j = 0;
        for(int i = 1; i < l.size(); i+=2){
            int resID = getResources().getIdentifier(img[arr2[j]], "drawable",  getPackageName());
            l.get(i).setImageResource(resID);
            l.get(i).setTag(resID);
            l.get(i).setVisibility(View.VISIBLE);
            j++;
        }
        for(int i = 0; i < l.size(); i++) l.get(i).setImageResource(R.drawable.question);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(STATE_SCORE, score);
        Log.i("lifecyle", "SavingInstance called");
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        score = savedInstanceState.getInt(STATE_SCORE);
        Log.i("lifecycle", "onRestore called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecyle", "OnStop called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecyle", "onPause called");

    }

    public void onBackPressed() {
        Log.i("Called:", "onBackPressed()");
        //moveTaskToBack(true);
        Intent intent = new Intent(this, Menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        Log.i("lifecycle", "onBackPressed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(didStart)askUser();
//        didStart = true;
        Log.i("lifecyle", "onResume called");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecyle", "onStart called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecyle", "onDestroy called");
    }

    public void incrementScore(View view){
        Log.i("Called:", "incrementScore()");
        score++;
        TextView tvScore = (TextView) findViewById(R.id.tvScore);
        tvScore.setText(Integer.toString(score));
        if(score % 10 == 0)
        {
            handler.postDelayed( new Runnable(){@Override public void run() {
                isBusy = true;
                setRandomImages(l);
                flipAll();
                isBusy = false;
            }},duration);
        }
        didStart = true;
    }

    public void show(View view) {
        Log.i("Called:", "show()");
        if(isBusy) return;
        ib1 = (ImageButton) findViewById(view.getId());
        flipOutX(ib1, duration);
        ib1.setImageResource((Integer) ib1.getTag());
        flipInX(ib1, duration);
        if (!flipOnce) {
            flipOnce = true;
            ibPrevious = (ImageButton) findViewById(view.getId());
        } else {
            if (ib1.getTag().equals(ibPrevious.getTag()) && ib1.getId() != ibPrevious.getId()) {
                isBusy = true;
                handler.postDelayed( new Runnable(){@Override public void run() {
                    fadeOutDown(ib1, duration);
                    fadeOutDown(ibPrevious, duration);
                    match = true;
                    }},duration);
                    handler.postDelayed( new Runnable(){@Override public void run() {
                        ib1.setVisibility(View.INVISIBLE);
                        ibPrevious.setVisibility(View.INVISIBLE);
                        isBusy = false;
                    }},duration*2);
                allCardsVisible = false;
                incrementScore(view);
                isRearranged = false;
            } else {
                ibCurrent = (ImageButton) findViewById(view.getId());
                match = false;
                isBusy = true;
            }
                flipOnce = false;
            }
        if (!match) {
            handler.postDelayed( new Runnable(){@Override public void run() {
                flipOutX(ibPrevious, duration);
                flipOutX(ibCurrent, duration);
                ibPrevious.setImageResource(R.drawable.question);
                ibCurrent.setImageResource(R.drawable.question);
                flipInX(ibPrevious, duration);
                flipInX(ibCurrent, duration);
                match = true;
                isBusy = false;
            }},duration);
        }
    }

    public void flipOutX(ImageButton ib, int duration)
    {
        Log.i("Called:", "flipOutX()");
        YoYo.with(Techniques.FlipOutX)
                .duration(duration)
                .playOn(ib);
    }

    public void flipInX(ImageButton ib, int duration)
    {
        Log.i("Called:", "flipInX()");
        YoYo.with(Techniques.FlipInX)
                .duration(duration)
                .playOn(ib);
    }

    public void fadeOutDown(ImageButton ib, int duration)
    {
        Log.i("Called:", "fadeOutDown()");
        YoYo.with(Techniques.FadeOutDown)
                .duration(duration/4)
                .playOn(ib);
    }

    public void flipAll()
    {
        Log.i("Called:", "flipAll");
        allCardsVisible = true;
        isBusy = true;
        handler.postDelayed( new Runnable(){@Override public void run() {
            for(int i = 0; i < l.size(); i++){
                flipInX(l.get(i),duration);
                l.get(i).setVisibility(View.VISIBLE);
            }
            isBusy = false;
        }},duration);
    }

    public void shuffleCards()
    {
        Log.i("Called:", "shuffleCards()");
        if(!isRearranged) {
            for (int i = 0; i < l.size(); i++) {
                ImageButton current = l.get(i);
                if (current.getVisibility() == View.INVISIBLE) {
                    int j = i + 1;
                    boolean found = false;
                    while (j < l.size() && !found) {
                        ImageButton next = l.get(j);
                        if (next.getVisibility() == View.VISIBLE) {
                            next.setVisibility(View.INVISIBLE);
                            current.setTag(next.getTag());
                            current.setImageResource(R.drawable.question);
                            current.setVisibility(View.VISIBLE);
                            flipInX(current, duration);
                            found = true;
                        }
                        j++;
                    }
                }
            }
            isRearranged = true;
        }
        int numberOfCards = 20 - countMatchedCards();
        List allImage = new ArrayList<>();
        for(int i = 0; i < numberOfCards; i++)
            allImage.add(l.get(i).getTag());
        Collections.shuffle(allImage);
        for(int i = 0; i < numberOfCards; i++)
            l.get(i).setTag(allImage.get(i));
    }

    private int countMatchedCards()
    {
        int matchedCards = 0;
        for(int i = 0; i < l.size(); i++)
            if(l.get(i).getVisibility() == View.INVISIBLE)
                matchedCards++;
        return matchedCards;
    }

}

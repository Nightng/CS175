package com.example.wilson.fortuneteller;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeText(View view) {
        Resources res = getResources();
        String[] fortune = res.getStringArray(R.array.fortune_array);
        int n = new Random().nextInt(fortune.length);
        TextView tv = (TextView) findViewById(R.id.textView4);
        tv.setText(fortune[n]);
        tv.setVisibility(View.VISIBLE);
    }
}

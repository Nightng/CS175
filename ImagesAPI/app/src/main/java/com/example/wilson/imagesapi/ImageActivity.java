package com.example.wilson.imagesapi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_image);
        Firebase.setAndroidContext(this);
        Intent intent = getIntent();
        url = intent.getExtras().getString("url");
        ImageView b = (ImageView) findViewById(R.id.imageViewImage);
        Picasso.with(this)
                .load(url)
                .resize(4000, 4000)
                .into(b);
    }

    public void onClickSave(View view)
    {
        Firebase ref = new Firebase(Config.FIREBASE_URL).push();
        ref.setValue(url);
        Button b = (Button) findViewById(view.getId());
        b.setText("Images was saved!");
        b.setClickable(false);
        b.setBackgroundColor(Color.GREEN);
    }
}

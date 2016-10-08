package com.example.wilson.imagesapi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<ImageButton> l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        l = new ArrayList<>();
        createList(l);
    }

    private void createList(ArrayList<ImageButton> l)
    {
        Log.i("Called:", "addList()");
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

    public void onClickSearch(View view)
    {
        EditText editText = (EditText) findViewById(R.id.editText);
        String query = editText.getText().toString();
        searchImages(query);
    }

    public void searchImages(String query)
    {
        Ion.with(this)
                .load("https://api.unsplash.com/search/photos?query=" + query + ";client_id=c83c002a5256cfe69fc73c551341cfe1e47607708031484e469fd1a4a478372e")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        processData(result);
                    }
                });
    }

    private void processData(String result) {
        try {
            Log.i("processData", "try");
            JSONObject results = new JSONObject(result);
            int total = results.getInt("total");
            if(total == 0) Toast.makeText(SearchActivity.this, "Sorry, there is no images for that query. Try words like 'nature' or 'ocean'", Toast.LENGTH_SHORT).show();
            JSONArray jsonArray = results.getJSONArray("results");
            int length = jsonArray.length();
            if(jsonArray.length() > l.size()) length = l.size();
            for(int i = 0; i < length; i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                JSONObject urls = json.getJSONObject("urls");
                String url = urls.getString("regular");
                ImageButton b = l.get(i);
                b.setTag(url);
                Picasso.with(this)
                        .load(url)
                        .resize(600, 400)
                        .into(b);
                Log.i("json", jsonArray.getJSONObject(i).getJSONObject("urls").getString("regular"));
            }

        } catch (JSONException e) {
            Log.i("processData", "error");
            e.printStackTrace();
        }
    }

    public void onClickImage(View view)
    {
        ImageButton ib1 = (ImageButton) findViewById(view.getId());
        String url = ib1.getTag().toString();
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}

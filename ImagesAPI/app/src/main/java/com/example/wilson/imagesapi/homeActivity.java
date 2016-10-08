package com.example.wilson.imagesapi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class homeActivity extends AppCompatActivity {
    private ArrayList<ImageButton> l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_home);
        l = new ArrayList<>();
        createList(l);
        loadImages();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_saved:
                Intent intent1 = new Intent(this, SavedActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_random:
                loadImages();
                return true;
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createList(ArrayList<ImageButton> l)
    {
        Log.i("Called:", "addList()");
        l.add((ImageButton) findViewById(R.id.imageButton01));
        l.add((ImageButton) findViewById(R.id.imageButton02));
        l.add((ImageButton) findViewById(R.id.imageButton03));
        l.add((ImageButton) findViewById(R.id.imageButton04));
        l.add((ImageButton) findViewById(R.id.imageButton05));
        l.add((ImageButton) findViewById(R.id.imageButton06));
        l.add((ImageButton) findViewById(R.id.imageButton07));
        l.add((ImageButton) findViewById(R.id.imageButton08));
        l.add((ImageButton) findViewById(R.id.imageButton09));
        l.add((ImageButton) findViewById(R.id.imageButton10));
    }

    public void loadImages()
    {
        Ion.with(this)
//                .load("https://api.unsplash.com/photos/?client_id=c83c002a5256cfe69fc73c551341cfe1e47607708031484e469fd1a4a478372e")
                .load("https://api.unsplash.com/photos/random?count=10;client_id=c83c002a5256cfe69fc73c551341cfe1e47607708031484e469fd1a4a478372e")
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
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < 10; i++) {
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

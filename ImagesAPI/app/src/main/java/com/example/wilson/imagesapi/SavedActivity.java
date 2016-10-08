package com.example.wilson.imagesapi;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SavedActivity extends ListActivity {
    final ArrayList data = new ArrayList();
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Firebase.setAndroidContext(this);
        ref = new Firebase(Config.FIREBASE_URL);
        loadDB();
        Log.i("array", Integer.toString(data.size()));
        if(data.isEmpty()){
            data.add(ref.getRoot());
            data.remove(0);
        }
    }

    public void loadDB()
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.i("onDataChange", "for-loop");
                    final String url = postSnapshot.getValue().toString();
                    data.add(url);
                }
                if(data.isEmpty()) Toast.makeText(SavedActivity.this, "Your Saved database is empty. Please add an Image", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        final ArrayAdapter adapter = new ArrayAdapter(this,R.layout.activity_saved, data);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // parent ‐ parent ListView, view ‐ item TextView, position ‐ item selected, id ‐ N/A
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri webpage = Uri.parse(adapter.getItem(position).toString());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });

    }
}

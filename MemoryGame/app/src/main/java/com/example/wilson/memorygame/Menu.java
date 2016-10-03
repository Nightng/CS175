package com.example.wilson.memorygame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Menu extends AppCompatActivity {

    @BindView(R.id.buttonRules)
    Button bRules;

    private RulesFragment rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    public void goToGame(View view)
    {
        Intent intent = new Intent(this, Game.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @OnClick(R.id.buttonRules)
    public void goToRules(View view)
    {
        if(rules == null) {
            rules = new RulesFragment();
            getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .add(R.id.fragment, rules)
                    .commit();
        }
        bRules.setVisibility(View.INVISIBLE);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }


    public void closeFragment(View view) {
        getFragmentManager().beginTransaction().remove(rules)
                .commit();
        rules = null;
        bRules.setVisibility(View.VISIBLE);
    }
}

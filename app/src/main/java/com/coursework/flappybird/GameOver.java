package com.coursework.flappybird;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameOver extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    TextView tvScore, tvPersonalBest;
    private DatabaseReference mDatabase;

    int scoreSP,score;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        // Replace this with your app ID
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Best_Score");

        getBestScore();
        mInterstitialAd = new InterstitialAd(this);
        // Replace this with your Ad Unit ID
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        score = getIntent().getExtras().getInt("score");


    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        finish();
    }

    public void getBestScore(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                scoreSP = Integer.parseInt(snapshot.getValue()+"");
                bestScore();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void bestScore(){
        if(score > scoreSP){
            mDatabase.setValue(score);
            scoreSP = score;
        }
        tvScore = findViewById(R.id.tvScore);
        tvPersonalBest = findViewById(R.id.tvPersonalBest);
        tvScore.setText(""+score);
        tvPersonalBest.setText(""+scoreSP);
    }
}

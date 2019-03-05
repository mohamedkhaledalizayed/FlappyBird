package com.coursework.flappybird;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class GameActivity extends Activity {

    GameView gameView;
    private AdView mAdView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AppConstants.gameActivityContext = this;

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        // Replace this with your app ID
        MobileAds.initialize(this, "ca-app-pub-8402212700993150");
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        // Replace this with your ad unit Id
        mAdView.setAdUnitId("ca-app-pub-8402212700993150");
        // Create an ad request.
        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        gameView = new GameView(this); // Instantiate gameView
        // Define ad view parameters
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layout.addView(gameView);
        layout.addView(mAdView, adParams);
        // Set main renderer
        setContentView(layout);
    }
}

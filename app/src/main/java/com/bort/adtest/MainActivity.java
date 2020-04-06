package com.bort.adtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.vungle.warren.Vungle;
import com.vungle.warren.AdConfig;              // Custom ad configurations
import com.vungle.warren.InitCallback;          // Initialization callback
import com.vungle.warren.LoadAdCallback;        // Load ad callback
import com.vungle.warren.PlayAdCallback;        // Play ad callback
import com.vungle.warren.VungleNativeAd;        // MREC ad
import com.vungle.warren.Banners;               // Banner ad
import com.vungle.warren.VungleBanner;          // Banner ad
import com.vungle.warren.Vungle.Consent;        // GDPR consent
import com.vungle.warren.VungleSettings;        // Minimum disk space
import com.vungle.warren.error.VungleException;  // onError message

public class MainActivity extends AppCompatActivity {
    private Button loadAd;
    private Button playAd;


    private String appId = "5e88fdb1312e3200014f6269";
    private Context context = this;
    private InitCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAd = (Button) findViewById(R.id.loadAd);
        playAd = (Button) findViewById(R.id.playAd);

        Vungle.init(appId, getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                // Initialization has succeeded and SDK is ready to load an ad or play one if there
                // is one pre-cached already
                Log.d("berttest", "init onSuccess");
            }

            @Override
            public void onError(VungleException exception) {
                // Initialization error occurred - exception.getLocalizedMessage() contains error message
                Log.d("berttest", "init error");
            }

            @Override
            public void onAutoCacheAdAvailable(String placementId) {
                // Callback to notify when an ad becomes available for the cache optimized placement
                // NOTE: This callback works only for the cache optimized placement. Otherwise, please use
                // LoadAdCallback with loadAd API for loading placements.
                Log.d("berttest", "init onAutoCacheAdAvailable");
            }
        });

        LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
            @Override
            public void onAdLoad(String placementReferenceId) {
                // Placement reference ID for the placement to load ad assets
                Log.d("berttest", "play Ad pressed");
            }

            @Override
            public void onError(String placementReferenceId, VungleException exception) {
                // Placement reference ID for the placement that failed to download ad assets
                // VungleException contains error code and message
            }
        };

        loadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("berttest", "load Ad pressed");
                if (Vungle.isInitialized()) {
                    Vungle.loadAd("INT-0631576", new LoadAdCallback() {
                        @Override
                        public void onAdLoad(String placementReferenceId) { }

                        @Override
                        public void onError(String placementReferenceId, VungleException exception) { }
                    });
                }
            }
        });

        playAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("berttest", "play Ad pressed");
                if (Vungle.canPlayAd("INT-0631576")) {
                    Vungle.playAd("INT-0631576", null, new PlayAdCallback() {
                        @Override public void onAdStart(String placementReferenceId) { }
                        @Override public void onAdEnd(String placementReferenceId, boolean completed, boolean isCTAClicked) { }
                        @Override public void onError(String placementReferenceId, VungleException exception) { }
                    });
                }
            }
        });


    }




}

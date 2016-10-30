package com.example.uberv.firebasedemoproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.PendingResult;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.uberv.firebasedemoproject.MessagingService.NOTIFICATION_EXTRA;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIXME doesn't work with ads
        // Firebase database
        // create reference to firebase database
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();

        // object we are going to store in our database
        Map<String, String> values = new HashMap<>();
        values.put("name","Richard");

        dbref.push().setValue(values, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError==null){
                    Log.d(LOG_TAG,"Save successful!");
                }else{
                    Log.d(LOG_TAG,"Save failed!");
                }
            }
        });

        // Ads
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.banner_ad_unit_id));

        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
        // use testing add
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")  // An example device ID
                .build();

        mAdView.loadAd(request);

        Intent startedIntent = getIntent();
        if(startedIntent!=null){
            Log.d(LOG_TAG,"started from intent");
            String notificationMessage=startedIntent.getStringExtra(NOTIFICATION_EXTRA);
            if(notificationMessage!=null){
                Toast.makeText(this, notificationMessage, Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG,notificationMessage);
            }
        }

    }
}

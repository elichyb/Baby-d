package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.elichy.baby_d.Globals;
import com.elichy.baby_d.R;

import java.util.UUID;

public class BabySleepingTime extends AppCompatActivity {

    private static final String TAG = "BabySleepingTime";
    private UUID baby_id;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_sleeping_time);
        Log.d(TAG, "onCreate: start");
        setInit();
    }

    private void setInit() {
        loadData();
        setBaby_id();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }

    public void setBaby_id(){
        baby_id =  UUID.fromString(getIntent().getStringExtra("baby_id"));
    }
}
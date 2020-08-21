package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Sleep;
import com.elichy.baby_d.R;
import com.elichy.baby_d.ViewAdds.SetSleepingDialog;
import java.util.ArrayList;
import java.util.UUID;

public class BabySleepingTime extends AppCompatActivity {

    private static final String TAG = "BabySleepingTime";
    private UUID baby_id;
    private String token;
    private ArrayList<Sleep> sleeps;
    private Button sleepingTimeBtn;
    private ListView sleepListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_sleeping_time);
        Log.d(TAG, "onCreate: start");
        setInit();
        setListeners();
    }

    private void setListeners() {
        sleepingTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetSleepingDialog setSleepingDialog = new SetSleepingDialog();
                setSleepingDialog.setBaby_id(baby_id);
                setSleepingDialog.setToken(token);
                setSleepingDialog.show(getSupportFragmentManager(),"setSleepingDialog");
            }
        });
    }

    private void setInit() {
        loadData();
        setBaby_id();
        sleepingTimeBtn = (Button) findViewById(R.id.sleepingTimeBtn);
        getSleeps();
        sleepListView = (ListView) findViewById(R.id.babySleepingTimeList);
        ArrayAdapter<Sleep> sleepAdp = new ArrayAdapter<>(BabySleepingTime.this, android.R.layout.simple_list_item_1, sleeps);
        sleepListView.setAdapter(sleepAdp);
    }

    private void getSleeps() {
        sleeps = (ArrayList<Sleep>) getIntent().getSerializableExtra("sleeps");
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }

    public void setBaby_id(){
        baby_id =  UUID.fromString(getIntent().getStringExtra("baby_id"));
    }
}
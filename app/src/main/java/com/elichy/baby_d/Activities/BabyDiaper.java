package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.elichy.baby_d.R;

import java.util.UUID;

public class BabyDiaper extends AppCompatActivity {
    private static final String TAG = "BabyDiaper";
    private UUID baby_id;
    private TextView dirtyDiaperTextValue;
    private TextView wetDiaperTextValue;
    private Button openDialogBtn;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_diaper);
        Log.d(TAG, "onCreate: Start!");
        setInit();
        setTextAres();
        setOnClickListeners();
    }

    private void setTextAres() {
        Intent intent = getIntent();
        String wet = intent.getStringExtra("wetDiapers");
        String dirty = intent.getStringExtra("dirtyDiapers");
        dirtyDiaperTextValue.setText(dirty);
        wetDiaperTextValue.setText(wet);
    }

    private void setOnClickListeners() {
    }

    private void setInit() {
        openDialogBtn = (Button) findViewById(R.id.AddDiaperRecordBtn);
        dirtyDiaperTextValue = (TextView) findViewById(R.id.dirtyDiaperTextValue);
        wetDiaperTextValue = (TextView) findViewById(R.id.wetDiaperTextValue);
    }
}
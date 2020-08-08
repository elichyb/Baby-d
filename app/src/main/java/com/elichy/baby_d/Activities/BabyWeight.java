package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elichy.baby_d.R;
import com.elichy.baby_d.ViewAdds.SetWeightDialog;

import java.util.UUID;

public class BabyWeight extends AppCompatActivity {
    private static final String TAG = "BabyWeight";
    private UUID baby_id;
    private TextView babyWeightText;
    private Button openDialogBtn;
    private String token;
    private String weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_weight);
        setInit();
        Log.d(TAG, "onCreate: Start!");
        setListeners();
        setWeight();
    }

    private void setListeners() {
        openDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetWeightDialog setWeightDialog = new SetWeightDialog();
                setWeightDialog.setBaby_id(baby_id);
                setWeightDialog.setToken(token);
                setWeightDialog.show(getSupportFragmentManager(),"setWeightDialog");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setWeight() {
        Intent intent = getIntent();
        this.weight = intent.getStringExtra("weight");
        babyWeightText.setText(weight);
    }

    private void setInit() {
        openDialogBtn = (Button) findViewById(R.id.changeWeightDialog);
        babyWeightText = (TextView) findViewById(R.id.babyWeightText);

    }
}
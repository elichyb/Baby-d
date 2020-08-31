/*
 * //----------------------------------------------------------------------------
 * // (C) Copyright Elichy Barak 2020
 * //
 * // The source code for this program is not published or other-
 * // wise divested of its trade secrets, irrespective of what has
 * // been deposited with the U.S. Copyright Office.
 * //
 * //----------------------------------------------------------------------------
 */

package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elichy.baby_d.Globals;
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
        loadData();
        setBaby_id();
    }

    private void setInit() {
        openDialogBtn = (Button) findViewById(R.id.changeWeightDialog);
        babyWeightText = (TextView) findViewById(R.id.weight);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }

    public void setBaby_id(){
        baby_id =  UUID.fromString(getIntent().getStringExtra("baby_id"));
    }
}
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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elichy.baby_d.Globals;
import com.elichy.baby_d.R;
import com.elichy.baby_d.ViewAdds.SetDiaperDialog;

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
        openDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetDiaperDialog setDiaperDialog = new SetDiaperDialog();
                setDiaperDialog.setBaby_id(baby_id);
                setDiaperDialog.setToken(token);
                setDiaperDialog.show(getSupportFragmentManager(),"setDiaperDialog");
            }
        });
    }

    private void setInit() {
        openDialogBtn = (Button) findViewById(R.id.AddDiaperRecordBtn);
        dirtyDiaperTextValue = (TextView) findViewById(R.id.dirtyDiaperTextValue);
        wetDiaperTextValue = (TextView) findViewById(R.id.wetDiaperTextValue);
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
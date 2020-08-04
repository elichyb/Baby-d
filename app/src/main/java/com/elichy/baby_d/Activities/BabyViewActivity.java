package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.elichy.baby_d.Dialogs.SetWeightDialog;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;

import java.util.UUID;

import retrofit2.Retrofit;

public class BabyViewActivity extends AppCompatActivity {
    
    private static final String TAG = "BabyViewActivity";
    private Retrofit retrofit;
    private String token;
    public static final String SHARED_PREFS = "sharedPrefs";
    private ResAPIHandler resAPIHandler;
    private UUID baby_id;
    private TextView currentWeight;
    private Button setWeightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_view);
        Log.d(TAG, "onCreate: starts succeddfully");
        setInit();
    }

    private void setInit() {
        baby_id = UUID.fromString(getIntent().getStringExtra(Globals.BABY_ID));
        currentWeight = (TextView) findViewById(R.id.weight);
        setWeightBtn = (Button) findViewById(R.id.SetWeightBtn);
        loadData();
    }


    public void showDialog(View view) {
        SetWeightDialog setWeightDialog = new SetWeightDialog();
        setWeightDialog.setBaby_id(this.baby_id);
        setWeightDialog.setToken(this.token);
        setWeightDialog.show(getSupportFragmentManager(),"setWeightDialog");
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }
}
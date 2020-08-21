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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.BreastFeed;
import com.elichy.baby_d.Models.Formula;
import com.elichy.baby_d.R;
import com.elichy.baby_d.ViewAdds.SetEatBreastDialog;
import com.elichy.baby_d.ViewAdds.SetEatFormulaDialog;
import java.util.ArrayList;
import java.util.UUID;

public class BabyEat extends AppCompatActivity {
    private static final String TAG = "BabyEat";
    private UUID baby_id;
    private String token;
    private ListView formulasListView;
    private ListView BreastFeedListView;
    private ArrayList<Formula> formulas;
    private ArrayList<BreastFeed> breastFeeds;
    private Button addEatFormulaRecord;
    private Button addEatBreastRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_eat);
        setInit();
        setListeners();
    }

    private void setListeners() {
        addEatFormulaRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetEatFormulaDialog setEatFormulaDialog = new SetEatFormulaDialog();
                setEatFormulaDialog.setBaby_id(baby_id);
                setEatFormulaDialog.setToken(token);
                setEatFormulaDialog.show(getSupportFragmentManager(),"setEatFormulaDialog");
            }
        });

        addEatBreastRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetEatBreastDialog setEatBreastDialog = new SetEatBreastDialog();
                setEatBreastDialog.setBaby_id(baby_id);
                setEatBreastDialog.setToken(token);
                setEatBreastDialog.show(getSupportFragmentManager(),"setEatBreastDialog");
            }
        });
    }

    private void setInit() {
        // get baby feeds
        getFormulas();
        getBreastFeed();

//        //Show baby formulas feed data
//        formulasListView = (ListView) findViewById(R.id.eatFormulaListView);
//        ArrayAdapter<Formula> formulaArrayAdapter = new ArrayAdapter<>(BabyEat.this, android.R.layout.simple_list_item_1, formulas);
//        formulasListView.setAdapter(formulaArrayAdapter);
//
//        // Show baby breast feed data
//        BreastFeedListView = (ListView) findViewById(R.id.eatBreastListView);
//        ArrayAdapter<BreastFeed> breastFeedArrayAdapter = new ArrayAdapter<>(BabyEat.this, android.R.layout.simple_list_item_1, breastFeeds);
//        BreastFeedListView.setAdapter(breastFeedArrayAdapter);

        // Set other view elements
        loadData();
        setBaby_id();
        addEatFormulaRecord = (Button) findViewById(R.id.addEatFormulaRecord);
        addEatBreastRecord = (Button) findViewById(R.id.addEatBreastRecord);
    }

    private void getBreastFeed() {
        breastFeeds = (ArrayList<BreastFeed>) getIntent().getSerializableExtra("breastFeed");
    }

    private void getFormulas() {
        formulas = (ArrayList<Formula>) getIntent().getSerializableExtra("formula");
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }

    public void setBaby_id(){
        baby_id =  UUID.fromString(getIntent().getStringExtra("baby_id"));
    }
}
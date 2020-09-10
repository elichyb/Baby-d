package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.BabyF;
import com.elichy.baby_d.Models.BabyFullInfo;
import com.elichy.baby_d.Models.BreastFeed;
import com.elichy.baby_d.Models.Formula;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.Models.Sleep;
import com.elichy.baby_d.R;
import com.elichy.baby_d.ViewAdds.BabySectionRecyclerViewAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BabyViewActivity extends AppCompatActivity implements BabySectionRecyclerViewAdapter.OnSectionListner
{
    private String[] babySections;
    private int colors[];
    private static final String TAG = "BabyViewActivity";
    private RecyclerView babySectionList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Retrofit retrofit;
    private String token;
    private List<BabyFullInfo> babyFullInfos;
    private ResAPIHandler resAPIHandler;
    private UUID baby_id;
    private boolean need_resume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_view);
        Log.d(TAG, "onCreate: Start successfully");
        setInit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (need_resume) {
            setContentView(R.layout.activity_baby_view);
            Log.d(TAG, "onCreate: Start successfully");
            setInit();
        }
        need_resume = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        need_resume = false;
    }

    private void setInit() {
        baby_id = UUID.fromString(getIntent().getStringExtra(Globals.BABY_ID));
        babySectionList = (RecyclerView) findViewById(R.id.babySectionList);
        colors = new int[]{R.color.pastelGreen, R.color.pasteRed, R.color.pasteYellow, R.color.pasteBlue};
        babySectionList.setHasFixedSize(true);
        babySections = getResources().getStringArray(R.array.babySections);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BabySectionRecyclerViewAdapter(babySections, colors, this);
        babySectionList.setLayoutManager(mLayoutManager);
        babySectionList.setAdapter(mAdapter);
        loadData();
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        // create okhttp client
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        okHttpClient.addInterceptor(loggin);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(String.format("%s/api/baby/", Globals.SERVER_IP))
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        resAPIHandler = retrofit.create(ResAPIHandler.class);
        BabyF b = new BabyF(baby_id, Globals.GET_DATE());
        Call<List<BabyFullInfo>> call = resAPIHandler.getBabyFullInfo(token, b);
        call.enqueue(new Callback<List<BabyFullInfo>>() {
            @Override
            public void onResponse(Call<List<BabyFullInfo>> call, Response<List<BabyFullInfo>> response) {
                if (! response.isSuccessful()){
                    Toast.makeText(BabyViewActivity.this, "Failed to get baby full details", Toast.LENGTH_SHORT).show();
                    BabyViewActivity.this.finish();
                }
                else
                {
                    Toast.makeText(BabyViewActivity.this, "Baby load details successfully", Toast.LENGTH_SHORT).show();
                    babyFullInfos = response.body();
                    if (babyFullInfos.isEmpty()){
                        List<BabyFullInfo> b = new ArrayList<>();
                        b.add(new BabyFullInfo(Globals.GET_DATE(), Globals.GET_TIME(), 0.0, false, false, 0,
                                null, 0, 0, null));
                        babyFullInfos = b;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BabyFullInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(BabyViewActivity.this, "Failed to get baby full details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String get_date() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Globals.DATE_FORMAT);
        return LocalDateTime.now().toString();
    }

    @Override
    public void onSectionClicked(int pos) {
        Intent intent;
        Log.d(TAG, "onSectionClicked: clicked on " + babySections[pos]);
        switch (babySections[pos]){

            case "Current Weight":
                Log.d(TAG, "onSectionClicked: Goes into current weight");
                intent = new Intent(this, BabyWeight.class);
                double weight = get_weight();
                intent.putExtra("weight", Double.toString(weight));
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;

            case "Diaper":
                Log.d(TAG, "onSectionClicked: Goes into diaper activity");
                intent = new Intent(this, BabyDiaper.class);
                intent.putExtra("wetDiapers",   Integer.toString(get_wet_diapers()));
                intent.putExtra("dirtyDiapers", Integer.toString(get_dirty_diapers()));
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;

            case "Sleeping":
                Log.d(TAG, "onSectionClicked: Goes into sleeping activity");
                ArrayList<Sleep> sleeps = getSleep();
                intent = new Intent(this, BabySleepingTime.class);
                intent.putExtra("sleeps", sleeps);
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;

            case "Eating":
                Log.d(TAG, "onSectionClicked: Goes into Eating activity");
                ArrayList<Formula> formulas = getFormula();
                ArrayList<BreastFeed> breastFeeds = getBreast();
                intent = new Intent(this, BabyEat.class);
                intent.putExtra("formula", formulas);
                intent.putExtra("breastFeed", breastFeeds);
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;
        }
    }

    private int get_dirty_diapers() {
        int count = 0;
        for(BabyFullInfo b: babyFullInfos){
            if (b.getDirty_dipper()){
                count++;
            }
        }
        return count;
    }

    private int get_wet_diapers() {
        int count = 0;
        for(BabyFullInfo b: babyFullInfos){
            if (b.getWet_dipper()){
                count++;
            }
        }
        return count;
    }

    private double get_weight() {
        return babyFullInfos.get(babyFullInfos.size() - 1).getWeight();
    }

    private ArrayList<Formula> getFormula() {
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        for(BabyFullInfo b: babyFullInfos) {
            if (b.getFeed_amount() != null){
                formulas.add(new Formula(baby_id, b.getMeasure_date(), b.getMeasure_time(), b.getFeed_amount(), b.getFeed_type()));
            }
        }
        return formulas;
    }

    private ArrayList<BreastFeed> getBreast() {
        ArrayList<BreastFeed> breastFeeds =  new ArrayList<BreastFeed>();
        for(BabyFullInfo b: babyFullInfos) {
            if (b.getBreast_side() != null){
                breastFeeds.add(new BreastFeed(baby_id, b.getMeasure_date(), b.getMeasure_time(), b.getBreast_side(),
                        b.getBreast_feeding_time_length(), b.getFeed_type()));
            }
        }
        return breastFeeds;
    }

    private ArrayList<Sleep> getSleep() {
        ArrayList<Sleep> sleeps = new ArrayList<Sleep>();
        for(BabyFullInfo b: babyFullInfos) {
            if(b.getSleeping_time() != 0){
                sleeps.add(new Sleep(baby_id, b.getMeasure_date(), b.getMeasure_time(), b.getSleeping_time()));
            }
        }
        return sleeps;
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }
}
package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Baby;
import com.elichy.baby_d.Models.BabyF;
import com.elichy.baby_d.Models.BabyFullInfo;
import com.elichy.baby_d.Models.BreastFeed;
import com.elichy.baby_d.Models.Formula;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.Models.Sleep;
import com.elichy.baby_d.R;
import com.elichy.baby_d.ViewAdds.BabySectionRecyclerViewAdapter;

import java.io.Serializable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_view);
        Log.d(TAG, "onCreate: Start successfully");
        setInit();
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
                    Toast.makeText(BabyViewActivity.this, "Failed to set baby eat formula", Toast.LENGTH_SHORT).show();
                    BabyViewActivity.this.finish();
                    return;
                }
                else
                {
                    Toast.makeText(BabyViewActivity.this, "Baby eat formula set successfully", Toast.LENGTH_SHORT).show();
                }
                babyFullInfos = response.body();
            }

            @Override
            public void onFailure(Call<List<BabyFullInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(BabyViewActivity.this, "Failed to set baby eat formula", Toast.LENGTH_SHORT).show();
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
                intent.putExtra("weight", Double.toString(0.0));
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;

            case "Diaper":
                Log.d(TAG, "onSectionClicked: Goes into diaper activity");
                intent = new Intent(this, BabyDiaper.class);
                intent.putExtra("wetDiapers",   Double.toString(0.0));
                intent.putExtra("dirtyDiapers", Double.toString(0.0));
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;

            case "Sleeping":
                Log.d(TAG, "onSectionClicked: Goes into sleeping activity");
                ArrayList<Sleep> sleeps = getSleep(); // todo we will pass here the baby full info list object and it will extract only the sleeping time from it

                intent = new Intent(this, BabySleepingTime.class);
                intent.putExtra("sleeps", sleeps);
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;

            case "Eating":
                Log.d(TAG, "onSectionClicked: Goes into Eating activity");
                ArrayList<Formula> formulas = getFormula();
                intent = new Intent(this, BabyEat.class);
                intent.putExtra("baby_id", baby_id.toString());
                startActivity(intent);
                break;
        }
    }

    private ArrayList<Formula> getFormula() {
        return new ArrayList<Formula>();
    }

    private ArrayList<BreastFeed> getBreast() {
        return new ArrayList<BreastFeed>();
    }

    private ArrayList<Sleep> getSleep() {
        ArrayList<Sleep> sleeps = new ArrayList<Sleep>();
        sleeps.add(new Sleep(baby_id, "2020-01-04", "14:00:00", 20));
        sleeps.add(new Sleep(baby_id, "2020-01-04", "17:00:00", 40));
        sleeps.add(new Sleep(baby_id, "2020-01-04", "20:00:00", 30));
        return sleeps;
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }
}
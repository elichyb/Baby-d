package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Baby;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentViewActivity extends AppCompatActivity {
    private GridLayout babyListView;
    private List<Baby> babyList;
    private Button addBabyBtn;
    private Retrofit retrofit;
    private String token;
    private String TEXT = "token";
    public static final String SHARED_PREFS = "sharedPrefs";
    private ResAPIHandler resAPIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view);
        setInit();
        setListners();
    }

    private void setListners() {
        addBabyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //will go to new activity to add baby.
                Intent intent = new Intent(ParentViewActivity.this, AddBabyViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setInit() {
        loadData();
        babyListView = (GridLayout) findViewById(R.id.babyListView);
        addBabyBtn = (Button) findViewById(R.id.addBabybtn);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(String.format("%s/api/baby", Globals.server_ip))
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();

        Call<List<Baby>> call = resAPIHandler.getMyBabies(this.token);
        call.enqueue(new Callback<List<Baby>>() {
            @Override
            public void onResponse(Call<List<Baby>> call, Response<List<Baby>> response) {
                if (! response.isSuccessful())
                {
                    Toast.makeText(ParentViewActivity.this, "failed to login", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<List<Baby>> call, Throwable t) {
                Toast.makeText(ParentViewActivity.this, "failed to login", Toast.LENGTH_SHORT).show();
                return;
            }
        });


    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(TEXT,"");
    }
}
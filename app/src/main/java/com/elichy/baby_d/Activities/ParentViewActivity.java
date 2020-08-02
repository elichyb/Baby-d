package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Baby;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentViewActivity extends AppCompatActivity {
    private ListView babyListView;
    private List<Baby> babyList;
    private Button addBabyBtn;
    private Retrofit retrofit;
    private String token;
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
        babyListView = (ListView) findViewById(R.id.babyListView);
        addBabyBtn = (Button) findViewById(R.id.addBabybtn);

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

        Call<List<Baby>> call = resAPIHandler.getMyBabies(this.token);
        call.enqueue(new Callback<List<Baby>>() {
            @Override
            public void onResponse(Call<List<Baby>> call, Response<List<Baby>> response) {
                if (! response.isSuccessful())
                {
                    Toast.makeText(ParentViewActivity.this, "failed to login", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Baby> babies = response.body();
                if (babies != null) {
                    ArrayAdapter<Baby> babiesAdapter = new ArrayAdapter(ParentViewActivity.this, android.R.layout.simple_list_item_1, babies);
                    babyListView.setAdapter(babiesAdapter);
                    babyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ParentViewActivity.this, BabyViewActivity.class);
                            Baby b = (Baby) parent.getAdapter().getItem(position);
                            intent.putExtra("Baby_id", b.getBaby_id());
                            startActivity(intent);
                        }
                    });
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
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }
}
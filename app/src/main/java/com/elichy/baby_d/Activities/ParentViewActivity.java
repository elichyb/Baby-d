package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Baby;
import com.elichy.baby_d.R;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentViewActivity extends AppCompatActivity {
    private ListView babyListView;
    private List<Baby> babyList;
    private Retrofit retrofit;
    private String token;
    private String TEXT = "token";
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view);
        setInit();
    }

    private void setInit() {
        loadData();
        babyListView = (ListView) findViewById(R.id.babyListView);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(String.format("%s/api/baby", Globals.server_ip))
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();


    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(TEXT,"");
    }
}
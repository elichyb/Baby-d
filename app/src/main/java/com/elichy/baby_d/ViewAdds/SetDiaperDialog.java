package com.elichy.baby_d.ViewAdds;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Diaper;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetDiaperDialog  extends DialogFragment {
    private static final String TAG = "SetDiaperDialog";
    private Retrofit retrofit;
    private ResAPIHandler resAPIHandler;
    private UUID baby_id;
    private String token;
    private RadioButton wet;
    private RadioButton dirty;
    private Button submitBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.set_diaper_dialog, container, false);
        setInit(view);
        setListener();
        Log.d(TAG, "onCreateView: Start dialog");
        return view;
    }

    private void setListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = get_date();
                String time = get_time();
                Diaper d = new Diaper(baby_id, date, time, wet.isChecked(), dirty.isChecked());

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
                Call<String> call = resAPIHandler.setBabyDiaper(token, d);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (! response.isSuccessful()){
                            Toast.makeText(getContext(), "Failed to set baby diaper", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Baby diaper set successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        Toast.makeText(getContext(), "Failed to set baby diaper", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private String get_date() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Globals.DATE_FORMAT);
                return LocalDateTime.now().toString();
            }

            private String get_time() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Globals.TIME_FORMAT);
                return LocalDateTime.now().toString();
            }

        });
    }

    private void setInit(View view) {
        submitBtn = (Button) view.findViewById(R.id.submitBtn);
        wet = (RadioButton) view.findViewById(R.id.wetBtn);
        dirty = (RadioButton) view.findViewById(R.id.dirtyBtn);
    }

    public void setToken(String token) {
        this.token = token;
        Log.d(TAG, "setToken: token set:" + this.token);
    }

    public void setBaby_id(UUID baby_id) {
        this.baby_id = baby_id;
        Log.d(TAG, "setBaby_id: baby id set: " + this.baby_id);
    }
}

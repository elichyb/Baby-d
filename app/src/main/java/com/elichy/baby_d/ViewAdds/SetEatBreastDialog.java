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

package com.elichy.baby_d.ViewAdds;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.BreastFeed;
import com.elichy.baby_d.Models.Formula;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.Models.ServerResponse;
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

public class SetEatBreastDialog extends DialogFragment {
    private static final String TAG = "SetEatBreastDialog";
    private UUID baby_id;
    private String token;
    private Retrofit retrofit;
    private ResAPIHandler resAPIHandler;
    private EditText breastSideText;
    private EditText feedingTime;
    private Button submitBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.set_eating_breast_dialog, container, false);
        setInit(view);
        setListener();
        Log.d(TAG, "onCreateView: Load the dialog fragment");
        //todo add textWatch;
        return view;
    }

    private void setListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = Globals.GET_DATE();
                String time = Globals.GET_TIME();
                String side = breastSideText.getText().toString().trim();
                int feedTime = Integer.parseInt(feedingTime.getText().toString().trim());
                BreastFeed b = new BreastFeed(baby_id, date, time, side, feedTime, Globals.BREASTFEED);

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
                Call<ServerResponse> call = resAPIHandler.setBabyEatBreast(token, b);
                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (! response.isSuccessful()){
                            Toast.makeText(getContext(), "Failed to set baby eat breast", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Baby eat breast set successfully", Toast.LENGTH_SHORT).show();
                            SetEatBreastDialog.this.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        Toast.makeText(getContext(), "Failed to set baby eat breast", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setInit(View view) {
        breastSideText = (EditText) view.findViewById(R.id.breastSideText);
        feedingTime = (EditText) view.findViewById(R.id.FeedingTime);
        submitBtn = (Button) view.findViewById(R.id.submitBtn);
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

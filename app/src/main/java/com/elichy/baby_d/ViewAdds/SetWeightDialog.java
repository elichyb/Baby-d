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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.Models.Weight;
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


public class SetWeightDialog  extends DialogFragment {
    private static final String TAG = "SetWeightDialog";
    private Retrofit retrofit;
    private ResAPIHandler resAPIHandler;
    private UUID baby_id;
    private TextView babyWeightText;
    private Button submitBtn;
    private String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.set_weight_dialog, container, false);
        setInit(view);
        setListener();
        // Setting text watcher for this
        babyWeightText.addTextChangedListener(submiteTextWatcher);
        Log.d(TAG, "onCreateView: Load the dialog fragment");
        return view;
    }

    private void setListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String today = Globals.GET_DATE();
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
                double w = Double.parseDouble(babyWeightText.getText().toString().trim());
                Weight weight = new Weight(baby_id, w, today);

                Call<String> call = resAPIHandler.setBabyWeight(token, weight);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (! response.isSuccessful()){
                            Toast.makeText(getContext(), "Failed to set baby    weight", Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                            Toast.makeText(getContext(), "Baby weight set successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        Toast.makeText(getContext(), "Failed to set baby weight", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private TextWatcher submiteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String weight = babyWeightText.getText().toString().trim();
            Log.d(TAG, "onTextChanged: Setting text watcher for submitting");
            submitBtn.setEnabled(! weight.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void setInit(View view) {
        submitBtn = (Button) view.findViewById(R.id.submitBtn);
        babyWeightText = (TextView) view.findViewById(R.id.babyWeightText);
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

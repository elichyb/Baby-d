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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Formula;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.Models.ServerResponse;
import com.elichy.baby_d.Models.Sleep;
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

import static com.elichy.baby_d.Globals.FOMULA;

public class SetEatFormulaDialog extends DialogFragment {
    private static final String TAG = "SetEatFormulaDialog";
    private UUID baby_id;
    private String token;
    private Retrofit retrofit;
    private ResAPIHandler resAPIHandler;
    private EditText amountText;
    private Button submitBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.set_eating_formula_dialog, container, false);
        setInit(view);
        setListener();
        Log.d(TAG, "onCreateView: Load the dialog fragment");
        return view;
    }

    private void setListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = Globals.GET_DATE();
                String time = Globals.GET_TIME();
                int amount = Integer.parseInt(amountText.getText().toString().trim());
                Formula f = new Formula(baby_id, date, time, amount, Globals.FOMULA);

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
                Call<ServerResponse> call = resAPIHandler.setBabyEatFormula(token, f);
                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if (! response.isSuccessful()){
                            Toast.makeText(getContext(), "Failed to set baby eat formula", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Baby eat formula set successfully", Toast.LENGTH_SHORT).show();
                            SetEatFormulaDialog.this.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        Toast.makeText(getContext(), "Failed to set baby eat formula", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setInit(View view) {
        amountText = (EditText) view.findViewById(R.id.amountText);
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


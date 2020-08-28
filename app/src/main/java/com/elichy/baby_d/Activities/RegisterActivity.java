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
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.elichy.baby_d.BuildConfig;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.ParentRegistration;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;

import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    EditText password;
    Button submit;
    private static final String TAG = "RegisterActivity";
    private ResAPIHandler resAPIHandler;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerInit();
        SetTextWatcher();
        setListenrs();

    }

    private void SetTextWatcher() {
        firstName.addTextChangedListener(submiteTextWatcher);
        lastName.addTextChangedListener(submiteTextWatcher);
        email.addTextChangedListener(submiteTextWatcher);
        phone.addTextChangedListener(submiteTextWatcher);
        password.addTextChangedListener(submiteTextWatcher);
    }

    private void setListenrs() {
        Log.d(TAG, "setListenrs: submit on click listenr");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentRegistration parentRegistration = new ParentRegistration(
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        password.getText().toString());
                Call<String> call = resAPIHandler.registertParent(parentRegistration);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()){
                            popToast("Failed registration");
                        }
                        else {
                            Log.d(TAG, "onResponse: successfully registered!!!");
                            popToast("Successfully register");
                            RegisterActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onFailure: failed" + t.toString());
                        // Until I will Create proper json.
                        if (t.toString().contains("Expected a string but was BEGIN_OBJECT")){
                            popToast("Successfully register");
                            RegisterActivity.this.finish();
                            return;
                        }
                        popToast("Failed registration");
                    }

                    private void popToast(String msg){
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                        toast.show();
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
            String userFirstName = firstName.getText().toString().trim();
            String userLastName = lastName.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userPhone = phone.getText().toString().trim();
            String userPassword = password.getText().toString().trim();
            Log.d(TAG, String.format("onTextChanged: first_namd: %s " +
                    "last name: %s " +
                    "email: %s " +
                    "password: %s ",
                    userFirstName, userLastName, userEmail, userPassword));
            // Check also email correctness
            Pattern pattern = Pattern.compile("^(.+)@(.+)$"); //email validator pattern

            submit.setEnabled(
                    ! userFirstName.isEmpty()  &&
                    ! userLastName.isEmpty()   &&
                    ! userEmail.isEmpty()      &&
                    ! userPassword.isEmpty()   &&
                    ! userPhone.isEmpty()      &&
                    pattern.matcher(userEmail).matches()
            );
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void registerInit() {
        Log.d(TAG, "registerInit: Init for register parent");
        firstName = (EditText) findViewById(R.id.first_name_text);
        lastName = (EditText) findViewById(R.id.last_name_text);
        email = (EditText) findViewById(R.id.email_text);
        phone = (EditText) findViewById(R.id.phone_text);
        password = (EditText) findViewById(R.id.password_text);
        submit = (Button) findViewById(R.id.submitBtn);

        // create okhttp client
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        okHttpClient.addInterceptor(loggin);

        retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s/api/parent/", Globals.SERVER_IP))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
        resAPIHandler = retrofit.create(ResAPIHandler.class);
    }
}
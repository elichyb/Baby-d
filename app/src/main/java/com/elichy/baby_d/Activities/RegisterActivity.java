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

import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.ParentRegistration;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
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
                        password.getText().toString());
                Call<String> call = resAPIHandler.registartParent(parentRegistration);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        CharSequence text;
                        if (!response.isSuccessful()){
                            text = "Failed registration";
                        }
                        else {
                            text = "Successfully register";
                        }
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Context context = getApplicationContext();
                        CharSequence text = "Failed registration";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
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
        password = (EditText) findViewById(R.id.password_text);
        submit = (Button) findViewById(R.id.submitBtn);
        //todo set smaller timeout for it.
        retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s/api/parent/", Globals.server_ip))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resAPIHandler = retrofit.create(ResAPIHandler.class);
    }
}
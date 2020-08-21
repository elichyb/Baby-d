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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.Baby;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;
import java.util.Calendar;
import java.util.concurrent.BlockingDeque;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBabyViewActivity extends AppCompatActivity {
    // Attributes list:
    private static final String TAG = "AddBabyViewActivity";
    private TextView firstName;
    private TextView lastName;
    private Spinner foodType;
    private TextView weight;
    private TextView birthDay;
    private Button submitBtn;
    private DatePickerDialog.OnDateSetListener dateSetListner;
    private Retrofit retrofit;
    private String token;
    public static final String SHARED_PREFS = "sharedPrefs";
    private ResAPIHandler resAPIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby_view);
        Log.d(TAG, "onCreate: baby view activity");
        setInit();
        setListners();
        SetTextWatcher();
    }

    private void SetTextWatcher() {
        firstName.addTextChangedListener(submiteTextWatcher);
        lastName.addTextChangedListener(submiteTextWatcher);
        weight.addTextChangedListener(submiteTextWatcher);
        birthDay.addTextChangedListener(submiteTextWatcher);
    }

    private void setListners() {
        birthDay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddBabyViewActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT,
                        dateSetListner,
                        year, month, day);
                dialog.show();
            }
        });

        dateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String sday = day >= 9 ? String.valueOf(day) : "0" + day;
                String smonth = month >= 9 ? String.valueOf(month) : "0" + month;

                String date = year + "-" + smonth + "-" + sday;
                birthDay.setText(date);
            }
        };

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence toastText = "Failed to add baby";
                Toast addBabyFailed = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                int food = getFoodType();
                Baby baby = new Baby(firstName.getText().toString().trim(),
                        lastName.getText().toString().trim(),
                        food,
                        birthDay.getText().toString().trim(),
                        String.valueOf(weight.getText()));
                Log.d(TAG, "onClick: Baby to send is:" + baby.toString());
                resAPIHandler = retrofit.create(ResAPIHandler.class);
                Call<String> call = resAPIHandler.addNewBaby(token, baby);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(! response.isSuccessful()){
                            addBabyFailed.show();
                            return;
                        }
                        Toast success = Toast.makeText(context, "Baby added successfully", Toast.LENGTH_SHORT);
                        success.show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        addBabyFailed.show();
                    }
                });
            }
        });
    }

    private int getFoodType() {
        String tmp = (String) foodType.getSelectedItem();
        if (tmp.equals("Breast Feeding"))
            return 1;
        else if (tmp.equals("Formula"))
            return 2;
        else
            return 3;

    }

    private void setInit() {
        firstName = (TextView) findViewById(R.id.BaybFirstNameText);
        lastName = (TextView) findViewById(R.id.BabyLastNameText);
        foodType = (Spinner) findViewById(R.id.BabyFoodTypeSpinner);
        weight = (TextView) findViewById(R.id.BabyWieghtText);
        birthDay = (TextView) findViewById(R.id.BabyBirthDayText);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(String.format("%s/api/baby/", Globals.SERVER_IP))
                .addConverterFactory(GsonConverterFactory.create());
        // load data from SHARED_PREFS
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");

        retrofit = builder.build();
        submitBtn = (Button) findViewById(R.id.SubmitBtn);
    }

    private TextWatcher submiteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String babyFirstName = firstName.getText().toString().trim();
            String babyLastName = lastName.getText().toString().trim();
            String babyWeight = weight.getText().toString().trim();
            String babyBirthDay = birthDay.getText().toString().trim();

            Log.d(TAG, String.format("onTextChanged: first_namd: %s " +
                            "last name: %s " +
                            "baby weight: %s " +
                            "baby birth day: %s ",
                    babyFirstName, babyLastName, babyWeight, babyBirthDay));

            submitBtn.setEnabled(
                            ! babyFirstName.isEmpty()  &&
                            ! babyLastName.isEmpty()   &&
                            ! babyWeight.isEmpty()     &&
                            ! babyBirthDay.isEmpty()
            );
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
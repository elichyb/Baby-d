package com.elichy.baby_d.ViewAdds;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.elichy.baby_d.R;

import java.util.UUID;

public class SetSleepingDialog extends DialogFragment {
    private static final String TAG = "SetSleepingDialog";
    private UUID baby_id;
    private String token;
    Button submitBtn;
    EditText babySleepFillText;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.set_sleeping_time_dialog, container, false);
        setInit(view);
        setListener();
        Log.d(TAG, "onCreateView: Load the dialog fragment");
        return view;
    }

    private void setListener() {
    }

    private void setInit(View view) {
        submitBtn = (Button) view.findViewById(R.id.submitBtn);
        babySleepFillText = (EditText) view.findViewById(R.id.babySleepFillText);

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

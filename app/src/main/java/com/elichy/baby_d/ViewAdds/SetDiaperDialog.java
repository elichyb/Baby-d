package com.elichy.baby_d.ViewAdds;

import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.elichy.baby_d.Models.ResAPIHandler;
import java.util.UUID;
import retrofit2.Retrofit;

public class SetDiaperDialog  extends DialogFragment {
    private static final String TAG = "SetDiaperDialog";
    private Retrofit retrofit;
    private ResAPIHandler resAPIHandler;
    private UUID baby_id;
    private String token;



}

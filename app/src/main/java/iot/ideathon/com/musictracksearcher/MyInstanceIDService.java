package iot.ideathon.com.musictracksearcher;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NRamasamy on 4/17/2017.
 */

public class MyInstanceIDService extends FirebaseInstanceIdService
{
    private static final String TAG = "iot.ideathon.com.musictracksearcher.MyInstanceIDService";

    @Override
    public void onTokenRefresh()
    {

        Log.d(TAG, "Ref GCM Reg Tkn");

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}

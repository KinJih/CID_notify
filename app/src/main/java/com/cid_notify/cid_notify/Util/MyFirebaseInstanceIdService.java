package com.cid_notify.cid_notify.Util;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MT", "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String refreshedToken){
        //todo
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
            databaseReference.child("Devices").child(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)).child("token").setValue(refreshedToken);
        }
    }
}

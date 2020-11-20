package com.ujjwal.p2ptrial.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import chat.Chat;

public class P2pNetwork {
    private final static Handler handler = new Handler();


    public static final int LOCATION = 0;
    public static final int STRING = 1;
    public static final int PAIR = 3;

    public P2pNetwork() {
        init();
    }

    public void init () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Chat.chat();
            }
        }).start();
    }

    public static void publishMessage (String msg) {
        Log.d("PublishMessage", Chat.transmitLocation(msg));
    }

    public void sendLocation(Activity activity) {
        sendLocation(activity, STRING);
    }

    public void sendLocation(Activity activity, int rType) {
        FusedLocationProviderClient fusedLocationClient = new FusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d("TAG", "No permission");
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.d("PublishMessage", Chat.transmitLocation("" + location.getLatitude() + location.getLongitude()));
                        } else {
                            Log.d("TAG", "onSuccess: got Null location");
                        }
                    }
                });
    }

}

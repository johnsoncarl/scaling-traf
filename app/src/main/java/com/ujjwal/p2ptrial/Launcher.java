package com.ujjwal.p2ptrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.ujjwal.p2ptrial.location.P2pNetwork;

public class Launcher extends AppCompatActivity {
//    private FusedLocationProviderClient fusedLocationClient;
    public static P2pNetwork pNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        pNetwork = new P2pNetwork();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                pNetwork.sendLocation(Launcher.this);
                handler.postDelayed(this, 3000);
            }
        });

        findViewById(R.id.maps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Launcher.this, Maps.class));
            }
        });

//        Places.initialize(getApplicationContext(), getResources().getString(R.string.maps_api_key));
    }
}
package com.ujjwal.p2ptrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ujjwal.p2ptrial.location.LocationUpdates;
import com.ujjwal.p2ptrial.location.P2pNetwork;
import com.ujjwal.p2ptrial.location.PeerLocation;
import com.ujjwal.p2ptrial.location.logdb.LogDbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class Launcher extends AppCompatActivity {
    private static final String TAG = "Launcher";

//    private FusedLocationProviderClient fusedLocationClient;
    public static P2pNetwork pNetwork;
    private Long INTERVAL = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        pNetwork = new P2pNetwork(this);

        pNetwork.sendLocation(Launcher.this, INTERVAL);
        pNetwork.fetchPeerLocation();

        findViewById(R.id.maps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Launcher.this, Maps.class));
            }
        });

        ((Switch) findViewById(R.id.switch1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pNetwork.setLogging(isChecked);
            }
        });

//        Places.initialize(getApplicationContext(), getResources().getString(R.string.maps_api_key));
    }

    @Override
    protected void onDestroy() {
        pNetwork.clean();
        super.onDestroy();
    }

    public void updateInterval(View v) {
        INTERVAL = Long.parseLong(((EditText) findViewById(R.id.interval)).getText().toString());
        pNetwork.sendLocation(this, INTERVAL);
    }

    public void exportDb(View v) throws IOException {
        File source = getDatabasePath(LogDbHelper.DATABASE_NAME);
        File dest = new File(getExternalFilesDir(null), LogDbHelper.DATABASE_NAME);

        InputStream is = null;
        OutputStream os = null;
        try {
            if (!dest.exists())
                dest.createNewFile();
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            Snackbar.make(findViewById(R.id.switch1), "Saved: " + dest.getPath(), Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) is.close();
            if (os != null) os.close();
        }
    }
}
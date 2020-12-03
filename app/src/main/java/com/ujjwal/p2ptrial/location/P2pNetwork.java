package com.ujjwal.p2ptrial.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;

import chat.Chat;

public class P2pNetwork {
    public static final int LOCATION = 0;
    public static final int STRING = 1;
    public static final int PAIR = 3;
    public static final int LAT_LONG = 4;
    private static final String TAG = "P2pNetwork";
    public static boolean LOGGING_ENABLED = true;
    public static final int SESSION = 0;
    public static final int ACTIVITY = 1;
    public static final int ERROR = 2;

    private LocationUpdates lc;
    private final HandlerThread chatMain = new HandlerThread("Chat Main");
    private final HandlerThread generic = new HandlerThread("Generic Tasks");
    private static Handler genericHandler;
    private Logger logger;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public P2pNetwork(Context context) {
        init(context);
    }

    public void init(Context context) {
        chatMain.start();
        generic.start();
        genericHandler = new Handler(generic.getLooper());
        this.logger = new Logger(context);
        new Handler(chatMain.getLooper()).post(new Runnable() {
            @Override
            public void run() {
                long inv = System.currentTimeMillis();
                String peerId = Chat.chat();

                peerId = peerId.equals("") ? "self" : peerId;
                if (LOGGING_ENABLED)
                    writeLog(SESSION, peerId + "," + inv + "," + System.currentTimeMillis());
            }
        });
        this.lc = new LocationUpdates(this);

        locationRequest = new LocationRequest()
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    // Logic to handle location object
                    PeerLocation pl = new PeerLocation("self", location.getLatitude(),
                            location.getLongitude(), System.currentTimeMillis());
                    lc.addPeerLocation(pl);
                    publishMessage("" + location.getLatitude() + "," +
                            location.getLongitude() + "," + location.getSpeed() + "," +
                            location.getProvider() + "," + location.getAccuracy() + "," +
                            System.currentTimeMillis() + "," + location.getTime());
                } else {
                    Log.d(TAG, "onSuccess: got Null location");
                    publishMessage("00.00000,00.000000,0.00,,0.0,0,0");
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };
    }

    public String fetchMessages() {
        String cm = Chat.passMessages();
        if (LOGGING_ENABLED && !cm.equals(""))
            writeLog(ACTIVITY, "" + System.currentTimeMillis() + "," + cm);
        return cm;
    }

    public void publishMessage(String msg) {
        if (LOGGING_ENABLED && msg != null && !msg.equals(""))
            writeLog(ACTIVITY, System.currentTimeMillis() + ",0,self," + msg);
        Chat.transmitLocation(msg);
    }

    public void sendLocation(Activity activity, long interval) {
        if (fusedLocationProviderClient == null)
            fusedLocationProviderClient = new FusedLocationProviderClient(activity);
        locationRequest.setInterval(interval);

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "no permission");
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void sendLocation(Activity activity) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                sendLocation(activity, STRING);
                handler.postDelayed(this, 700);
            }
        });
    }

    public void sendLocation(Activity activity, int rType) {
        FusedLocationProviderClient fusedLocationClient = new FusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "No permission");
            publishMessage("00.00000,00.000000,0.00,,0.0,0,0");
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Logic to handle location object
                            PeerLocation pl = new PeerLocation("self", location.getLatitude(),
                                    location.getLongitude(), System.currentTimeMillis());
                            lc.addPeerLocation(pl);
                            publishMessage("" + location.getLatitude() + "," +
                                    location.getLongitude() + "," + location.getSpeed() + "," +
                                    location.getProvider() + "," + location.getAccuracy() + "," +
                                    System.currentTimeMillis() + "," + location.getTime());
                        } else {
                            Log.d(TAG, "onSuccess: got Null location");
                            publishMessage("00.00000,00.000000,0.00,,0.0,0,0");
                        }
                    }
                });
    }

    public void plotPeers(GoogleMap map) {
        lc.plotPeers(map);
    }

    public void plotPeers(GoogleMap map, long interval) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                lc.plotPeers(map);
                new Handler().postDelayed(this, interval);
            }
        });
    }

    public void fetchPeerLocation() {
        new Handler(chatMain.getLooper()).post(new Runnable() {
            @Override
            public void run() {
                lc.fetchPeerLocation();
            }
        });
    }

    public void clean() {
        chatMain.quit();
        genericHandler.post(new Runnable() {
            @Override
            public void run() {
                logger.clean();
            }
        });
        generic.quitSafely();
    }

    public void setLogging(boolean state) {
        LOGGING_ENABLED = state;
    }

    protected Handler getGenericHandler() {
        return genericHandler;
    }

    protected void notifyLoggerError(Context context, String msg) {
        Toast.makeText(context, "Logger Error: " + msg, Toast.LENGTH_LONG).show();
    }

    protected void writeLog(int type, String msg) {
        switch (type) {
            case SESSION:
                genericHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        logger.logSession(msg);
                    }
                });
                break;
            case ACTIVITY:
                genericHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        logger.logSessionActivity(msg);
                    }
                });
                break;
            case ERROR:
                genericHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        logger.logError(msg);
                    }
                });
                break;
        }
    }
}

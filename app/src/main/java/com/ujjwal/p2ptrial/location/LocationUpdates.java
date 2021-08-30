package com.ujjwal.p2ptrial.location;

import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Hashtable;

public class LocationUpdates {
    private float ALPHA = 1.0f;
    private BitmapDescriptor ICON_DESCRIPTOR;
    private static final String TAG = "LocationUpdates";

    private P2pNetwork pn = null;
    private final Hashtable<String, PeerLocation> ht = new Hashtable<>();
    private final Hashtable<String, Marker> htMarker = new Hashtable<>();

    public LocationUpdates(P2pNetwork pn) {
        this.pn = pn;
    }

    public void addPeerLocation(PeerLocation peerLocation) {
        ht.put(peerLocation.getPeerId(), peerLocation);
    }

    public void fetchPeerLocation() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            pn.writeLog(P2pNetwork.ERROR, "," + e.getMessage());
        }
        while (true) {
            String pid = "";
            try {
                String [] cm = pn.fetchMessages().split(",");
                PeerLocation pl = new PeerLocation();

                pid = cm[1];
                pl.setLastUpdate(Long.parseLong(cm[7]));
                pl.setPeerId(cm[1]);
                pl.setLat(Double.parseDouble(cm[2]));
                pl.setLog(Double.parseDouble(cm[3]));

                ht.put(pl.getPeerId(), pl);
                if (htMarker.get(pl.getPeerId()) != null && (pl.getLat() != 0.0 || pl.getLog() != 0.0))
                    htMarker.get(pl.getPeerId()).setPosition(new LatLng(pl.getLat(), pl.getLog()));

                Thread.sleep(10);
            } catch (Exception e) {
                pn.writeLog(P2pNetwork.ERROR, pid + "," + e.getMessage());
            }
        }
    }

    public void plotPeers(GoogleMap mMap) {
        for(String key: ht.keySet()) {
            float diff = System.currentTimeMillis() - ht.get(key).getLastUpdate();
            ALPHA = diff < 60000 ? (60000f - diff) / 60000 : 1.0f;
            ICON_DESCRIPTOR = key.equals("self") ?
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE) :
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            if (System.currentTimeMillis() - ht.get(key).getLastUpdate() < 60000)
                plot(mMap, ht.get (key));
//            System.out.println("Value of "+ key +" is: " + ht.get(key).getLat() + "," + ht.get(key).getLog());
        }
    }

    private void plot(GoogleMap map, PeerLocation peer) {
        LatLng pl = new LatLng(peer.getLat(), peer.getLog());
        if (htMarker.get(peer.getPeerId()) != null) {
            htMarker.get(peer.getPeerId()).setPosition(pl);
            htMarker.get(peer.getPeerId()).setAlpha(ALPHA);
        } else {
            MarkerOptions mo = new MarkerOptions()
                    .icon(ICON_DESCRIPTOR)
                    .position(pl)
                    .alpha(Math.max(ALPHA, 0))
                    .draggable(false)
                    .title(peer.getPeerId());
            htMarker.put(peer.getPeerId(), map.addMarker(mo));
        }

        if (peer.getPeerId().equals("self"))
            map.animateCamera(CameraUpdateFactory.newLatLng(pl));
    }
}

package com.softwarica.map;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akhgupta.easylocation.EasyLocationAppCompatActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;

public class LocationActivity extends EasyLocationAppCompatActivity {

    Button reqSingle, reqCont, stopReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        reqCont = findViewById(R.id.requestLocationUpdatesButton);
        reqSingle = findViewById(R.id.requestSingleLocationButton);
        stopReq = findViewById(R.id.stopLocationUpdatesButton);
        reqCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationRequest locationRequest = new LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                        .setInterval(5000)
                        .setFastestInterval(5000);
                EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                        .setLocationRequest(locationRequest)
                        .setFallBackToLastLocationTime(3000)
                        .build();
                requestLocationUpdates(easyLocationRequest);
                Log.i("TAG", "onClick: "+locationRequest.toString());
            }
        });

        reqSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationRequest locationRequest = new LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                        .setInterval(5000)
                        .setFastestInterval(5000);
                EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                        .setLocationRequest(locationRequest)
                        .setFallBackToLastLocationTime(3000)
                        .build();
                requestSingleLocationFix(easyLocationRequest);
            }
        });
        stopReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();
            }
        });
    }
    @Override
    public void onLocationPermissionGranted() {
        showToast("Location permission granted");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationPermissionDenied() {
        showToast("Location permission denied");
    }

    @Override
    public void onLocationReceived(Location location) {
        showToast(location.getProvider() + "," + location.getLatitude() + "," + location.getLongitude());
    }

    @Override
    public void onLocationProviderEnabled() {
        showToast("Location services are now ON");
    }

    @Override
    public void onLocationProviderDisabled() {
        showToast("Location services are still Off");
    }
}

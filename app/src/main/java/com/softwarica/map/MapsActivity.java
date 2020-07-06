package com.softwarica.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.akhgupta.easylocation.EasyLocationAppCompatActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import model.LongitudeLatitude;

public class MapsActivity extends EasyLocationAppCompatActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;

    public void centreMapOnLocation(Location location, String title){

        LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
        List<LongitudeLatitude> latLngs = new ArrayList<>();
        latLngs.add(new LongitudeLatitude(27.684448,85.249115,"Shanti Store"));
        latLngs.add(new LongitudeLatitude(27.684441, 85.249091,"Developer"));
        CameraUpdate center, zoom;
        for(int i = 0; i<latLngs.size();i++){
            center = CameraUpdateFactory.newLatLng(new LatLng(latLngs.get(i).getLatitude(),
                    latLngs.get(i).getLongtitude()));
            zoom = CameraUpdateFactory.zoomTo(50);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latLngs.get(i).getLatitude(),
                    latLngs.get(i).getLongtitude())).title(latLngs.get(i).getMarker());
//            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_black_24dp));
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.home));
            mMap.addMarker(marker);
//            mMap.moveCamera(center);
//            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centreMapOnLocation(lastKnownLocation,"Your Location");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button btnRequestLocation = findViewById(R.id.location);
        btnRequestLocation.setOnClickListener(new View.OnClickListener() {
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
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_black_24dp));
        List<LongitudeLatitude> latLngs = new ArrayList<>();
        latLngs.add(new LongitudeLatitude(27.684448,85.249115,"Shanti Store"));
        latLngs.add(new LongitudeLatitude(27.684441, 85.249091,"Developer"));
        CameraUpdate center, zoom;
        for(int i = 0; i<latLngs.size();i++){
            center = CameraUpdateFactory.newLatLng(new LatLng(latLngs.get(i).getLatitude(),
                    latLngs.get(i).getLongtitude()));
            zoom = CameraUpdateFactory.zoomTo(17);
            mMap.addMarker(new MarkerOptions().position(new LatLng(latLngs.get(i).getLatitude(),
                    latLngs.get(i).getLongtitude())).title(latLngs.get(i).getMarker()));
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone));
//            googleMap.addMarker()
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
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
//        showToast(location.getProvider() + "," + location.getLatitude() + "," + location.getLongitude());
        centreMapOnLocation(location,"I'm here");
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
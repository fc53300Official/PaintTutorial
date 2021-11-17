package com.example.canvas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private boolean locationPermissionGranted;
    private GoogleMap map;


    private Location lastLocationloc;
    private LatLng lastLocation;

    private Location currentLocationLoc;

    private LatLng currentLocation;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private Button draw;
    private boolean onDraw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setLocationRequest();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    lastLocationloc = currentLocationLoc;
                    currentLocationLoc = location;

                    lastLocation = new LatLng(lastLocationloc.getLatitude(), lastLocationloc.getLongitude());
                    currentLocation = new LatLng(currentLocationLoc.getLatitude(), currentLocationLoc.getLongitude());

                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 30));
                    if(onDraw){
                        map.addPolyline(new PolylineOptions().add(lastLocation, currentLocation));
                    }


                }
            }
        };


        SupportMapFragment supportMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (supportMap != null) {
            supportMap.getMapAsync(this);
        }

        draw = (Button) findViewById(R.id.button2);
        draw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(onDraw) {
                    onDraw = false;
                    draw.setText("Start draw");
                }else {
                    onDraw = true;
                    draw.setText("Stop draw");

                }

            }
        });
        getLocation();
        startLocationUpdateFused();
    }




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;
        updateLocation();
        getLocation();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocation();

    }

    private void checkLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            Log.d("permissionn", "truee");
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d("permissionn", "false");
        }
    }

    public void getLocation() {

        try {
            //Log.d("debug", "yyaa");
            Task<Location> locationRes = fusedLocationClient.getLastLocation();
            locationRes.addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {

                    currentLocationLoc = task.getResult();
                    if (currentLocationLoc != null) {
                        //currentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                        map.addMarker(new MarkerOptions().position(new LatLng(currentLocationLoc.getLatitude(),currentLocationLoc.getLongitude())));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocationLoc.getLatitude(),currentLocationLoc.getLongitude()), 30));
                    }
                } else {
                    Log.d("Failed", "Get location failed");

                }
            });
        } catch (SecurityException e) {
            Log.e("Exception %s", e.getMessage(), e);
        }


    }

    private void updateLocation() {
        //Log.d("debug", "updateeeee");
        checkLocationPermission();
        if (locationPermissionGranted) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            Log.d("finee", "fine");
            //map.addMarker(new MarkerOptions().position(currentLocation));
            //map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
            //Log.d("uppdate", "updateLocation: " + currentLocation.longitude);

        }

    }



    private void startLocationUpdates() {
        checkLocationPermission();
        if (locationPermissionGranted) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        }

    }

    private void setLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    /**private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            //PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
              //      Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }**/


    private void startLocationUpdateFused() {
        checkLocationPermission();
        if (locationPermissionGranted) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdateFused();

    }
}
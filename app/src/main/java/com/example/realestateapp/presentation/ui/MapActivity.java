package com.example.realestateapp.presentation.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityMapActivityBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityMapActivityBinding binding;
    private static GoogleMap myMap;
    private boolean chooseOnMap;
    static double house_latitude=-1;
    static double house_longitude=-1;
    Location cur_location=null;
    private static String address="";
    FusedLocationProviderClient fusedLocationProviderClient;

    private LocationRequest mLocationRequest= null;
    private LocationCallback mLocationCallback = null;
    private int LOCATION_REQUEST_INTERVAL = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chooseOnMap = getIntent().getBooleanExtra("chooseOnMap", false);
        if(!chooseOnMap)
            binding.btnSetLocation.setVisibility(View.GONE);

        house_latitude=getIntent().getDoubleExtra("latitude",-1);
        house_longitude=getIntent().getDoubleExtra("logintude",-1);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

        binding.ivCurrentLocation.setOnClickListener(V->{
            /*Location cur=getLastLocation();
            if(cur!=null) {
                LatLng cur_location = new LatLng(cur.getLatitude(), cur.getLongitude());
                myMap.addMarker(new MarkerOptions().position(cur_location).title("current location"));
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur_location, 16.0f));
                address=getAddress(cur_location.latitude,cur_location.longitude);
                house_latitude=cur_location.latitude;
                house_longitude=cur_location.longitude;
            }*/

            mLocationCallback= new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    double lat  = locationResult.getLastLocation().getLatitude();
                    double lng = locationResult.getLastLocation().getLongitude();

                    fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);

                    LatLng cur_location = new LatLng(lat,lng);
                    myMap.clear();
                    myMap.addMarker(new MarkerOptions().position(cur_location).title("current location"));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur_location, 16.0f));
                    address=getAddress(cur_location.latitude,cur_location.longitude);
                    house_latitude=cur_location.latitude;
                    house_longitude=cur_location.longitude;
                }
            };

            createLocationRequest();
        });

        binding.btnSetLocation.setOnClickListener(v->{
            if(chooseOnMap) {
                Intent intent = new Intent();
                intent.putExtra("address", address);
                intent.putExtra("latitude", house_latitude);
                intent.putExtra("longitude", house_longitude);
                setResult(RESULT_OK, intent);
                finish();
            }
            overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
        });

        binding.ivBtnBack.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL).setFastestInterval(LOCATION_REQUEST_INTERVAL);
        requestLocationUpdate();
    }

    private void requestLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {

            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
        );
    }

    private String getAddress(double latitude, double longitude){
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        String fullAddress="";
        try {
            addresses = gc.getFromLocation(latitude,longitude, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (addresses.size() == 1) {
            Address address= addresses.get(0);
            fullAddress= address.getLocality() +","+address.getAdminArea()+","+ address.getPostalCode()+".";
        } else {
            Toast.makeText(this,"No address found",Toast.LENGTH_SHORT).show();
        }
        return fullAddress;
    }
    private Location getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},300);
            return null;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    cur_location= location;
                    setLocation(cur_location.getLatitude(),cur_location.getLongitude());
                }
                else{
                    Toast.makeText(MapActivity.this,"Please turn on location and try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return cur_location;
    }

    private void setLocation(double latitude, double longitude){
        myMap.clear();
        LatLng locaiton = new LatLng(latitude, longitude);
        myMap.addMarker(new MarkerOptions().position(locaiton).title("location"));
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locaiton, 16.0f));
        address=getAddress(latitude,longitude);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if(chooseOnMap) {
                    setLocation(latLng.latitude, latLng.longitude);
                    address=getAddress(latLng.latitude,latLng.longitude);
                    house_latitude=latLng.latitude;
                    house_longitude=latLng.longitude;
                }
            }
        });

        if(house_longitude!=-1 || house_latitude!=-1)
            setLocation(house_latitude,house_longitude);
        else{
            Location loc=getLastLocation();
            if(loc!=null)
                setLocation(loc.getLatitude(),loc.getLongitude());
            }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==300){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else{
                Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
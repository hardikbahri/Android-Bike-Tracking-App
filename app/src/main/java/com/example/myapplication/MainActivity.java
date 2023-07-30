package com.example.myapplication;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;


import android.Manifest;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;


import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    public float x;
    public float y;
    private Location previousLocation;
    private long previousLocationTime;
    private double totalDistance = 0;
    private  long totaltime=0;
    private long appStartTime;

















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Create location request object
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000); // 1 second
        locationRequest.setFastestInterval(500); // 0.5 seconds


        SensorManager sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor acceleroSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,acceleroSensor,SensorManager.SENSOR_DELAY_GAME);


        appStartTime = System.currentTimeMillis();



        // Create location callback object
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // Handle location update
                Location location = locationResult.getLastLocation();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                long currentTime = System.currentTimeMillis();

                // Calculate distance and speed


                double speed = 0;
                if (previousLocation != null) {
                    double distance = 0;
                    distance = previousLocation.distanceTo(location);
                    totalDistance += distance;
                    long timeInterval = currentTime - previousLocationTime;
                    totaltime=totaltime+timeInterval;
                    speed = distance / (timeInterval / 1000.0); // Speed in m/s

                }
                previousLocation = location;
                previousLocationTime = currentTime;


                long cTime = System.currentTimeMillis();
                long timeSinceStart = cTime - appStartTime;


                TextView disttext=findViewById(R.id.txtDist);
                TextView speedtext=findViewById(R.id.txtSpeed);
                TextView timetext=findViewById(R.id.txtTime);
                TextView textView = findViewById(R.id.txtGPS);
                Log.d("GPS", "Latitude: " + latitude + " Longitude: " + longitude);

                textView.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                disttext.setText("Total Distance "+totalDistance);
                speedtext.setText("Speed "+speed);
                timetext.setText("Time elapsed "+timeSinceStart/1000);
            }
        };

        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            // Request location updates
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop location updates when activity is destroyed
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];


        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            handleAccelerometerData(x, y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void handleAccelerometerData(float x, float y) {

        // Calculate the magnitude of acceleration
        double accelerationMagnitude = Math.abs(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) ));

        TextView textView = findViewById(R.id.txtAcc);



        // threshold for acceleration
        double accelerationThreshold = 0;

        // If the magnitude of acceleration exceeds the threshold, the bike is moving
        if (accelerationMagnitude > accelerationThreshold) {


            // Determine the direction of movement using the accelerometer readings
            if (Math.abs(x) > Math.abs(y) ) {
                if (x > 0) {
//                    Log.d("Accelerometer", "X: " + x);
//                    // Bike is moving towards the right
//                    textView.setText("the bike is moving towards right");
//                    Log.d("Accelerometer", "Moving towards the right");

                } else {
//                    // Bike is moving towards the left
//                    textView.setText("the bike is moving towards left");
                }
            } else if (Math.abs(y) > Math.abs(x) ) {
                if (y > 0) {
                    // Bike is moving forwards
                    textView.setText("the bike is moving forward");
                } else {
                    // Bike is moving backwards
                    textView.setText("the bike is moving forward");
                }
            }
        }
    }





}



























//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private double latitude = 37.4220;
//    private double longitude = -122.0841;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker at a specific latitude and longitude and move the camera
//        LatLng location = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//    }
//}






package com.example.myruns5;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GPSRunActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, SensorEventListener {

    private NotificationManager notificationManager;
    private final int NOTIFY_ID = 11;

    private HistoryData historyData;

    private GoogleMap mMap;
    private LocationManager locationManager;
    public static final int PERMISSION_REQUEST_CODE = 0;

    private Marker startMarker;
    private Marker currentMarker;
    private Polyline polyline;
    private PolylineOptions polylineOptions;

    private TextView textview_type;
    private TextView textview_avg_speed;
    private int avg_spd;
    private TextView textview_cur_speed;
    private TextView textview_climb;
    private double last_alt;
    private int distance_climbed;
    private TextView textview_calorie;
    private TextView textview_distance;
    private int distance_traveled;

    private long init_time;
    private long time_passed;

    // All of the globals for automatic mode
    private SensorManager sensorManager;
    private boolean is_automatic = false;
    ArrayList<SensorEvent> sensorEvents;
    FFT fft;
    private String automatic_activity_type;
    private int[] automatic_activty_type_counts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsrun);

        showNotification();

        historyData = new HistoryData(this);

        Intent intent = getIntent();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        textview_type = findViewById(R.id.textview_type);
        textview_type.setTextColor(Color.BLACK);

        is_automatic = intent.getBooleanExtra("automatic", false);
        if( is_automatic ) {

            textview_type.setText("Type: Standing");
        }
        else {

            textview_type.setText("Type: " + intent.getStringExtra("activity_type"));
        }

        textview_avg_speed = findViewById(R.id.textview_avg_speed);
        textview_avg_speed.setTextColor(Color.BLACK);
        textview_avg_speed.setText("Avg speed: 0.0");
        avg_spd = 0;


        textview_cur_speed = findViewById(R.id.textview_cur_speed);
        textview_cur_speed.setTextColor(Color.BLACK);
        textview_cur_speed.setText("Cur speed: 0.0");

        textview_climb = findViewById(R.id.textview_climb);
        textview_climb.setTextColor(Color.BLACK);
        textview_climb.setText("Climb: 0");
        distance_climbed = 0;
        last_alt = 99999;

        textview_calorie = findViewById(R.id.textview_calorie);
        textview_calorie.setTextColor(Color.BLACK);
        textview_calorie.setText("Calorie: 0");

        textview_distance = findViewById(R.id.textview_distance);
        textview_distance.setTextColor(Color.BLACK);
        textview_distance.setText("Distance: 0");
        distance_traveled = 0;

        init_time = System.currentTimeMillis();
        time_passed = 0;

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorEvents = new ArrayList<>();
        fft = new FFT(64);
        automatic_activity_type = "Calculating...";
        automatic_activty_type_counts = new int[]{ 0, 0, 0 };
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkPermission();
    }

    public void initLocationManager(){
        try {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Gets the provider and sets the accuracy to fine
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locationManager.getBestProvider(criteria, true);

            Location location = null;

            // Sets the default location to our last known location
            if(provider != null) location = locationManager.getLastKnownLocation(provider);


            // Places a marker at our initial location
            if(location != null) {

                startMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("start"));
                currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("current").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                polylineOptions = new PolylineOptions();
                polylineOptions.add(startMarker.getPosition());
                polylineOptions.add(currentMarker.getPosition());
                polylineOptions.color(Color.BLACK);

                polyline = mMap.addPolyline(polylineOptions);

                onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 0, 0, this);

        }catch (SecurityException e){

            Log.d("rafa", "SecurityException");
        }
    }


    private Location lastLocation;

    public void onLocationChanged(Location location) {

        Log.d("rafa", "location updated.");

        LatLng currentCoords = new LatLng(location.getLatitude(), location.getLongitude());

        // Adds the new point to the polyline
        List<LatLng> polylinePoints = polyline.getPoints();
        polylinePoints.add(currentCoords);
        polyline.setPoints(polylinePoints);

        // Keep the camera moving with us
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoords, 16));

        // Keep the green marker moving with us
        currentMarker.setPosition(currentCoords);

        // Update our current speed
        float current_speed = location.getSpeed();
        textview_cur_speed.setText("Cur speed: " + current_speed);

        time_passed = System.currentTimeMillis() - init_time;
        if(lastLocation != null) distance_traveled += location.distanceTo(lastLocation);
        textview_avg_speed.setText("Avg speed: " + (distance_traveled / time_passed) );
        textview_distance.setText("Distance: " + distance_traveled);

        lastLocation = location;


        // If our altitude increased, add the difference to the distance climbed!
        double cur_altitude = location.getAltitude();
        if( last_alt < cur_altitude ) {

            distance_climbed += (cur_altitude - last_alt);
            last_alt = cur_altitude;
        }
        else {

            last_alt = cur_altitude;
        }
        textview_climb.setText("Climb: " + distance_climbed);


        textview_calorie.setText("Calorie: " + ((distance_climbed + distance_traveled) * 1.5));


        // If we're in automatic mode, update the activity-type
        if( is_automatic ) {

            textview_type.setText("Type: " + automatic_activity_type);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {

            Log.d("rafa", "accelerating...");

            if (sensorEvents.size() < 64) {

                sensorEvents.add(event);
            } else {

                double max = -99999;

                double[] re = new double[64];
                double[] im = new double[64];

                // Sets "im" array to all 0s
                for (int i = 0; i < 64; i++) {

                    im[i] = 0;
                }

                // Generate "re"
                // An array of all the magnitudes
                int re_i = 0;
                for (SensorEvent sensorEvent : sensorEvents) {

                    double x = sensorEvent.values[0] / SensorManager.GRAVITY_EARTH;
                    double y = sensorEvent.values[1] / SensorManager.GRAVITY_EARTH;
                    double z = sensorEvent.values[2] / SensorManager.GRAVITY_EARTH;

                    double magnitude = Math.sqrt(x * x + y * y + z * z);

                    re[re_i] = magnitude;
                    re_i++;

                    // Updates the max value
                    if (magnitude > max) max = magnitude;
                }


                // modifies the two arrays by reference
                fft.fft(re, im);


                ArrayList<Double> featureVector = new ArrayList<>();

                for (int i = 0; i < re.length; i++) {

                    double magnitude = Math.sqrt(re[i] * re[i] + im[i] * im[i]);

                    featureVector.add(Double.valueOf(magnitude));

                    im[i] = .0;
                }


                // Adds the final max value at index 64 (65th element)
                featureVector.add(Double.valueOf(max));

                try {

                    int classify_result = (int) WekaClassifier.classify(featureVector.toArray());

                    switch (classify_result) {

                        case 0:

                            automatic_activity_type = "Standing";
                            automatic_activty_type_counts[0]++;
                            break;

                        case 1:

                            automatic_activity_type = "Walking";
                            automatic_activty_type_counts[1]++;
                            break;

                        case 2:

                            automatic_activity_type = "Running";
                            automatic_activty_type_counts[2]++;
                            break;

                        default:

                            automatic_activity_type = "Classification-error";
                    }

                } catch (Exception e) {

                    Log.d("rafa", "ERROR: Weka failed to classify");
                }


            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    public void onDestroy(){
        super.onDestroy();

        notificationManager.cancel(NOTIFY_ID);

        if(locationManager != null)
            locationManager.removeUpdates(this);
    }

    public void onProviderDisabled(String provider) {}
    public void onProviderEnabled(String provider) {}
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public void checkPermission(){

        if(Build.VERSION.SDK_INT < 23) return;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        else
            initLocationManager();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        if(requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                initLocationManager();
        }
    }





    public void showNotification(){

        final String CHANNEL_ID = "notification channel";
        final int PENDINGINTENT_REQUEST_CODE = 0;
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent = new Intent(this, GPSRunActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, PENDINGINTENT_REQUEST_CODE,
                intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notificationBuilder.setContentTitle("Using location");
        notificationBuilder.setContentText("MyRuns5 is tracking your location.");
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.common_full_open_on_phone);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // or builder.setAutoCancel(true);


        if(Build.VERSION.SDK_INT > 26) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "channel name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(NOTIFY_ID, notification);
    }


    public void mapSaveClicked(View view) {

        Calendar calendar = Calendar.getInstance();

        int[] date;
        int[] time;

        date = new int[] {
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        };

        time = new int[] {
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE)
        };


        // Converts time_passed from milliseconds to seconds
        time_passed /= 1000;


        String activity_type = "";
        if( is_automatic ) {

            // If we're in automatic mode, records the activity-type as the type we spent the most time in
            if( automatic_activty_type_counts[0] >= automatic_activty_type_counts[1] && automatic_activty_type_counts[0] >= automatic_activty_type_counts[2]) {

                activity_type = "Standing";
            }
            else if( automatic_activty_type_counts[1] >= automatic_activty_type_counts[2] ) {

                activity_type = "Walking";
            }
            else {

                activity_type = "Running";
            }
        }
        else activity_type = getIntent().getStringExtra("activity_type");

        Entry entry = new Entry(
                "GPS",
                activity_type,
                (int)time_passed,
                distance_traveled,
                (int)((distance_climbed + distance_traveled) * 1.5),
                0,
                "no comment",
                date,
                time);

        historyData.insert(entry);




        GPSEntry gpsEntry = new GPSEntry(
                (int)time_passed,
                startMarker,
                currentMarker
        );

        historyData.insert(gpsEntry);

        Toast.makeText(this, "Run successfully saved.", Toast.LENGTH_SHORT).show();

        finish();
    }


    public void mapCancelClicked(View view) {

        Toast.makeText(this, "Entry discarded.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

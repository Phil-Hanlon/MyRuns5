package com.example.myruns5;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GPSHistoryItemActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HistoryData historyData;

    private String duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpshistory_item);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Get markers from database
        duration = getIntent().getStringExtra("entry_string").split("Miles, ")[1].split(" secs")[0];

        historyData = new HistoryData(this);

        ArrayList<LatLng> marker_latlngs = historyData.get_map_data(Integer.parseInt(duration));


        // And add the markers to the map!
        mMap.addMarker(new MarkerOptions().position(marker_latlngs.get(0)).title("start"));
        mMap.addMarker(new MarkerOptions().position(marker_latlngs.get(1)).title("finish").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker_latlngs.get(0)));
    }



    public void gps_delete_clicked(View view) {

        historyData.delete(duration, ImpSQLiteOpenHelper.TABLE_GPS);

        finish();
    }
}

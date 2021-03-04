package com.logisticscompany.activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.logisticscompany.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btnSubmit;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(100,intent);
                finish();
            }
        });
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

        // Add a marker in Sydney and move the camera
        LatLng vjy = new LatLng(45.508888, -73.561668);
        mMap.addMarker(new MarkerOptions().position(vjy));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vjy));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 11.0f ) );
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point){
                Toast.makeText(getApplicationContext(),
                        point.latitude + ", " + point.longitude,
                        Toast.LENGTH_SHORT).show();
                mMap.clear();
                LatLng latest = new LatLng(point.latitude, point.longitude);
                mMap.addMarker(new MarkerOptions().position(latest));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latest));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 11.0f ) );
                 intent=new Intent();
                intent.putExtra("lat",point.latitude+"");
                intent.putExtra("lng",point.longitude+"");
                try {
                    getAddress(point.latitude,point.longitude);
                }catch (Exception e){}
            }
        });
    }
    String address1="";
    private void getAddress(double latitude,double longitude) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        address1=address+","+city+","+postalCode;
        intent.putExtra("address",address1);
    }
}

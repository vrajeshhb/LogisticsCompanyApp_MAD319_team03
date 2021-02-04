package com.logisticscompany.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.logisticscompany.R;

public class DriversDashboardActivity extends AppCompatActivity {
    CardView cdAvailableTrips,cdCurrentTrips,cdCompletedTrips,editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_dashboard);

        getSupportActionBar().setTitle("Driver Dashboard");

        cdAvailableTrips=(CardView)findViewById(R.id.cdAvailableTrips);
        cdAvailableTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriversDashboardActivity.this,AvailableTripsActivity.class));

            }
        });
        cdCurrentTrips=(CardView)findViewById(R.id.cdCurrentTrips);
        cdCurrentTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriversDashboardActivity.this,CurrentTripsActivity.class));


            }
        });
        cdCompletedTrips=(CardView)findViewById(R.id.cdCompletedTrips);
        cdCompletedTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriversDashboardActivity.this,CompletedTripsActivity.class));


            }
        });

        editprofile=(CardView)findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriversDashboardActivity.this,DriverEditProfileActivity.class));


            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuEditProfile){
            startActivity(new Intent(DriversDashboardActivity.this,DriverEditProfileActivity.class));
        }
        else if (id== R.id.menuLogout){
            startActivity(new Intent(DriversDashboardActivity.this,MainActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
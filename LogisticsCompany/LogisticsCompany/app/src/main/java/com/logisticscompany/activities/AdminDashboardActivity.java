package com.logisticscompany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.logisticscompany.R;

public class AdminDashboardActivity extends AppCompatActivity {
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    CardView cdAddTrip,cdViewTrip,editprofile,driverinfo,cdCurrentTrips,cdCompletedTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        navigationView();

        cdAddTrip=(CardView)findViewById(R.id.cdAddTrip);
        cdViewTrip=(CardView)findViewById(R.id.cdViewTrip);
        editprofile=(CardView)findViewById(R.id.editprofile);
        driverinfo=(CardView)findViewById(R.id.driverinfo);
        cdCurrentTrips=(CardView)findViewById(R.id.cdCurrentTrips);
        cdCompletedTrips=(CardView)findViewById(R.id.cdCompletedTrips);

        cdAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,AddTripActivity.class));
            }
        });
        cdViewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ViewMyTripsActivity.class));

            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,AdminEditProfileActivity.class));


            }
        });
        driverinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,ViewDriversInfoActivity.class));


            }
        });


        cdCurrentTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,AdminCurrentTripsActivity.class));


            }
        });
        cdCompletedTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,AdminCompletedTripsActivity.class));


            }
        });


    }
    private void navigationView(){
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.addTrip:
                        Intent intent = new Intent(getApplicationContext(), AddTripActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.viewTrip:
                        Intent intent1 = new Intent(getApplicationContext(), ViewMyTripsActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.viewDriversInfo:
                        Intent driverinfo = new Intent(getApplicationContext(), ViewDriversInfoActivity.class);
                        startActivity(driverinfo);
                        break;


                    case R.id.myReq:
                        Intent myreq = new Intent(getApplicationContext(), AdminRequestsActivity.class);
                        startActivity(myreq);
                        break;

                    case R.id.edit_profile:
                        Intent view_jobs = new Intent(getApplicationContext(), AdminEditProfileActivity.class);
                        startActivity(view_jobs);
                        break;

                    case R.id.logout:
                        Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(logout);
                        finish();
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
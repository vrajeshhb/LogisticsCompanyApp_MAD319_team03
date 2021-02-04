package com.logisticscompany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.logisticscompany.R;
import com.logisticscompany.adapters.ViewTripsAdapter;
import com.logisticscompany.models.ViewTripsPojo;

import java.util.ArrayList;
import java.util.List;

public class ViewTripsActivity extends AppCompatActivity {
    List<ViewTripsPojo> viewTripsPojo;
    ListView list_view;
    private String parentDbName = "Add_trips";
    String username;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    DatabaseReference dbMyTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trips);

        getSupportActionBar().setTitle("My Trips");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sp = getSharedPreferences("AA", 0);
        username = sp.getString("uname", "-");

        list_view=(ListView)findViewById(R.id.list_view);
        //viewTripsPojo=new ArrayList<>();
        //dbMyTrips = FirebaseDatabase.getInstance().getReference("Add_trips").child(username);
        //dbMyTrips.addListenerForSingleValueEvent(valueEventListener);
        getTripDetails();


    }
    private void getTripDetails() {
        viewTripsPojo = new ArrayList<>();
        progressDialog = new ProgressDialog(ViewTripsActivity.this);
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("Add_trips").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            progressDialog.dismiss();
            viewTripsPojo.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ViewTripsPojo student = snapshot.getValue(ViewTripsPojo.class);
                    viewTripsPojo.add(student);
                }
                if (viewTripsPojo.size() > 0) {
                    list_view.setAdapter(new ViewTripsAdapter( ViewTripsActivity.this,viewTripsPojo));
                }
            } else {
                Toast.makeText(ViewTripsActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();

        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
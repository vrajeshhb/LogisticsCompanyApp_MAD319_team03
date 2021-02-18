package com.logisticscompany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logisticscompany.R;

import java.util.HashMap;

public class TripDetailsActivity extends AppCompatActivity {
    TextView tvTypeOfLoad,tvPickupTime,tvTripdate,tvPickUpLocation,tvDeliveryTime,tvDeliveryLocation,tvCostperHour;
    Button btnAccept,btnReject;
    DatabaseReference RootRef;
    private String parentDbName = "Add_trips";
    String username,typeofload,pickuptime,pickuplocation,deliverytime,deliverylocation,costperhour,time_date,Driveruname,tripdate;
    SharedPreferences sp;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        getSupportActionBar().setTitle("Trip Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingBar = new ProgressDialog(this);
        RootRef = FirebaseDatabase.getInstance().getReference();

        tvTypeOfLoad=(TextView) findViewById(R.id.tvTypeOfLoad);
        tvPickupTime=(TextView)findViewById(R.id.tvPickupTime);
        tvPickUpLocation=(TextView)findViewById(R.id.tvPickUpLocation);
        tvDeliveryTime=(TextView)findViewById(R.id.tvDeliveryTime);
        tvDeliveryLocation=(TextView)findViewById(R.id.tvDeliveryLocation);
        tvCostperHour=(TextView)findViewById(R.id.tvCostperHour);
        tvTripdate=(TextView)findViewById(R.id.tvTripdate);

        sp = getSharedPreferences("AA", 0);
        Driveruname = sp.getString("uname", "-");

        tvTypeOfLoad.setText("Type Of Load  :"+getIntent().getStringExtra("typeofload"));
        tvPickupTime.setText("Pick Up Time  :"+getIntent().getStringExtra("pickuptime"));
        tvPickUpLocation.setText("Pick Up Location  :"+getIntent().getStringExtra("pickuplocation"));
        tvDeliveryTime.setText("Delivery Time  :"+getIntent().getStringExtra("deliverytime"));
        tvDeliveryLocation.setText("Delivery Location  :"+getIntent().getStringExtra("deliverylocation"));
        tvCostperHour.setText("Cost Per Hour  :"+getIntent().getStringExtra("costperhour")+"$");
        tvTripdate.setText("Date  :"+getIntent().getStringExtra("tripdate"));

        username = getIntent().getStringExtra("username");
        typeofload = getIntent().getStringExtra("typeofload");
        pickuptime = getIntent().getStringExtra("pickuptime");
        pickuplocation = getIntent().getStringExtra("pickuplocation");
        deliverytime = getIntent().getStringExtra("deliverytime");
        deliverylocation = getIntent().getStringExtra("deliverylocation");
        costperhour = getIntent().getStringExtra("costperhour");
        time_date = getIntent().getStringExtra("timedate");
        tripdate = getIntent().getStringExtra("tripdate");

        btnAccept=(Button)findViewById(R.id.btnAccept);
        btnReject=(Button)findViewById(R.id.btnReject);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TripDetailsActivity.this, "Coming Soon  "+username, Toast.LENGTH_SHORT).show();
                UpdateTripDetails("Accepted");

            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TripDetailsActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                UpdateTripDetails("Rejected");
            }
        });

    }

    private void UpdateTripDetails(String status) {

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("typeofload", typeofload);
                userdataMap.put("pickuptime", pickuptime);
                userdataMap.put("pickuplocation", pickuplocation);
                userdataMap.put("deliverytime", deliverytime);
                userdataMap.put("deliverylocation", deliverylocation);
                userdataMap.put("costperhour", costperhour);
                userdataMap.put("status", status);
                userdataMap.put("tripData_Time", time_date);
                userdataMap.put("tripdate", tripdate);
                userdataMap.put("username", username);
                userdataMap.put("Drivername", Driveruname);
                RootRef.child(parentDbName).child(time_date).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(TripDetailsActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), DriversDashboardActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(TripDetailsActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


            }

        });

    }
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
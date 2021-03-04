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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logisticscompany.R;

import java.util.HashMap;

public class AdminEditTripActivity extends AppCompatActivity {
    TextInputEditText textTypeOfLoad,textPicuptime,textPicuplocation,textDeliveryTime,textDeliveryLocation,textCostPerHour;
    Button buttonUpdate;
    ProgressDialog loadingBar;
    DatabaseReference RootRef;
    private String parentDbName = "Add_trips";
    String time_date;
    String username;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_trip);

        getSupportActionBar().setTitle("Edit Trip");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingBar = new ProgressDialog(this);
        RootRef = FirebaseDatabase.getInstance().getReference();
        sp = getSharedPreferences("AA", 0);
        username = sp.getString("uname", "-");

        textTypeOfLoad=(TextInputEditText)findViewById(R.id.textTypeOfLoad);
        textPicuptime=(TextInputEditText)findViewById(R.id.textPicuptime);
        textPicuplocation=(TextInputEditText)findViewById(R.id.textPicuplocation);
        textDeliveryTime=(TextInputEditText)findViewById(R.id.textDeliveryTime);
        textDeliveryLocation=(TextInputEditText)findViewById(R.id.textDeliveryLocation);
        textCostPerHour=(TextInputEditText)findViewById(R.id.textCostPerHour);

        textTypeOfLoad.setText(getIntent().getStringExtra("typeofload"));
        textPicuptime.setText(getIntent().getStringExtra("pickuptime"));
        textPicuplocation.setText(getIntent().getStringExtra("pickuplocation"));
        textDeliveryTime.setText(getIntent().getStringExtra("deliverytime"));
        textDeliveryLocation.setText(getIntent().getStringExtra("deliverylocation"));
        textCostPerHour.setText(getIntent().getStringExtra("costperhour"));


        time_date=getIntent().getStringExtra("timedate");
        buttonUpdate=(Button)findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTripDetails();
                //Toast.makeText(AdminEditTripActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void EditTripDetails() {

        String typeofload = textTypeOfLoad.getText().toString();
        String pickuptime = textPicuptime.getText().toString();
        String pickplocation = textPicuplocation.getText().toString();
        String deliverytime = textDeliveryTime.getText().toString();
        String deliverylocation = textDeliveryLocation.getText().toString();
        String costperhour = textCostPerHour.getText().toString();
        String tripdate = getIntent().getStringExtra("tripdate");

        if (TextUtils.isEmpty(typeofload)) {
            Toast.makeText(this, "Type of load should not be empty...", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pickuptime)) {
            Toast.makeText(this, "Pick up time should not be empty...", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pickplocation)) {
            Toast.makeText(this, "Pick up location should not be empty...", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(deliverytime)) {
            Toast.makeText(this, "Delvery time should not be empty...", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(deliverylocation)) {
            Toast.makeText(this, "Delivery Location should not be empty...", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(costperhour)) {
            Toast.makeText(this, "Cost per hour should not be empty...", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("typeofload", typeofload);
                    userdataMap.put("pickuptime", pickuptime);
                    userdataMap.put("pickuplocation", pickplocation);
                    userdataMap.put("deliverytime", deliverytime);
                    userdataMap.put("deliverylocation", deliverylocation);
                    userdataMap.put("costperhour", costperhour);
                    userdataMap.put("status","Available");
                    userdataMap.put("tripData_Time",time_date);
                    userdataMap.put("tripdate",tripdate);

                    userdataMap.put("PLLat", getIntent().getStringExtra("PLLat"));
                    userdataMap.put("PLlng", getIntent().getStringExtra("PLlng"));
                    userdataMap.put("DLLat", getIntent().getStringExtra("DLLat"));
                    userdataMap.put("DLLng", getIntent().getStringExtra("DLLng"));

                    userdataMap.put("username", username);
                    RootRef.child(parentDbName).child(time_date).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminEditTripActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), ViewMyTripsActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(AdminEditTripActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

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

package com.logisticscompany.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {
    TextInputEditText textTypeOfLoad,textPicuptime,
            textPicuplocation,textDeliveryTime,textDeliveryLocation,textCostPerHour,etDate;
    Button buttonSubmit;
    ProgressDialog loadingBar;
    SharedPreferences sp;
    String username;
    String currentDate,currentTime;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    Button btnPickupLocation,btnDeliveryLocation;
    TextView tvPickLoc,tvDeliveryLoc;
    String PLLat,PLlng,DLLat,DLLng;
    public static final int PICK_UP_FG=100,DEL_FG=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        getSupportActionBar().setTitle("Add Trip");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textTypeOfLoad=(TextInputEditText)findViewById(R.id.textTypeOfLoad);
        tvPickLoc=(TextView)findViewById(R.id.tvPickLoc);
        tvDeliveryLoc=(TextView)findViewById(R.id.tvDeliveryLoc);
        btnPickupLocation=(Button) findViewById(R.id.btnPickUpLocation);
        btnDeliveryLocation=(Button) findViewById(R.id.btnDeliveryLocation);
        btnPickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(),MapsActivity.class),PICK_UP_FG);
            }
        });

        btnDeliveryLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(),MapsActivity.class),DEL_FG);
            }
        });

        textPicuptime=(TextInputEditText)findViewById(R.id.textPicuptime);
        textPicuplocation=(TextInputEditText)findViewById(R.id.textPicuplocation);
        textDeliveryTime=(TextInputEditText)findViewById(R.id.textDeliveryTime);
        textDeliveryLocation=(TextInputEditText)findViewById(R.id.textDeliveryLocation);
        textCostPerHour=(TextInputEditText)findViewById(R.id.textCostPerHour);
        etDate=(TextInputEditText)findViewById(R.id.etDate);


        loadingBar = new ProgressDialog(AddTripActivity.this);

         currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
         currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


        etDate.setFocusable(false);
        etDate.setClickable(true);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();

            }
        });

        textPicuptime.setFocusable(false);
        textDeliveryTime.setFocusable(false);

        textPicuptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickUpTime();
            }
        });


        textDeliveryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDropUpTime();
            }
        });

        sp = getSharedPreferences("AA", 0);
        username = sp.getString("uname", "-");

        buttonSubmit=(Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTripDetails();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(PICK_UP_FG==requestCode){
            tvPickLoc.setText("Lat : "+data.getStringExtra("lat")+" | Lng : "+data.getStringExtra("lng"));
            PLLat=data.getStringExtra("lat");
            PLlng=data.getStringExtra("lng");
            textPicuplocation.setText(data.getStringExtra("address"));
        }else{
            tvDeliveryLoc.setText("Lat : "+data.getStringExtra("lat")+" | Lng : "+data.getStringExtra("lng"));
            DLLat=data.getStringExtra("lat");
            DLLng=data.getStringExtra("lng");
            textDeliveryLocation.setText(data.getStringExtra("address"));
        }
        //Toast.makeText(getApplicationContext(),data.getStringExtra("lat"),Toast.LENGTH_SHORT).show();
    }

    private void AddTripDetails() {

        String typeofload = textTypeOfLoad.getText().toString();
        String pickuptime = textPicuptime.getText().toString();
        String pickplocation = textPicuplocation.getText().toString();
        String deliverytime = textDeliveryTime.getText().toString();
        String deliverylocation = textDeliveryLocation.getText().toString();
        String costperhour = textCostPerHour.getText().toString();

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
        } else {
            loadingBar.setTitle("Adding Trip Details");
            loadingBar.setMessage("Please wait, while we are Adding your trip Details.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AddTripDetailsToDatabase(typeofload, pickuptime, pickplocation, deliverytime, deliverylocation, costperhour);
        }
    }

    private void AddTripDetailsToDatabase(String typeofload, String pickuptime, String pickplocation, String deliverytime, String deliverylocation, String costperhour) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("typeofload", typeofload);
                userdataMap.put("pickuptime", pickuptime);
                userdataMap.put("pickuplocation", pickplocation);
                userdataMap.put("deliverytime", deliverytime);
                userdataMap.put("deliverylocation", deliverylocation);
                userdataMap.put("costperhour", costperhour);
                userdataMap.put("status","Available");
                userdataMap.put("tripData_Time",currentDate+currentTime);
                userdataMap.put("tripdate",etDate.getText().toString());
                userdataMap.put("username", username);
                userdataMap.put("PLLat", PLLat);
                userdataMap.put("PLlng", PLlng);
                userdataMap.put("DLLat", DLLat);
                userdataMap.put("DLLng", DLLng);

                RootRef.child("Add_trips").child(currentDate+currentTime).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(AddTripActivity.this, "Trip Details Added Succussfully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(AddTripActivity.this, AdminDashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(AddTripActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setPickUpTime(){

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textPicuptime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }


    public void setDropUpTime(){

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textDeliveryTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
    public void datepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        etDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
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




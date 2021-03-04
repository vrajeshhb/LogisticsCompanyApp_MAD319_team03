package com.logisticscompany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HireDriverActivity extends AppCompatActivity {
    ImageView ImageView;
    TextView tvDriverName,tvPhoneno,tvEmail,tvvehicleType,tvVehicleno;
    Button btnHireDriver;
    TextInputEditText etMessage;
    DatabaseReference RootRef;
    String currentDate,currentTime;
    SharedPreferences sp;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_driver);

        getSupportActionBar().setTitle("Driver Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp = getSharedPreferences("AA", 0);
        username = sp.getString("uname", "-");

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        RootRef = FirebaseDatabase.getInstance().getReference();


        ImageView=(ImageView)findViewById(R.id.ImageView);
        Glide.with(HireDriverActivity.this).load(getIntent().getStringExtra("image")).into(ImageView);

        tvDriverName=(TextView)findViewById(R.id.tvDriverName);
        tvPhoneno=(TextView)findViewById(R.id.tvPhoneno);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvvehicleType=(TextView)findViewById(R.id.tvvehicleType);
        tvVehicleno=(TextView)findViewById(R.id.tvVehicleno);
        etMessage=(TextInputEditText)findViewById(R.id.etMessage);

        tvDriverName.setText("Driver Name: "+getIntent().getStringExtra("name"));
        tvPhoneno.setText("Phone No: "+getIntent().getStringExtra("phoneno"));
        tvEmail.setText("Email: "+getIntent().getStringExtra("email"));
        tvvehicleType.setText("Vehicle Type: "+getIntent().getStringExtra("vehicletype"));
        tvVehicleno.setText("Vehicle No: "+getIntent().getStringExtra("vehiclenumber"));

        btnHireDriver=(Button)findViewById(R.id.btnHireDriver);
        btnHireDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMessage.getText().toString().isEmpty()){
                    Toast.makeText(HireDriverActivity.this, "You Want to say something to driver ..", Toast.LENGTH_SHORT).show();
                    return;
                }
                HireaDriver();

            }
        });
    }

    ProgressDialog loadingBar;
    public void HireaDriver(){

        loadingBar = new ProgressDialog(HireDriverActivity.this);
        loadingBar.setTitle("Adding Trip Details");
        loadingBar.setMessage("Please wait, while we are Adding your Details.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("name", getIntent().getStringExtra("name"));
                userdataMap.put("Email", getIntent().getStringExtra("email"));
                userdataMap.put("VehicleType", getIntent().getStringExtra("vehicletype"));
                userdataMap.put("VehicleNumber", getIntent().getStringExtra("vehiclenumber"));
                userdataMap.put("Phoneno", getIntent().getStringExtra("phoneno"));
                userdataMap.put("DateTime", currentDate+currentTime+"_"+username);
                userdataMap.put("Hire_by", username);
                userdataMap.put("message", etMessage.getText().toString());
                userdataMap.put("status", "Requested");
                userdataMap.put("Drivername", getIntent().getStringExtra("driver_uname"));

                RootRef.child("Hire_Drivers").child(currentDate+currentTime+"_"+username).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(HireDriverActivity.this, "Your request sent succussfully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(HireDriverActivity.this, AdminDashboardActivity.class);
                                    HireDriverActivity.this.startActivity(intent);
                                    finish();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(HireDriverActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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
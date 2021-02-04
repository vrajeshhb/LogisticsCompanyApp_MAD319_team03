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
import com.logisticscompany.models.DriverPojo;
import com.logisticscompany.models.Users;

import java.util.HashMap;

public class DriverEditProfileActivity extends AppCompatActivity {

    TextInputEditText et_email,et_username,et_password,et_last_name,et_first_name,et_phoneno,et_type_of_vehicle,et_vehicle_reg_num;
    Button btn_updateprofile;
    ProgressDialog loadingBar;
    DatabaseReference RootRef;
    private String parentDbName = "Driver_Registration";
    String uName;
    SharedPreferences sp;
    String fname,lname,phone,email,password,username,typeofvehicle,vehicleregnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_edit_profile);

        getSupportActionBar().setTitle("Driver Edit Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RootRef = FirebaseDatabase.getInstance().getReference();
        sp = getSharedPreferences("AA", 0);
        uName = sp.getString("uname", "-");


        et_last_name = (TextInputEditText) findViewById(R.id.et_last_name);
        et_first_name = (TextInputEditText) findViewById(R.id.et_first_name);
        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_phoneno = (TextInputEditText) findViewById(R.id.et_phoneno);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        et_type_of_vehicle = (TextInputEditText) findViewById(R.id.et_type_of_vehicle);
        et_vehicle_reg_num = (TextInputEditText) findViewById(R.id.et_vehicle_reg_num);

        GetUserValues();
        btn_updateprofile=(Button)findViewById(R.id.btn_updateprofile);
        btn_updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }
    public void updateProfile(){

        fname = et_first_name.getText().toString();
        lname = et_last_name.getText().toString();
        phone = et_phoneno.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        username = et_username.getText().toString();
        typeofvehicle=et_type_of_vehicle.getText().toString();
        vehicleregnum=et_vehicle_reg_num.getText().toString();

        if (TextUtils.isEmpty(fname))
        {
            Toast.makeText(this, "first name should not be empty...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(lname))
        {
            Toast.makeText(this, "last name should not be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, " Email should not be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, " phone number should not be empty...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, " Username should not be empty..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "password should not be empty...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(typeofvehicle))
        {
            Toast.makeText(this, "Vehicle Type should not be empty...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(vehicleregnum))
        {
            Toast.makeText(this, "Vehicle Reg Number should not be empty...", Toast.LENGTH_SHORT).show();
        }
       else {
            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String, Object> hashMap = new HashMap();
                    hashMap.put("Firstname", et_first_name.getText().toString());
                    hashMap.put("Lastname", et_last_name.getText().toString());
                    hashMap.put("Phone", et_phoneno.getText().toString());
                    hashMap.put("Email", et_email.getText().toString());
                    hashMap.put("Username", et_username.getText().toString());
                    hashMap.put("Password", et_password.getText().toString());
                    hashMap.put("Vehiclerenum", et_type_of_vehicle.getText().toString());
                    hashMap.put("Vehicle_type", et_type_of_vehicle.getText().toString());
                    RootRef.child(parentDbName).child(et_username.getText().toString()).updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(DriverEditProfileActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), DriverEditProfileActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(DriverEditProfileActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

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
    private void GetUserValues() {

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DriverPojo usersData = snapshot.child(parentDbName).child(uName).getValue(DriverPojo.class);
                et_first_name.setText(usersData.getFirstname());
                et_last_name.setText(usersData.getLastname());
                et_phoneno.setText(usersData.getPhone());
                et_type_of_vehicle.setText(usersData.getVehicle_type());
                et_vehicle_reg_num.setText(usersData.getVehiclerenum() );
                et_email.setText(usersData.getEmail());
                et_password.setText(usersData.getPassword());
                et_username.setText(usersData.getUsername());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
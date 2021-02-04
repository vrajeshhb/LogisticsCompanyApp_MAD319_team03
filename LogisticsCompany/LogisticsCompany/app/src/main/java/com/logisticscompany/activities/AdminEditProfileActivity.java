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
import com.logisticscompany.models.Users;

import java.util.HashMap;

public class AdminEditProfileActivity extends AppCompatActivity {
    Button btn_updateprofile;
    TextInputEditText et_email,et_username,et_password,et_first_name,et_last_name,et_phoneno;
    ProgressDialog loadingBar;
    DatabaseReference RootRef;
    private String parentDbName = "Admin_Registration";
    String username;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_first_name = (TextInputEditText) findViewById(R.id.et_first_name);
        et_last_name = (TextInputEditText) findViewById(R.id.et_last_name);
        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_phoneno = (TextInputEditText) findViewById(R.id.et_phoneno);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        et_username.setEnabled(false);
        loadingBar = new ProgressDialog(this);

        RootRef = FirebaseDatabase.getInstance().getReference();
        sp = getSharedPreferences("AA", 0);
        username = sp.getString("uname", "-");
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
        String firstname = et_first_name.getText().toString();
        String lastname = et_last_name.getText().toString();
        String phoneno = et_phoneno.getText().toString();
        String eMail = et_email.getText().toString();
        String password = et_password.getText().toString();
        String username = et_username.getText().toString();

        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(this, "Please write your Firstname...", Toast.LENGTH_SHORT).show();
            return;
        }
        else  if (TextUtils.isEmpty(lastname)) {
            Toast.makeText(this, "Please write your Lasttname...", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(eMail)) {
            Toast.makeText(this, "Please write your eMail...", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(phoneno)) {
            Toast.makeText(this, "Please write your Phone no...", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please Choose your Username...", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Firstname", firstname);
                    userdataMap.put("Lastname", lastname);
                    userdataMap.put("Email", eMail);
                    userdataMap.put("Phoneno", phoneno);
                    userdataMap.put("Username", username);
                    userdataMap.put("Password", password);
                    RootRef.child(parentDbName).child(et_username.getText().toString()).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminEditProfileActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), AdminEditProfileActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(AdminEditProfileActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

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
                Users usersData = snapshot.child(parentDbName).child(username).getValue(Users.class);
                et_first_name.setText(usersData.getFirstname());
                et_last_name.setText(usersData.getLastname());
                et_email.setText(usersData.getEmail());
                et_phoneno.setText(usersData.getPhoneno());
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
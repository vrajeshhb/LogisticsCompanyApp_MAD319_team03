package com.logisticscompany.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logisticscompany.R;
import com.logisticscompany.api.ApiService;
import com.logisticscompany.api.RetroClient;
import com.logisticscompany.models.ResponseData;
import com.logisticscompany.models.Users;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputEditText et_username, et_password, etEmail;
    Button btn_submit;
    private String parentDbName = "Admin_Registration";
    DatabaseReference RootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setTitle("Forget Password");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RootRef = FirebaseDatabase.getInstance().getReference();


        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        etEmail = (TextInputEditText) findViewById(R.id.etEmail);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (etEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your new password", Toast.LENGTH_LONG).show();
                    return;
                }
                AllowAccessToAccount(et_username.getText().toString());
                recetPassword(etEmail.getText().toString(),et_password.getText().toString());
            }
        });

    }

    private void AllowAccessToAccount(final String username) {


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(username).exists()) {
                    Users usersData = snapshot.child(parentDbName).child(username).getValue(Users.class);

                    ForgotPassword(usersData.getFirstname(), usersData.getLastname(), usersData.getEmail(), usersData.getPhoneno(),
                            username, et_password.getText().toString());
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "User with this username :" + et_username.getText().toString() + " is not exaist", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    ProgressDialog loadingBar;

    public void ForgotPassword(String fname, String lname, String email, String phno, String uname, String password) {

        loadingBar = new ProgressDialog(ForgotPasswordActivity.this);
        loadingBar.setTitle("Adding Trip Details");
        loadingBar.setMessage("Please wait, while we are Adding your trip Details.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("Firstname", fname);
                userdataMap.put("Lastname", lname);
                userdataMap.put("Email", email);
                userdataMap.put("Phoneno", phno);
                userdataMap.put("Username", uname);
                userdataMap.put("Password", password);

                RootRef.child("Admin_Registration").child(uname).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(ForgotPasswordActivity.this, "Password Updated Succussfully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(ForgotPasswordActivity.this, AdminLoginActivity.class);
                                    ForgotPasswordActivity.this.startActivity(intent);
                                    finish();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(ForgotPasswordActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public  void recetPassword(String email,String pwd)
    {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.forgotPassword(email,pwd);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().message.equals("true")) {
                    Toast.makeText(ForgotPasswordActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
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

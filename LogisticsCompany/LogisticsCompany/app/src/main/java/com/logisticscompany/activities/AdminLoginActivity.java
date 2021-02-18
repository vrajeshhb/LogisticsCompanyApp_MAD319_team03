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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.logisticscompany.R;
import com.logisticscompany.models.Users;

public class    AdminLoginActivity extends AppCompatActivity {
     TextView signUpText;
     Button buttonLogin;
    TextInputEditText et_username,et_password;
    AutoCompleteTextView spinLoinType;
    ProgressDialog loadingBar;
    private String parentDbName = "Admin_Registration";
    TextView tvForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getSupportActionBar().setTitle("Admin Login");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        loadingBar = new ProgressDialog(AdminLoginActivity.this);

        tvForgotPassword=(TextView)findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this,ForgotPasswordActivity.class));
                finish();
            }
        });



        signUpText=(TextView)findViewById(R.id.signUpText);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this, AdminRegistrationActivity.class));
            }
        });

        buttonLogin=(Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AdminLoginActivity.this, AdminDashboardActivity.class));
                LoginUser();

            }
        });
    }

    private void LoginUser() {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please write your Username...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(username, password);
        }
    }

    private void AllowAccessToAccount(final String username, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(username).exists()) {
                    Users usersData = snapshot.child(parentDbName).child(username).getValue(Users.class);
                    if (usersData.getUsername().equals(username)) {
                        if (usersData.getPassword().equals(password)) {
                            Toast.makeText(AdminLoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                            SharedPreferences sp = getSharedPreferences("AA", 0);
                            SharedPreferences.Editor et = sp.edit();
                            et.putString("uname", username);
                            et.commit();
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(AdminLoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AdminLoginActivity.this, "Account with this " + username + " do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

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
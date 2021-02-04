package com.logisticscompany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class AdminRegistrationActivity extends AppCompatActivity {
    TextView loginText;
    Button btn_register;
    TextInputEditText et_email,et_username,et_password,et_last_name,et_first_name,et_phoneno;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_last_name = (TextInputEditText) findViewById(R.id.et_last_name);
        et_first_name = (TextInputEditText) findViewById(R.id.et_first_name);
        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_phoneno = (TextInputEditText) findViewById(R.id.et_phoneno);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        loadingBar = new ProgressDialog(AdminRegistrationActivity.this);

        loginText = (TextView) findViewById(R.id.loginText);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegistrationActivity.this, AdminLoginActivity.class));
            }
        });

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(RegistrationActivity.this, AdminLoginActivity.class));
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {

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
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatepEmail(firstname,lastname,phoneno, eMail, username, password);
        }
    }

    private void ValidatepEmail(final String firstname,final String lastname,final String phoneno, final String eMail, final String username, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Admin_Registration").child(username).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Firstname", firstname);
                    userdataMap.put("Lastname", lastname);
                    userdataMap.put("Email", eMail);
                    userdataMap.put("Phoneno", phoneno);
                    userdataMap.put("Username", username);
                    userdataMap.put("Password", password);


                    RootRef.child("Admin_Registration").child(username).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(AdminRegistrationActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(AdminRegistrationActivity.this, AdminLoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(AdminRegistrationActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(AdminRegistrationActivity.this, "This " + username + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(AdminRegistrationActivity.this, "Please try again using another username.", Toast.LENGTH_SHORT).show();

                }
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
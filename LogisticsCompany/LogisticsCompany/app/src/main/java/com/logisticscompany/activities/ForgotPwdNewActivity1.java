package com.logisticscompany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.logisticscompany.models.Users1;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdNewActivity1 extends AppCompatActivity {
    DatabaseReference RootRef;
    TextInputEditText et_email_id,etSquestion;
    Spinner spSQuestions;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd_new);
        et_email_id=(TextInputEditText)findViewById(R.id.et_email_id);
        etSquestion=(TextInputEditText)findViewById(R.id.etSquestion);
        spSQuestions=(Spinner)findViewById(R.id.spSQuestions);
        btn_submit=(Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowAccessToAccount(et_email_id.getText().toString());
            }
        });
        RootRef = FirebaseDatabase.getInstance().getReference();

    }
    private void AllowAccessToAccount(final String username) {
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Admin_Registration").child(username).exists()) {
                    Users1 usersData = snapshot.child("Admin_Registration").child(username).getValue(Users1.class);
                    //Toast.makeText(ForgotPwdNewActivity.this, "User with this username  : "+usersData.getSecurity_question_type(), Toast.LENGTH_SHORT).show();
                    if(!usersData.getSecurity_question_type().equalsIgnoreCase(spSQuestions.getSelectedItem().toString())){
                        Toast.makeText(ForgotPwdNewActivity1.this, "Invalid Security Question", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!usersData.getSecurity_question_answer().equalsIgnoreCase(etSquestion.getText().toString())){
                        Toast.makeText(ForgotPwdNewActivity1.this, "Invalid Security Question Answer", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    recetPassword(usersData.getEmail(),usersData.getPassword());
                    //Toast.makeText(ForgotPwdNewActivity.this, "Sent password successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPwdNewActivity1.this, "User with this username is not exist", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    ProgressDialog pd;
    public void recetPassword(String email,String pwd)
    {
        pd=new ProgressDialog(ForgotPwdNewActivity1.this);
        pd.setTitle("Please wait,Password is being sent.");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.forgotPassword(email,pwd);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().message.equals("true")) {
                    Toast.makeText(ForgotPwdNewActivity1.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    Intent i=new Intent(ForgotPwdNewActivity1.this,MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(ForgotPwdNewActivity1.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
}
package com.logisticscompany.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.logisticscompany.R;

import java.util.HashMap;

public class DriverRegistrationActivity extends AppCompatActivity {
    TextView loginText;
    Button btn_register,btn_upload_licence;
    TextInputEditText etSquestion,et_email,et_username,et_password,et_last_name,et_first_name,et_phoneno,et_type_of_vehicle,et_vehicle_reg_num;
    ProgressDialog loadingBar;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    String downloadImageUrl;
    ImageView image_view;
    String fname,lname,phone,email,password,username,typeofvehicle,vehicleregnum,sanswer;
    Spinner spSQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        getSupportActionBar().setTitle("Driver Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Drivers");
        loadingBar = new ProgressDialog(DriverRegistrationActivity.this);

        et_last_name = (TextInputEditText) findViewById(R.id.et_last_name);

        et_first_name = (TextInputEditText) findViewById(R.id.et_first_name);

        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_phoneno = (TextInputEditText) findViewById(R.id.et_phoneno);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        et_type_of_vehicle = (TextInputEditText) findViewById(R.id.et_type_of_vehicle);
        et_vehicle_reg_num = (TextInputEditText) findViewById(R.id.et_vehicle_reg_num);
        etSquestion = (TextInputEditText) findViewById(R.id.etSquestion);
        image_view=(ImageView)findViewById(R.id.image_view);
        loadingBar = new ProgressDialog(DriverRegistrationActivity.this);

        loginText = (TextView) findViewById(R.id.loginText);
        spSQuestions = (Spinner) findViewById(R.id.spSQuestions);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverRegistrationActivity.this, DriverLoginActivity.class));
            }
        });

        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
                //startActivity(new Intent(DriverRegistrationActivity.this, DriverLoginActivity.class));
            }
        });

        btn_upload_licence = (Button) findViewById(R.id.btn_upload_licence);
                btn_upload_licence.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenGallery();

                    }
                });

    }
    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            image_view.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData()
    {
        fname = et_first_name.getText().toString();
        lname = et_last_name.getText().toString();
        phone = et_phoneno.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        username = et_username.getText().toString();
        typeofvehicle=et_type_of_vehicle.getText().toString();
        sanswer=etSquestion.getText().toString();
        vehicleregnum=et_vehicle_reg_num.getText().toString();

        if (ImageUri == null)
        {
            Toast.makeText(DriverRegistrationActivity.this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(fname))
        {
            Toast.makeText(this, "Please write your first name...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(lname))
        {
            Toast.makeText(this, "Please write your last name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please Choose your Username...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(typeofvehicle))
        {
            Toast.makeText(this, "Please write Vehicle Type...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(vehicleregnum))
        {
            Toast.makeText(this, "Please write your Vehicle Reg Number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(sanswer))
        {
            Toast.makeText(this, "Please write your Answer...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation()
    {
        loadingBar.setTitle("Create Account");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment()+ ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(DriverRegistrationActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(DriverRegistrationActivity.this, "Licence uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            ValidateDetails();
                        }
                    }
                });
            }
        });
    }
    private void ValidateDetails() {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Driver_Registration").child(username).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("image", downloadImageUrl);
                    userdataMap.put("Firstname", fname);
                    userdataMap.put("Lastname", lname);
                    userdataMap.put("Phone", phone);
                    userdataMap.put("Email", email);
                    userdataMap.put("Username", username);
                    userdataMap.put("Password", password);
                    userdataMap.put("Vehiclerenum", vehicleregnum);
                    userdataMap.put("Vehicle_type", typeofvehicle);
                    userdataMap.put("security_question_type", spSQuestions.getSelectedItem().toString());
                    userdataMap.put("security_question_answer", sanswer);
                    RootRef.child("Driver_Registration").child(username).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(DriverRegistrationActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                      /*Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                        startActivity(intent);*/
                                        finish();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(DriverRegistrationActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(DriverRegistrationActivity.this, "This " + username + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(DriverRegistrationActivity.this, "Please try again using another Email.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DriverRegistrationActivity.this, DriverRegistrationActivity.class);
                    startActivity(intent);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
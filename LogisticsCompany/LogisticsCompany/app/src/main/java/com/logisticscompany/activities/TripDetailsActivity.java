package com.logisticscompany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.logisticscompany.R;

public class TripDetailsActivity extends AppCompatActivity {
    TextView tvTypeOfLoad,tvPickupTime,tvPickUpLocation,tvDeliveryTime,tvDeliveryLocation,tvCostperHour;
    Button btnAccept,btnReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        getSupportActionBar().setTitle("Trip Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTypeOfLoad=(TextView) findViewById(R.id.tvTypeOfLoad);
        tvPickupTime=(TextView)findViewById(R.id.tvPickupTime);
        tvPickUpLocation=(TextView)findViewById(R.id.tvPickUpLocation);
        tvDeliveryTime=(TextView)findViewById(R.id.tvDeliveryTime);
        tvDeliveryLocation=(TextView)findViewById(R.id.tvDeliveryLocation);
        tvCostperHour=(TextView)findViewById(R.id.tvCostperHour);


        tvTypeOfLoad.setText("Type Of Load  :"+getIntent().getStringExtra("typeofload"));
        tvPickupTime.setText("Pick Up Time  :"+getIntent().getStringExtra("pickuptime"));
        tvPickUpLocation.setText("Pick Up Location  :"+getIntent().getStringExtra("pickuplocation"));
        tvDeliveryTime.setText("Delivery Time  :"+getIntent().getStringExtra("deliverytime"));
        tvDeliveryLocation.setText("Delivery Location  :"+getIntent().getStringExtra("deliverylocation"));
        tvCostperHour.setText("Cost Per Hour  :"+getIntent().getStringExtra("costperhour"));

        btnAccept=(Button)findViewById(R.id.btnAccept);
        btnReject=(Button)findViewById(R.id.btnReject);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TripDetailsActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TripDetailsActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
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
package com.logisticscompany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.logisticscompany.R;
import com.logisticscompany.adapters.ViewDriversAdapter;
import com.logisticscompany.adapters.ViewTripsAdapter;
import com.logisticscompany.models.DriversInfoPojo;
import com.logisticscompany.models.ViewTripsPojo;

import java.util.ArrayList;
import java.util.List;

public class ViewDriversInfoActivity extends AppCompatActivity {
    List<DriversInfoPojo> driversInfoPojo;
    ListView list_view;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drivers_info);

        getSupportActionBar().setTitle("Drivers Info");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);

        driversInfoPojo=new ArrayList<>();
        getDriversDetails();

    }

    private void getDriversDetails() {
        driversInfoPojo = new ArrayList<>();
        progressDialog = new ProgressDialog(ViewDriversInfoActivity.this);
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("Driver_Registration");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            progressDialog.dismiss();
            driversInfoPojo.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DriversInfoPojo driverInfo = snapshot.getValue(DriversInfoPojo.class);
                    driversInfoPojo.add(driverInfo);
                }
                if (driversInfoPojo.size() > 0) {
                    list_view.setAdapter(new ViewDriversAdapter( ViewDriversInfoActivity.this,driversInfoPojo));
                }
            } else {
                Toast.makeText(ViewDriversInfoActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            progressDialog.dismiss();

        }
    };
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
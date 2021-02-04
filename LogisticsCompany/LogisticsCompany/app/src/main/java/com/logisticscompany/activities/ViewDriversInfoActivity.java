package com.logisticscompany.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.logisticscompany.R;
import com.logisticscompany.adapters.ViewDriversAdapter;
import com.logisticscompany.models.DriversInfoPojo;

import java.util.ArrayList;
import java.util.List;

public class ViewDriversInfoActivity extends AppCompatActivity {
    List<DriversInfoPojo> driversInfoPojo;
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drivers_info);

        getSupportActionBar().setTitle("Drivers Info");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);

        driversInfoPojo=new ArrayList<>();
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));
        driversInfoPojo.add(new DriversInfoPojo("XYZ","Vijayawada","500 Rs"));

        ViewDriversAdapter viewDriversAdapter=new ViewDriversAdapter(this,driversInfoPojo);
        list_view.setAdapter(viewDriversAdapter);
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
package com.logisticscompany.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.logisticscompany.R;
import com.logisticscompany.activities.AdminEditTripActivity;
import com.logisticscompany.models.DriversInfoPojo;
import com.logisticscompany.models.ViewTripsPojo;

import java.util.List;

public class ViewDriversAdapter extends BaseAdapter {
    List<DriversInfoPojo> driversInfo;
    Context context;

    public ViewDriversAdapter(Context context, List<DriversInfoPojo> driversInfoPojo) {
        this.context=context;
        this.driversInfo=driversInfoPojo;
    }

    @Override
    public int getCount() {
        return driversInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater obj1 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_drivers_info, null);

        TextView tvDriverName=(TextView)obj2.findViewById(R.id.tvDriverName);
        tvDriverName.setText("Driver Name:  "+driversInfo.get(position).getDriverName());

        TextView tvDriverLocation=(TextView)obj2.findViewById(R.id.tvDriverLocation);
        tvDriverLocation.setText("Driver Location:  "+driversInfo.get(position).getDriverLocation());

        TextView tvCostperHour=(TextView)obj2.findViewById(R.id.tvCostperHour);
        tvCostperHour.setText("Cost Per Hour:  "+driversInfo.get(position).getCostPerHour());

        Button btn_book=(Button)obj2.findViewById(R.id.btn_book);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return obj2;
    }
}

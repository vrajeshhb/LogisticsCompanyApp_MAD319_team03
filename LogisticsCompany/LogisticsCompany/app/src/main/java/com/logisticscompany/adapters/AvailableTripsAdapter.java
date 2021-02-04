package com.logisticscompany.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.logisticscompany.R;
import com.logisticscompany.activities.AdminEditTripActivity;
import com.logisticscompany.activities.TripDetailsActivity;
import com.logisticscompany.models.ViewTripsPojo;

import java.util.List;

public class AvailableTripsAdapter extends BaseAdapter {
    List<ViewTripsPojo> viewTripsPojo;
    Context context;

    public AvailableTripsAdapter(Context context, List<ViewTripsPojo> mytrips) {
        this.context=context;
        this.viewTripsPojo=mytrips;
    }

    @Override
    public int getCount() {
        return viewTripsPojo.size();
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
        View obj2 = obj1.inflate(R.layout.adapter_available_trips, null);

        TextView tvTypeOfLoad=(TextView)obj2.findViewById(R.id.tvTypeOfLoad);
        tvTypeOfLoad.setText("Type of Load:  "+viewTripsPojo.get(position).getTypeofload());

        TextView tvPicupTime=(TextView)obj2.findViewById(R.id.tvPicupTime);
        tvPicupTime.setText("Pick Up Time:  "+viewTripsPojo.get(position).getPickuptime());

        TextView tvPicupLocation=(TextView)obj2.findViewById(R.id.tvPicupLocation);
        tvPicupLocation.setText("Pick Up Location:  "+viewTripsPojo.get(position).getPickuplocation());

        TextView tvDeliveryTime=(TextView)obj2.findViewById(R.id.tvDeliveryTime);
        tvDeliveryTime.setText("Delivery Time:  "+viewTripsPojo.get(position).getDeliverytime());

        TextView tvDeliveryLocation=(TextView)obj2.findViewById(R.id.tvDeliveryLocation);
        tvDeliveryLocation.setText("Delivery Location:  "+viewTripsPojo.get(position).getDeliverylocation());

        TextView tvCostperHour=(TextView)obj2.findViewById(R.id.tvCostperHour);
        tvCostperHour.setText("Cost Per Hour:  "+viewTripsPojo.get(position).getCostperhour());

        Button btnReviews=(Button)obj2.findViewById(R.id.btnReviews);
        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnTripDetails=(Button)obj2.findViewById(R.id.btnTripDetails);
        btnTripDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TripDetailsActivity.class);
                intent.putExtra("typeofload",viewTripsPojo.get(position).getTypeofload());
                intent.putExtra("pickuptime",viewTripsPojo.get(position).getPickuptime());
                intent.putExtra("pickuplocation",viewTripsPojo.get(position).getPickuplocation());
                intent.putExtra("deliverytime",viewTripsPojo.get(position).getDeliverytime());
                intent.putExtra("deliverylocation",viewTripsPojo.get(position).getDeliverylocation());
                intent.putExtra("costperhour",viewTripsPojo.get(position).getCostperhour());
                context.startActivity(intent);
            }
        });

        return obj2;
    }
}

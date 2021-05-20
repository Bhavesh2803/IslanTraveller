package com.islantraveller.Dashboard.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.islantraveller.Dashboard.Activity.Model.Locations.LocationsList;
import com.islantraveller.R;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CompleteOrderviewholder> {

    Context context;

    LocationsList locationsList;
    LocationClickListner locationClickListner;

    public WeatherAdapter(Context context, LocationsList locationsList, LocationClickListner locationClickListner1) {
        this.context = context;
        this.locationsList = locationsList;
        this.locationClickListner = locationClickListner1;
    }

    @NonNull
    @Override
    public CompleteOrderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        final CompleteOrderviewholder viewHolder1 = new CompleteOrderviewholder(view1);
        return viewHolder1;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CompleteOrderviewholder holder, final int position) {

        try {
            holder.location_name.setText("" + locationsList.getData().get(position).getLocationName());
            holder.rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationClickListner.onSelectLocation( locationsList.getData().get(position));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {

        return locationsList.getData().size();
    }

    public class CompleteOrderviewholder extends RecyclerView.ViewHolder {
        TextView location_name = null;
        RelativeLayout rootview;

        public CompleteOrderviewholder(View itemView) {
            super(itemView);

            location_name = (TextView) itemView.findViewById(R.id.location_name);
            rootview = (RelativeLayout) itemView.findViewById(R.id.rootview);

        }
    }


}




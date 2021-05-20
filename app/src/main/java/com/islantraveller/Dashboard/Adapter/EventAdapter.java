package com.islantraveller.Dashboard.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.DealInfo.DealInfo;
import com.islantraveller.R;
import com.islantraveller.Utils.CommonFunction;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;
import com.squareup.picasso.Picasso;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CompleteOrderviewholder> {

    Context context;
    DealAllDTO dealAllDTO;

    public EventAdapter(Context context, DealAllDTO dealAllDTO) {
        this.context = context;
        this.dealAllDTO = dealAllDTO;
    }

    @NonNull
    @Override
    public CompleteOrderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        final CompleteOrderviewholder viewHolder1 = new CompleteOrderviewholder(view1);
        return viewHolder1;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CompleteOrderviewholder holder, final int position) {

        try {
            // holder.main_view.setBackgroundResource(dealAllDTO.);
            try {
                if (dealAllDTO.getData().get(position).getImages() != null && dealAllDTO.getData().get(position).getImages().size()>0) {
                    Picasso.with(context).load(dealAllDTO.getData().get(position).getImages().get(0).getDeal_image()).into(holder.iv_image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppSession.save(context, Constants.DEAL_ID, dealAllDTO.getData().get(position).getDealId());
                    context.startActivity(new Intent(context, DealInfo.class));
                }
            });
            holder.tv_name.setText(dealAllDTO.getData().get(position).getDealTitle());
            holder.tv_price.setText(dealAllDTO.getData().get(position).getDealPrice());
         //   holder.tv_short_description.setText(dealAllDTO.getData().get(position).getDealDescription());
            holder.tv_short_description.setText(Html.fromHtml(dealAllDTO.getData().get(position).getDealDescription()));
            holder.tv_distance.setText("2.2 Km");
            showKM(position, holder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showKM(int position, CompleteOrderviewholder holder) {
        try {
            double qr_lat = Double.parseDouble(dealAllDTO.getData().get(position).getDealAddressLat());
            double qr_lang = Double.parseDouble(dealAllDTO.getData().get(position).getDealAddressLong());
            double my_lat = Double.parseDouble(AppSession.getValue(context, Constants.LATTITUDE));
            double my_lang = Double.parseDouble(AppSession.getValue(context, Constants.LONGITUDE));

            LatLng latLng = new LatLng(qr_lat, qr_lang);
            LatLng latLng1 = new LatLng(my_lat, my_lang);
            int distance = (int) Double.parseDouble("" + CommonFunction.distanceBetween(latLng, latLng1));

            try {
                String dist = "";
                if (distance < 1000) {

                    dist = "" + distance + " m";

                } else if (distance > 999) {
                    int dis = distance / 1000;
                    int remi = distance % 1000;
                    int dis1 = remi / 100;
                    dist = "" + dis + "." + dis1 + " km";


                }
                holder.tv_distance.setText(dist);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dealAllDTO.getData().size();
    }

    public class CompleteOrderviewholder extends RecyclerView.ViewHolder {
        CardView main_view = null;
        ImageView iv_image = null;
        TextView tv_name = null;
        TextView tv_price = null;
        TextView tv_distance = null;
        TextView tv_short_description = null;


        public CompleteOrderviewholder(View itemView) {
            super(itemView);

            main_view = (CardView) itemView.findViewById(R.id.main_view);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            tv_short_description = (TextView) itemView.findViewById(R.id.tv_short_description);
        }
    }


}




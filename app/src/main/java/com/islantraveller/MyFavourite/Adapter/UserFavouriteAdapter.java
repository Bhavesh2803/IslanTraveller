package com.islantraveller.MyFavourite.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.DealInfo.DealInfo;
import com.islantraveller.R;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;
import com.squareup.picasso.Picasso;


public class UserFavouriteAdapter extends RecyclerView.Adapter<UserFavouriteAdapter.CompleteOrderviewholder> {

    Context context;
    DealAllDTO dealAllDTO;

    public UserFavouriteAdapter(Context context, DealAllDTO dealAllDTO) {
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
            holder.tv_distance.setVisibility(View.GONE);

           // String image_path =  dealAllDTO.getData().get(position).getDealImage();
          //  Picasso.with(context).load(image_path).into(holder.iv_image);

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
        TextView tv_distance;
        ImageView iv_image;

        public CompleteOrderviewholder(View itemView) {
            super(itemView);

            main_view = (CardView) itemView.findViewById(R.id.main_view);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }


}




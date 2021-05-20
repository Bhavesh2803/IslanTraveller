package com.islantraveller.Dashboard.Adapter;

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
import com.islantraveller.Dashboard.Activity.Model.SavedEvents.SavedDealsDTO;
import com.islantraveller.DealInfo.DealInfo;
import com.islantraveller.R;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;
import com.squareup.picasso.Picasso;


public class SavedEventAdapter extends RecyclerView.Adapter<SavedEventAdapter.CompleteOrderviewholder> {

    Context context;
    DealAllDTO savedDealsDTO;
    public SavedEventAdapter(Context context, DealAllDTO savedDealsDTO) {
        this.context = context;
        this.savedDealsDTO = savedDealsDTO;
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
            Picasso.with(context).load( savedDealsDTO.getData().get(position).getDealImage()).into(holder.iv_image);
            holder.main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppSession.save(context, Constants.DEAL_ID,savedDealsDTO.getData().get(position).getDealId());
                    context.startActivity(new Intent(context, DealInfo.class));
                }
            });
            holder.tv_name.setText(savedDealsDTO.getData().get(position).getDealTitle());
            holder.tv_price.setText(savedDealsDTO.getData().get(position).getDealPrice());
            holder.tv_short_description.setText(savedDealsDTO.getData().get(position).getDealDescription());
            holder.tv_distance.setText("2.2 Km");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return savedDealsDTO.getData().size();
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




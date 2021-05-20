package com.islantraveller.DealInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.DealInfo.Model.DealInfoModel.DealInfoDTO;
import com.islantraveller.R;
import com.islantraveller.database.Constants;
import com.squareup.picasso.Picasso;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.CompleteOrderviewholder> {

    Context context;
    DealInfoDTO dealAllDTO;
    public ImageAdapter(Context context, DealInfoDTO dealAllDTO) {
        this.context = context;
        this.dealAllDTO = dealAllDTO;
    }

    @NonNull
    @Override
    public CompleteOrderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_info_img_item, parent, false);
        final CompleteOrderviewholder viewHolder1 = new CompleteOrderviewholder(view1);
        return viewHolder1;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CompleteOrderviewholder holder, final int position) {

        try {
            Picasso.with(context).load( dealAllDTO.getData().getImages().get(position).getDeal_image()).into(holder.iv_image);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {

        return dealAllDTO.getData().getImages().size();
    }

    public class CompleteOrderviewholder extends RecyclerView.ViewHolder {
        ImageView iv_image = null;
        ImageView circle_img_cilcked = null;
        ImageView circle_img = null;
        LinearLayout main_view;

        public CompleteOrderviewholder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
          /*  iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            circle_img = (ImageView) itemView.findViewById(R.id.circle_img);
            cat_text = (TextView) itemView.findViewById(R.id.cat_text);
            main_view = (LinearLayout) itemView.findViewById(R.id.main_view);*/

        }
    }


}




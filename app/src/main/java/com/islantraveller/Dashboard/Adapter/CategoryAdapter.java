package com.islantraveller.Dashboard.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.R;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CompleteOrderviewholder> {

    Context context;
int selected_index = -10;
    String[] text_array = {"MEDICAL CENTER", "RESTAURANTS", "NIGHTCLUBS", "COCKTAILS", "WATER SPORTS"};
    String[] tint_color = {"#30fd38", "#44e5e5", "#ef0c19", "#fed330", "#67aef3"};

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CompleteOrderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        final CompleteOrderviewholder viewHolder1 = new CompleteOrderviewholder(view1);
        return viewHolder1;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CompleteOrderviewholder holder, final int position) {

        try {
            holder.circle_img.setColorFilter(Color.parseColor(tint_color[position]));
            holder.circle_img_cilcked.setColorFilter(Color.parseColor(tint_color[position]));


            holder.circle_img.setVisibility(View.VISIBLE);
            holder.circle_img_cilcked.setVisibility(View.GONE);

            if(selected_index == position)
            {
                holder.circle_img.setVisibility(View.GONE);
                holder.circle_img_cilcked.setVisibility(View.VISIBLE);
                holder.cat_text.setTextSize(13f);
            }else
            {
                holder.circle_img.setVisibility(View.VISIBLE);
                holder.circle_img_cilcked.setVisibility(View.GONE);
                holder.cat_text.setTextSize(11f);
            }

            holder.main_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                try
                {
                    selected_index = position;
                    ((MainActivity)context).showRestaurents();
                    notifyDataSetChanged();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                }
            });
            holder.cat_text.setText(text_array[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {

        return text_array.length;
    }

    public class CompleteOrderviewholder extends RecyclerView.ViewHolder {
        TextView cat_text = null;
        ImageView circle_img_cilcked = null;
        ImageView circle_img = null;
LinearLayout main_view;
        public CompleteOrderviewholder(View itemView) {
            super(itemView);

            circle_img_cilcked = (ImageView) itemView.findViewById(R.id.circle_img_cilcked);
            circle_img = (ImageView) itemView.findViewById(R.id.circle_img);
            cat_text = (TextView) itemView.findViewById(R.id.cat_text);
            main_view = (LinearLayout) itemView.findViewById(R.id.main_view);

        }
    }


}




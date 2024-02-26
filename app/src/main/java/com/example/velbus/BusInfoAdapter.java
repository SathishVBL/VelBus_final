package com.example.velbus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class BusInfoAdapter extends RecyclerView.Adapter<BusInfoAdapter.MyViewHolder> {
    private final Context context;
    static List<String> route_name=new ArrayList<>();

    public BusInfoAdapter(Context context,List<String> route_name) {
        BusInfoAdapter.route_name =route_name;
        this.context=context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.bus_route_info);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle item click here
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String selectedItem = route_name.get(position);

                    // Create an Intent to start the DetailsActivity
                    Intent navToBusTime = new Intent(itemView.getContext(), bus_time_info.class);
                    navToBusTime.putExtra("route_name",selectedItem);


                    itemView.getContext().startActivity(navToBusTime);
                    }
                }
            });

            LinearLayout linearLayout = itemView.findViewById(R.id.onclickviewroute);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle LinearLayout click here
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String selectedItem = route_name.get(position);

                        // Create an Intent to start the desired activity
                        Intent intent = new Intent(itemView.getContext(), see_direction_mapview.class);
                        intent.putExtra("particular_route", selectedItem);

                        // Start the new activity
                        itemView.getContext().startActivity(intent);
                    }
                }
            });


        }
    }

    @NonNull
    @Override
    public BusInfoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.route_of_bus,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BusInfoAdapter.MyViewHolder holder, int position) {
        if(position%2==0){
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.first_item));
        }
        else {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.section_item));
        }
        holder.name.setText(route_name.get(position));


    }

    @Override
    public int getItemCount() {
        return route_name.size();
    }
}

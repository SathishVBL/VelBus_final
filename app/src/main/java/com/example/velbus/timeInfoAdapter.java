package com.example.velbus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class timeInfoAdapter extends RecyclerView.Adapter<timeInfoAdapter.timeViewHolder> {
    Context context;
    List<String> times=new ArrayList<>();
    timeInfoAdapter(Context context,List<String> times){
        this.context=context;
        this.times=times;
    }

    @NonNull
    @Override
    public timeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.time_card_view,parent,false);
        return new timeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull timeViewHolder holder, int position) {
        if(position%2==0){
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.first_item));
        }
        else {
            holder.itemView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.section_item));
        }
        holder.times.setText(times.get(position));

    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class timeViewHolder extends RecyclerView.ViewHolder{
        public  TextView times;
        public timeViewHolder(@NonNull View itemView) {
            super(itemView);
            times=(TextView) itemView.findViewById(R.id.time_for_bus);

        }
    }
}

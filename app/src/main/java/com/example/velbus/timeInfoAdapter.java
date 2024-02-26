package com.example.velbus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class timeInfoAdapter extends RecyclerView.Adapter<timeInfoAdapter.timeViewHolder> {
    Context context;
//    List<String> times;
    List<String> actualTime=new ArrayList<>();
    timeInfoAdapter(Context context,List<String> times){
        this.context=context;
//        this.times=times;
        twelveTo24(times);

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
        holder.times.setText("DEPARTURE AT "+actualTime.get(position));

    }

    @Override
    public int getItemCount() {
        return actualTime.size();
    }

    public static class timeViewHolder extends RecyclerView.ViewHolder{
        public  TextView times;
        public timeViewHolder(@NonNull View itemView) {
            super(itemView);
            times=(TextView) itemView.findViewById(R.id.time_for_bus);

        }
    }


    public void twelveTo24(List<String> t){
        String tempTime;

        DateTimeFormatter formatter24hr;
        DateTimeFormatter formatter12hr;
        LocalTime currentTime;
        LocalDateTime curentDateTime;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter24hr = DateTimeFormatter.ofPattern("H:mm:ss");
            formatter12hr = DateTimeFormatter.ofPattern("h:mm a");
            curentDateTime=LocalDateTime.now();
            currentTime=curentDateTime.toLocalTime();


            for(String tt:t){
                LocalTime specificTime=LocalTime.parse(tt,formatter24hr);

                if(specificTime.isAfter(currentTime)){
                    tempTime=specificTime.format(formatter12hr);
                    actualTime.add(tempTime);
                }

            }

        }
    }
}

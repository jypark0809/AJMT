package com.example.ajourestaurant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ajourestaurant.CafeInfoActivity;
import com.example.ajourestaurant.Database.Restaurant;
import com.example.ajourestaurant.R;
import com.example.ajourestaurant.StoreInfoActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.ViewHolder> {
    private ArrayList<Restaurant> cafeList;
    private Context context;
    private boolean isOpen;

    Calendar cal = Calendar.getInstance();
    int curHour = cal.get(Calendar.HOUR_OF_DAY);
    int curMinute = cal.get(Calendar.MINUTE);
    int curSecond = cal.get(Calendar.SECOND);
    int curTime = (curHour*60*60) + (curMinute*60) + curSecond;

    public CafeAdapter(ArrayList<Restaurant> postList, Context context) {
        this.cafeList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public CafeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CafeAdapter.ViewHolder holder, int position) {

        // Recycler View 클릭 이벤트 처리
        final String cafeKey = cafeList.get(position).getName();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CafeInfoActivity.class);
                intent.putExtra(CafeInfoActivity.EXTRA_RESTAURANT_KEY, cafeKey);
                context.startActivity(intent);
            }
        });


        Glide.with(holder.itemView)
                .load(cafeList.get(position).getUrl())
                .centerCrop()
                .into(holder.iv_restaurant);

        String open_time = cafeList.get(position).getOpen(); // open_time을 데이터베이스에서 받아옴
        String[] splitOpenHourAndMin = open_time.split(":"); // ":"를 기준으로 Hour과 Minitue을 나눔
        String close_time = cafeList.get(position).getClose(); // close_time을 데이터베이스에서 받아옴
        String[] splitCloseHourAndMin = close_time.split(":"); //":"를 기준으로 Hour과 Minitue을 나눔
        //Log.e("시간",cafeList.get(position).getName() + " " + open_time + " " + close_time);
        int openTime = (Integer.parseInt(splitOpenHourAndMin[0])*60*60) + Integer.parseInt(splitOpenHourAndMin[1])*60;
        int closeTime = (Integer.parseInt(splitCloseHourAndMin[0])*60*60) + Integer.parseInt(splitCloseHourAndMin[1])*60;
        if(closeTime>86400) { // 마감시간이 24:00보다 늦게 끝난다면
            if(closeTime-86400 <= curTime && curTime <= openTime) {
                isOpen = false;
            } else {
                isOpen = true;
            }
        } else {
            if(openTime <= curTime && curTime <= closeTime) {
                isOpen = true;
            } else {
                isOpen = false;
            }
        }
        if(isOpen == true) {
            holder.iv_isOpen.setImageResource(R.drawable.img_open);
            holder.tv_open_time.setTextColor(0xAA00DA6F);
            holder.tv_close_time.setTextColor(0xAA00DA6F);
            holder.tv_tilde.setTextColor(0xAA00DA6F);
        } else {
            holder.iv_isOpen.setImageResource(R.drawable.img_close);
            holder.tv_open_time.setTextColor(0xFF979797);
            holder.tv_close_time.setTextColor(0xFF979797);
            holder.tv_tilde.setTextColor(0xFF979797);
        }
        holder.tv_name.setText(cafeList.get(position).getName());
        holder.tv_type.setText(cafeList.get(position).getType());
        holder.tv_menu.setText(cafeList.get(position).getMenu());

        DecimalFormat formatter = new DecimalFormat("###,###");
        int decimalForamt_price = cafeList.get(position).getPrice();
        holder.tv_price.setText(String.valueOf(formatter.format(decimalForamt_price))+"원");
        holder.tv_rating.setText(String.valueOf(cafeList.get(position).getRating()));
        holder.tv_open_time.setText(cafeList.get(position).getOpen());
        holder.tv_close_time.setText(cafeList.get(position).getClose());
    }

    @Override
    public int getItemCount() { return (cafeList != null ? cafeList.size() : 0 ); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_restaurant, iv_isOpen;
        TextView tv_name, tv_type, tv_menu, tv_price, tv_open_time, tv_close_time, tv_tilde, tv_rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_restaurant = itemView.findViewById(R.id.iv_restaurant);
            iv_isOpen = itemView.findViewById(R.id.iv_isOpen);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_menu = itemView.findViewById(R.id.tv_menu);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_open_time = itemView.findViewById(R.id.tv_open_time);
            tv_close_time = itemView.findViewById(R.id.tv_close_time);
            tv_tilde = itemView.findViewById(R.id.tv_tilde);
            tv_rating = itemView.findViewById(R.id.tv_rating);


        }
    }
}

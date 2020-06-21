package com.example.ajourestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajourestaurant.Database.Menu;
import com.example.ajourestaurant.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ArrayList<Menu> menusList;
    private Context context;

    public MenuAdapter(ArrayList<Menu> menusList, Context context) {
        this.menusList = menusList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(menusList.get(position).getName());

        DecimalFormat formatter = new DecimalFormat("###,###");
        int decimalForamt_price = menusList.get(position).getPrice();
        holder.tv_price.setText(String.valueOf(formatter.format(decimalForamt_price))+"원");


    }

    @Override
    public int getItemCount() {
        return (menusList != null ? menusList.size() : 0 );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_menu_name);
            tv_price = itemView.findViewById(R.id.tv_menu_price);

        }
    }
}

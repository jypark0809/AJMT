package com.example.ajourestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ajourestaurant.Database.ListViewItem;
import com.example.ajourestaurant.R;

import java.util.List;

public class MyInfoAdapter extends ArrayAdapter<ListViewItem> {
    LayoutInflater layoutInflater;

    public MyInfoAdapter(Context context, int textViewResourceId, List<ListViewItem> object) {
        super(context, textViewResourceId, object);
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListViewItem listViewItem = (ListViewItem)getItem(position);

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_my_info,null);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView_my_info);
        imageView.setImageDrawable(listViewItem.getIcon());

        TextView textView = (TextView)convertView.findViewById(R.id.textView_my_info);
        textView.setText(listViewItem.getTitle());

        return convertView;
    }
}

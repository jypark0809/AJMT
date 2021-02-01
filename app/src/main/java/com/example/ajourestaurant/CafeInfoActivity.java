package com.example.ajourestaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajourestaurant.Database.Menu;
import com.example.ajourestaurant.Database.Restaurant;
import com.example.ajourestaurant.adapter.MenuAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CafeInfoActivity extends AppCompatActivity {

    private RatingBar ratingbar;
    private String cafeKey;
    public static final String EXTRA_RESTAURANT_KEY = "cafeKey";

    private TextView tv_cafe_name1;
    private TextView tv_cafe_name2;
    private TextView tv_open_time;
    private TextView tv_close_time;
    private TextView tv_rating;
    private TextView tv_tel_number;
    private TextView tv_address;
    private ImageView iv_back;

    private RecyclerView recyclerView_menu;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Menu> menuList;

    private DatabaseReference mRestaurantReference;
    private DatabaseReference mMenuReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_info);

        cafeKey = getIntent().getStringExtra(EXTRA_RESTAURANT_KEY);

        mRestaurantReference = FirebaseDatabase.getInstance().getReference().child("Cafes").child(cafeKey);

        // Initialize Views
        tv_cafe_name1 = (TextView)findViewById(R.id.tv_cafe_name1);
        tv_cafe_name2 = (TextView)findViewById(R.id.tv_cafe_name2);
        tv_rating = (TextView)findViewById(R.id.tv_rating_cafe);
        tv_open_time = (TextView)findViewById(R.id.tv_open_time_cafe);
        tv_close_time = (TextView)findViewById(R.id.tv_close_time_cafe);
        tv_tel_number = (TextView)findViewById(R.id.tv_tel_number_cafe);
        tv_address = (TextView)findViewById(R.id.tv_address_cafe);
        iv_back = (ImageView)findViewById(R.id.iv_back3);


        // 뒤로가기
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView_menu = (RecyclerView)findViewById(R.id.recyclerView_menu_cafe);
        recyclerView_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_menu.setLayoutManager(layoutManager);
        menuList = new ArrayList<>();

        mMenuReference = FirebaseDatabase.getInstance().getReference().child("Menus").child(cafeKey);
        mMenuReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Menu menu = snapshot.getValue(Menu.class);
                    menuList.add(menu);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });

        adapter = new MenuAdapter(menuList, this);
        recyclerView_menu.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener restaurantListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);

                tv_cafe_name1.setText(restaurant.getName());
                tv_cafe_name2.setText(restaurant.getName());
                tv_rating.setText(String.valueOf(restaurant.getRating()));
                tv_open_time.setText(restaurant.getOpen());
                tv_close_time.setText(restaurant.getClose());
                tv_tel_number.setText(restaurant.getTel());
                tv_address.setText(restaurant.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ValueEventListener menuListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRestaurantReference.addValueEventListener(restaurantListener);
    }

}

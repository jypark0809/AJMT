package com.example.ajourestaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajourestaurant.Database.Restaurant;
import com.example.ajourestaurant.adapter.SearchAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    public static final String EXTRA_SEARCH_KEY = "search_key";

    private String mSearchKey;
    private ImageView btn_clear;
    private ImageView btn_back;
    public EditText et_search_result;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Restaurant> restaurantsList;
    private DatabaseReference mPostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // SearchActivity에서 검색한 값을 받아옴
        mSearchKey = getIntent().getStringExtra(EXTRA_SEARCH_KEY);

        et_search_result = (EditText) findViewById(R.id.et_search_result);
        et_search_result.setText(mSearchKey);
        et_search_result.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                    intent.putExtra(SearchResultActivity.EXTRA_SEARCH_KEY,et_search_result.getText().toString());
                    startActivity(intent);
                    hideKeyboard();

                    return true;
                }
                return false;
            }
        });

        // 뒤로가기 버튼
        btn_back = (ImageView)findViewById(R.id.btn_back4);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_clear = (ImageView)findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search_result.setText(null);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_search);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        restaurantsList = new ArrayList<>();

        mPostReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantsList.clear(); //초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Restaurant restaurant = snapshot.getValue(Restaurant.class);
                    if(isContain(restaurant)) {
                        restaurantsList.add(restaurant);
                    } else {

                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        adapter = new SearchAdapter(restaurantsList, this);
        recyclerView.setAdapter(adapter);
    }

    //키보드 숨기기
    private void hideKeyboard() {
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private boolean isContain(Restaurant restaurant) {
        return restaurant.getName().contains(mSearchKey) || restaurant.getMenu().contains(mSearchKey) || restaurant.getType().contains(mSearchKey)
                || restaurant.filter.contains(mSearchKey);
    }
}
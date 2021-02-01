package com.example.ajourestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ajourestaurant.Fragment.Fragment_Cafe;
import com.example.ajourestaurant.Fragment.Fragment_Pub;
import com.example.ajourestaurant.Fragment.Fragment_Restaurant;

public class FilteringActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment_Restaurant fragment_restaurant;
    private Fragment_Cafe fragment_cafe;
    private Fragment_Pub fragment_pub;
    private FragmentTransaction transaction;

    private ImageView iv_filtering_category;
    private ImageView iv_close;
    private ImageView iv_reset;
    private ImageView iv_filtering;
    private int filteringType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering);

        //초기화
        iv_close = (ImageView)findViewById(R.id.iv_back);
        iv_reset = (ImageView)findViewById(R.id.iv_reset);
        iv_filtering = (ImageView)findViewById(R.id.iv_filtering) ;
        iv_filtering_category = (ImageView)findViewById(R.id.iv_filtering_category);
        filteringType = getIntent().getIntExtra("filteringType",filteringType);

        // 뒤로 가기
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 초기화 버튼
        iv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilter();
            }
        });

        iv_filtering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("btn1", fragment_restaurant.btn1.isChecked());
                intent.putExtra("btn2", fragment_restaurant.btn1.isChecked());
                intent.putExtra("btn2", fragment_restaurant.btn2.isChecked());
                intent.putExtra("btn3", fragment_restaurant.btn3.isChecked());
                intent.putExtra("btn4", fragment_restaurant.btn4.isChecked());
                intent.putExtra("btn5", fragment_restaurant.btn5.isChecked());
                intent.putExtra("btn6", fragment_restaurant.btn6.isChecked());
                intent.putExtra("btn7", fragment_restaurant.btn7.isChecked());
                intent.putExtra("btn8", fragment_restaurant.btn8.isChecked());
                intent.putExtra("btn9", fragment_restaurant.btn9.isChecked());
                intent.putExtra("btn10", fragment_restaurant.btn10.isChecked());
                intent.putExtra("btn11", fragment_restaurant.btn11.isChecked());
                intent.putExtra("btn12", fragment_restaurant.btn12.isChecked());
                intent.putExtra("filteringType",filteringType);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();

        fragment_restaurant = new Fragment_Restaurant();
        fragment_cafe = new Fragment_Cafe();
        fragment_pub = new Fragment_Pub();

        transaction = fragmentManager.beginTransaction();
        switch (filteringType) {
            case 0:
                transaction.replace(R.id.frameLayout_filtering, fragment_restaurant).commitAllowingStateLoss();
                iv_filtering_category.setImageResource(R.drawable.test1);
                break;
            case 1:
                transaction.replace(R.id.frameLayout_filtering, fragment_cafe).commitAllowingStateLoss();
                iv_filtering_category.setImageResource(R.drawable.test2);
                break;
            case 2:
                transaction.replace(R.id.frameLayout_filtering, fragment_pub).commitAllowingStateLoss();
                iv_filtering_category.setImageResource(R.drawable.test3);
                break;
        }



    }

    // 필터링 Fragment 전환
    public void clickHandler(View view) {
        transaction = fragmentManager.beginTransaction();

        switch(view.getId())
        {
            case R.id.btn_restaurant:
                transaction.replace(R.id.frameLayout_filtering, fragment_restaurant).commitAllowingStateLoss();
                iv_filtering_category.setImageResource(R.drawable.test1);
                filteringType = 0;
                break;
            case R.id.btn_cafe:
                transaction.replace(R.id.frameLayout_filtering, fragment_cafe).commitAllowingStateLoss();
                iv_filtering_category.setImageResource(R.drawable.test2);
                filteringType = 1;
                break;
            case R.id.btn_pub:
                transaction.replace(R.id.frameLayout_filtering, fragment_pub).commitAllowingStateLoss();
                iv_filtering_category.setImageResource(R.drawable.test3);
                filteringType = 2;
                break;
        }
    }

    private void resetFilter() {
        fragment_restaurant.btn1.setChecked(false);
        fragment_restaurant.btn2.setChecked(false);
        fragment_restaurant.btn3.setChecked(false);
        fragment_restaurant.btn4.setChecked(false);
        fragment_restaurant.btn5.setChecked(false);
        fragment_restaurant.btn6.setChecked(false);
        fragment_restaurant.btn7.setChecked(false);
        fragment_restaurant.btn8.setChecked(false);
        fragment_restaurant.btn9.setChecked(false);
        fragment_restaurant.btn10.setChecked(false);
        fragment_restaurant.btn11.setChecked(false);
        fragment_restaurant.btn12.setChecked(false);
    }

    /*private boolean isNothingChecked() {
        return !fragment_restaurant.btn1.isChecked() && !fragment_restaurant.btn2.isChecked() &&
                !fragment_restaurant.btn3.isChecked() && !fragment_restaurant.btn4.isChecked() &&
                !fragment_restaurant.btn5.isChecked() && !fragment_restaurant.btn6.isChecked() &&
                !fragment_restaurant.btn7.isChecked() && !fragment_restaurant.btn8.isChecked() &&
                !fragment_restaurant.btn9.isChecked() && !fragment_restaurant.btn10.isChecked() &&
                !fragment_restaurant.btn11.isChecked() && !fragment_restaurant.btn12.isChecked();
    }*/
}

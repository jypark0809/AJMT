package com.example.ajourestaurant.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ajourestaurant.FilteringActivity;
import com.example.ajourestaurant.R;

public class Fragment_Restaurant extends Fragment {

    public ToggleButton btn1; //전체
    public ToggleButton btn2; //한식
    public ToggleButton btn3; //분식
    public ToggleButton btn4; //일식
    public ToggleButton btn5; //중국집
    public ToggleButton btn6; //양식
    public ToggleButton btn7; //치킨
    public ToggleButton btn8; //피자
    public ToggleButton btn9; //야식
    public ToggleButton btn10; //찜,탕
    public ToggleButton btn11; //패스트푸드
    public ToggleButton btn12; //세계음식


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        //초기화
        btn1 = (ToggleButton)view.findViewById(R.id.toggle_btn1);
        btn2 = (ToggleButton)view.findViewById(R.id.toggle_btn2);
        btn3 = (ToggleButton)view.findViewById(R.id.toggle_btn3);
        btn4 = (ToggleButton)view.findViewById(R.id.toggle_btn4);
        btn5 = (ToggleButton)view.findViewById(R.id.toggle_btn5);
        btn6 = (ToggleButton)view.findViewById(R.id.toggle_btn6);
        btn7 = (ToggleButton)view.findViewById(R.id.toggle_btn7);
        btn8 = (ToggleButton)view.findViewById(R.id.toggle_btn8);
        btn9 = (ToggleButton)view.findViewById(R.id.toggle_btn9);
        btn10 = (ToggleButton)view.findViewById(R.id.toggle_btn10);
        btn11 = (ToggleButton)view.findViewById(R.id.toggle_btn11);
        btn12 = (ToggleButton)view.findViewById(R.id.toggle_btn12);

        btn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btn1.setChecked(false);
            }
        });


        return view;
    }
}

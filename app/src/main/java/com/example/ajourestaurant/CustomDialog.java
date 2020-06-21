package com.example.ajourestaurant;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class CustomDialog extends Dialog {

    public TimePicker timePicker;
    public Button btn_defalt;
    public Button btn_apply;
    public RadioButton rb_monday, rb_tuesday, rb_wednesday, rb_thursday, rb_friday, rb_saterday, rb_sunday;

    public CustomDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.custom_dialog);     //다이얼로그에서 사용할 레이아웃입니다.

        btn_defalt = (Button)findViewById(R.id.btn_default);
        btn_apply = (Button)findViewById(R.id.btn_apply);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        rb_monday = (RadioButton)findViewById(R.id.rb_monday);
        rb_tuesday = (RadioButton)findViewById(R.id.rb_tuesday);
        rb_wednesday = (RadioButton)findViewById(R.id.rb_wednesday);
        rb_thursday = (RadioButton)findViewById(R.id.rb_thursday);
        rb_friday = (RadioButton)findViewById(R.id.rb_friday);
        rb_saterday = (RadioButton)findViewById(R.id.rb_saturday);
        rb_sunday = (RadioButton)findViewById(R.id.rb_sunday);

        Calendar cal = Calendar.getInstance();
        int curHour = cal.get(Calendar.HOUR_OF_DAY);
        int curMinute = cal.get(Calendar.MINUTE);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        Log.e("요일",String.valueOf(dayOfWeek));
        timePicker.setHour(curHour);
        timePicker.setMinute(curMinute);
        switch (dayOfWeek) {
            case 1:
                rb_sunday.setChecked(true);
                break;
            case 2:
                rb_monday.setChecked(true);
                break;
            case 3:
                rb_tuesday.setChecked(true);
                break;
            case 4:
                rb_wednesday.setChecked(true);
                break;
            case 5:
                rb_thursday.setChecked(true);
                break;
            case 6:
                rb_friday.setChecked(true);
                break;
            case 7:
                rb_saterday.setChecked(true);
                break;
        }
    }

    /*
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.btn_default) {
            dismiss();
        } else if(id==R.id.btn_apply) {
            dismiss();
        }
    }
     */
}

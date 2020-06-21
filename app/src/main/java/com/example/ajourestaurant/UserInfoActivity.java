package com.example.ajourestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ajourestaurant.Database.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        ImageView iv_save_info = (ImageView)findViewById(R.id.iv_save_info);
        iv_save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoSave();
            }
        });



    }

    private void userInfoSave() {
        String nickname = ((EditText)findViewById(R.id.et_nickName)).getText().toString();

        //Spinner_Major
        Spinner spinner_major = (Spinner)findViewById(R.id.spinner_major);
        String major = (String)spinner_major.getSelectedItem();
        spinner_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinner_studentID = (Spinner)findViewById(R.id.spinner_studentID);
        String studentID = (String)spinner_studentID.getSelectedItem();
        spinner_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        /*
        Radio Button 구현
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup_sex);
        RadioButton selectedBtn = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        String sex = selectedBtn.getText().toString();
         */

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        writeNewUser(user.getUid(), nickname, major, studentID);

        // Go to MainActivity
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void writeNewUser(String userId, String nickname, String major, String studentID) {
        User user = new User(nickname, major, studentID);
        mDatabase.child("users").child(userId).setValue(user);
    }
}

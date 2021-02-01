package com.example.ajourestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ajourestaurant.Database.ListViewItem;
import com.example.ajourestaurant.adapter.MyInfoAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyInfoActivity extends AppCompatActivity {

    private DatabaseReference mUserReference;
    private ListViewItem[] INDEX_DATA;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        // 뒤로가기
        mBack = (ImageView)findViewById(R.id.iv_back2);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 회원 정보 가져오기
        mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        INDEX_DATA = new ListViewItem[] {
            new ListViewItem("계정 관리", ContextCompat.getDrawable(this,R.drawable.ic_baseline_person_30)),
            new ListViewItem("찜 목록", ContextCompat.getDrawable(this,R.drawable.ic_baseline_grade_24)),
            new ListViewItem("로그아웃", ContextCompat.getDrawable(this,R.drawable.ic_baseline_reply_30))
        };
        List<ListViewItem> list = new ArrayList<ListViewItem>();
        for(int i=0; i< INDEX_DATA.length; i++) {
            list.add(INDEX_DATA[i]);
        }

        MyInfoAdapter adapter = new MyInfoAdapter(this,0,list);
        ListView listView = (ListView)findViewById(R.id.listview_MyInfo);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),"It's not implemented yet.", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        signOut();
                        break;
                }
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
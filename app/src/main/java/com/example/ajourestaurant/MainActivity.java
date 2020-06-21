package com.example.ajourestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajourestaurant.Database.Restaurant;
import com.example.ajourestaurant.Database.User;
import com.example.ajourestaurant.adapter.ListViewAdapter;
import com.example.ajourestaurant.adapter.RestaurantAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private FirebaseAuth mAuth; // Declare Firebase Auth
    private ImageView mDrawerProfile;
    private boolean btn1_key, btn2_key, btn3_key, btn4_key, btn5_key, btn6_key, btn7_key, btn8_key, btn9_key, btn10_key, btn11_key, btn12_key;
    private DatabaseReference mRestaurantReference;
    private DatabaseReference mUserReference;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private MapView mMapView;
    private GPSTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Restaurant> restaurantsList;

    private Spinner mSpinner;
    private CustomDialog customDialog;
    private ImageView btn_dialog;
    private ImageView mSearch;
    private TextView mDrawerNickname;
    private TextView mDrawerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn2_key = getIntent().getBooleanExtra("btn2", true);
        btn3_key = getIntent().getBooleanExtra("btn3", true);
        btn4_key = getIntent().getBooleanExtra("btn4", true);
        btn5_key = getIntent().getBooleanExtra("btn5", true);
        btn6_key = getIntent().getBooleanExtra("btn6", true);
        btn7_key = getIntent().getBooleanExtra("btn7", true);
        btn8_key = getIntent().getBooleanExtra("btn8", true);
        btn9_key = getIntent().getBooleanExtra("btn9", true);
        btn10_key = getIntent().getBooleanExtra("btn10", true);
        btn11_key = getIntent().getBooleanExtra("btn11", true);
        btn12_key = getIntent().getBooleanExtra("btn12", true);

        gpsTracker = new GPSTracker(MainActivity.this);
        //timePicker = (TimePicker)findViewById(R.id.timePicker);
        mDrawerNickname = (TextView)findViewById(R.id.mDrawerNickname);
        mDrawerEmail = (TextView)findViewById(R.id.mDrawerEmail);
        mSearch = (ImageView)findViewById(R.id.mSearch);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
        ImageView btn_open_drawer = (ImageView) findViewById(R.id.btn_open_drawer);
        btn_open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });
        mDrawerProfile = (ImageView)findViewById(R.id.mDrawerProfile);
        mDrawerProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mDrawerProfile.setClipToOutline(true);


        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        //Drawer Layout 내부 이벤트 구현
        ListView listview ;
        ListViewAdapter drawerAdapter;
        // Adapter 생성
        drawerAdapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView_drawer);
        listview.setAdapter(drawerAdapter);
        drawerAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_circle_20),"회원 정보");
        drawerAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_help_outline_20),"도움말");
        drawerAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_logout_black_20dp),"로그아웃");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        return;
                    case 1:
                        return;
                    case 2:
                        signOut();
                        break;
                }
            }
        });

        mSpinner = (Spinner)findViewById(R.id.spinner_filtering);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.279712,127.043695), true);
                        restaurantsList = selectionSortByDisance(restaurantsList);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                        restaurantsList = selectionSortByMyPos(restaurantsList);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        restaurantsList = selectionSortByRating(restaurantsList);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        long now = System.currentTimeMillis();
        Date mdate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("aa hh:mm");
        String getTime = simpleDate.format(mdate);
        TextView tv_now = (TextView)findViewById(R.id.tv_now);
        tv_now.setText(getTime);


        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        btn_dialog = (ImageView)findViewById(R.id.iv_dialog);
        customDialog = new CustomDialog(this);
        customDialog.btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //timeFiltering(restaurantsList);
                //adapter.notifyDataSetChanged();
                customDialog.dismiss();
            }
        });
        customDialog.btn_defalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        WindowManager.LayoutParams wm = customDialog.getWindow().getAttributes();
        wm.copyFrom(customDialog.getWindow().getAttributes());
        wm.width = (int)(width / (1.2));
        wm.height = (int)(height / (1.78));
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.show();
            }
        });

        mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mRestaurantReference = FirebaseDatabase.getInstance().getReference().child("Restaurants");

        //다음이 제공하는 MapView객체 생성 및 API Key 설정
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setCurrentLocationEventListener(this);
        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.279712,127.043695), true);
        mMapView.setZoomLevel(0, true);


        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        restaurantsList = new ArrayList<>();

        final String[] filterCategory = {"한식", "분식", "일식", "중국집", "양식", "치킨", "피자", "야식", "찜/탕", "패스트푸드", "세계음식"};
        final Boolean[] btnIsChecked = {btn2_key, btn3_key, btn4_key, btn5_key, btn6_key ,btn7_key, btn8_key, btn9_key, btn10_key, btn11_key, btn12_key};
        mRestaurantReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        mRestaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantsList.clear(); //초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Restaurant restaurant = snapshot.getValue(Restaurant.class);
                    //checkFilter(restaurant);
                    for(int i=0; i<11; i++) {
                        if(btnIsChecked[i] && restaurant.filter.contains(filterCategory[i])) {
                            restaurantsList.add(restaurant);
                            MapPOIItem marker = new MapPOIItem();
                            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(restaurant.getLatitude(),restaurant.getLongitude());
                            marker.setItemName(restaurant.getName());
                            marker.setTag(0);
                            marker.setMapPoint(mapPoint);
                            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
                            marker.setCustomImageResourceId(R.drawable.img_marker_off); // 마커 이미지.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                            marker.setCustomSelectedImageResourceId(R.drawable.img_marker_on); // 마커를 클릭했을때
                            marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                            marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                            // marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                            mMapView.addPOIItem(marker);
                            break;
                        }
                    }

                    /*
                    MapPOIItem marker = new MapPOIItem();
                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(restaurant.getLatitude(),restaurant.getLongitude());
                    marker.setItemName(restaurant.getName());
                    marker.setTag(0);
                    marker.setMapPoint(mapPoint);
                    marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
                    marker.setCustomImageResourceId(R.drawable.img_marker_off); // 마커 이미지.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                    marker.setCustomSelectedImageResourceId(R.drawable.img_marker_on); // 마커를 클릭했을때
                    marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                    marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                    // marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    mMapView.addPOIItem(marker);

                     */

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new RestaurantAdapter(restaurantsList, this);
        recyclerView.setAdapter(adapter);

        ImageView iv_filtering = (ImageView)findViewById(R.id.fb_filtering);
        iv_filtering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilteringActivity.class);
                startActivity(intent);
            }
        });



        /*Floating Button 구현
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new FABClickListener());
         */

    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener userLinstener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mDrawerNickname.setText(user.getNickname());
                mDrawerEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mUserReference.addValueEventListener(userLinstener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ( requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;
            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if ( check_result ) {
                //위치 값을 가져올 수 있음
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /*Floating Button 클릭 이벤트 처리
    class FABClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            // FAB Click 이벤트 처리 구간
            Intent intent = new Intent(getApplicationContext(), FilteringActivity.class);
            startActivity(intent);
        }
    }
     */

    private ArrayList<Restaurant> selectionSortByRating(ArrayList<Restaurant> arrayList) {
        int size = arrayList.size();
        Restaurant minPrice;
        int min;

        for(int i=0; i<size-1; i++) {
            minPrice = arrayList.get(i);
            min = i;
            for (int j = i+1; j < size; j++) {
                if(arrayList.get(j).getRating() < minPrice.getRating()) {
                    minPrice = arrayList.get(j);
                    min = j;
                }
            }
            swap(arrayList, min, i);
        }
        return  arrayList;
    }

    private ArrayList<Restaurant> selectionSortByDisance(ArrayList<Restaurant> arrayList) {
        int size = arrayList.size();
        Restaurant minDistance;
        int min;

        for (int i=0; i<size-1; i++) {
            minDistance = arrayList.get(i);
            min = i;
            for(int j = i+1; j<size; j++) {
                if(getDistanceFromSchool(arrayList.get(j))<getDistanceFromSchool(minDistance)) {
                    minDistance = arrayList.get(j);
                    min = j;
                }
            }
            swap(arrayList, min, i);
        }
        return arrayList;
    }

    private ArrayList<Restaurant> selectionSortByMyPos(ArrayList<Restaurant> arrayList) {
        int size = arrayList.size();
        Restaurant minDistance;
        int min;

        for (int i=0; i<size-1; i++) {
            minDistance = arrayList.get(i);
            min = i;
            for(int j = i+1; j<size; j++) {
                if(getDistanceFromMyPos(arrayList.get(j))<getDistanceFromMyPos(minDistance)) {
                    minDistance = arrayList.get(j);
                    min = j;
                }
            }
            swap(arrayList, min, i);
        }
        return arrayList;
    }

    private void swap(ArrayList<Restaurant> arrayList, int i, int j) {
        Restaurant temp = arrayList.get(i);
        arrayList.set(i,arrayList.get(j));
        arrayList.set(j,temp);
    }

    private double getDistanceFromSchool(Restaurant restaurant) {
        //아주대학교 정문 위치(37.279712,127.043695)
        double x = (restaurant.getLatitude() - 37.279712);
        double y = (restaurant.getLongitude() - 127.043695);
        return Math.sqrt(x*x + y*y);
    }

    private double getDistanceFromMyPos(Restaurant restaurant) {
        double myLatitude = gpsTracker.getLatitude();
        double myLongitude = gpsTracker.getLongitude();
        double x = (restaurant.getLatitude() - myLatitude);
        double y = (restaurant.getLongitude() - myLongitude);
        return Math.sqrt(x*x + y*y);
    }

    /*
    private ArrayList<Restaurant> timeFiltering(ArrayList<Restaurant> arrayList) {
        for(int i = 0; i<arrayList.size(); i++) {
            if(!isOpen(arrayList.get(i))) {
                arrayList.remove(i);
            }
        }
        return arrayList;
    }

    private Boolean isOpen(Restaurant restaurant) {
        boolean isOpen;
        int pickerHour = customDialog.timePicker.getHour();
        int pickerMinute = customDialog.timePicker.getMinute();
        int pickerSecond = 0;
        int pickerTime = (pickerHour*60*60) + (pickerMinute*60) + pickerSecond;
        String open_time = restaurant.getOpen(); // open_time을 데이터베이스에서 받아옴
        String[] splitOpenHourAndMin = open_time.split(":"); // ":"를 기준으로 Hour과 Minitue을 나눔
        String close_time = restaurant.getClose(); // close_time을 데이터베이스에서 받아옴
        String[] splitCloseHourAndMin = close_time.split(":"); //":"를 기준으로 Hour과 Minitue을 나눔
        int openTime = (Integer.parseInt(splitOpenHourAndMin[0])*60*60) + Integer.parseInt(splitOpenHourAndMin[1])*60;
        int closeTime = (Integer.parseInt(splitCloseHourAndMin[0])*60*60) + Integer.parseInt(splitCloseHourAndMin[1])*60;
        if(closeTime>86400) { // 마감시간이 24:00보다 늦게 끝난다면
            if(closeTime <= pickerTime && pickerTime <= openTime) {
                isOpen = false;
            } else {
                isOpen = true;
            }
        } else {
            if(openTime <= pickerTime && pickerTime <= closeTime) {
                isOpen = true;
            } else {
                isOpen = false;
            }
        }
        return isOpen;
    }
     */

    //Sign out
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        finish();
    }

}

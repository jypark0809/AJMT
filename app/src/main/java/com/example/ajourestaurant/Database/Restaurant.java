package com.example.ajourestaurant.Database;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String url; //가게 사진 url
    private String name; //가게 이름
    private String type; //가게 종류
    private double latitude; //위도
    private double longitude; //경도
    private String address; //주소
    private String tel; //전화번호
    private String open; //영업 시작 시간
    private String close; //영업 종료 시간
    private String menu; //대표 메뉴
    private int price; //가격
    private double rating; //평점
    public List<String> filter;
    public List<String> uidList = new ArrayList<>(); // 평점 남긴 유저 uid 리스트
    public List<Menu> menuList = new ArrayList<>(); // 메뉴 리스트

    public Restaurant() {
    }

    public Restaurant(String url, String name, String type, double latitude, double longitude, String address,
                      String tel, String open, String close, String menu, int price, double rating) {
        this.url = url;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.tel = tel;
        this.open = open;
        this.close = close;
        this.menu = menu;
        this.price = price;
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

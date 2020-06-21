package com.example.ajourestaurant.Database;

public class User {
    public String nickname;
    //public String sex;
    public String major;
    public String studentID;

    public User() {
    }

    public User(String nickname, String major, String studentID) {
        this.nickname = nickname;
        //this.sex = sex;
        this.major = major;
        this.studentID = studentID;
    }

    public String getNickname() {
        return nickname;
    }
}

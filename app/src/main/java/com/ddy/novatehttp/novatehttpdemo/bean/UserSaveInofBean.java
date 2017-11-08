package com.ddy.novatehttp.novatehttpdemo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class UserSaveInofBean implements Serializable {
    private String avatar;  //头像
    private String nickname; //昵称
    private String gender; // sex  性别
    private String birthday;  //生日
    private String city; // 城市
    private String sig; //个性签名
    private String locationAvatr;
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocationAvatr() {
        return locationAvatr;
    }

    public void setLocationAvatr(String locationAvatr) {
        this.locationAvatr = locationAvatr;
    }

    @Override
    public String toString() {
        return "UserInofBean{" +
                "avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", city='" + city + '\'' +
                ", sig='" + sig + '\'' +
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

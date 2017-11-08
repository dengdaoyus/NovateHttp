package com.ddy.novatehttp.novatehttpdemo;

import com.ddy.novatehttp.novatehttpdemo.config.UserNameConfig;

import java.util.Random;

public class Test {
    public static void main(String[] args) {

        System.out.println(" "+generateUserNames());

    }
    //随机生成姓名
    public static String  generateUserNames(){
        String usname = null;
        String name= null;
        System.out.println(" "+ UserNameConfig.surname.length);
        System.out.println(" "+ UserNameConfig.name_male.length);
        System.out.println(" "+ UserNameConfig.name_female.length);
        for (int i = 0; i < 1; i++) {
            usname = UserNameConfig.surname[(new Random().nextInt(UserNameConfig.surname.length) )];
            if (i / 2 == 0) {
                name = UserNameConfig.name_male[(new Random().nextInt(UserNameConfig.name_male.length) )];
            } else {
                name = UserNameConfig.name_female[(new Random().nextInt(UserNameConfig.name_female.length))];
            }
        }
        return  usname+name;
    }
}

package com.ddy.novatehttp.novatehttpdemo.utils;

import com.ddy.novatehttp.novatehttpdemo.config.AvatarConfig;
import com.ddy.novatehttp.novatehttpdemo.config.CityConfig;
import com.ddy.novatehttp.novatehttpdemo.config.PhoneConfig;
import com.ddy.novatehttp.novatehttpdemo.config.UserNameConfig;

import java.util.Random;

/**
 * 随机信息工具
 */
public class GenerateUtils {

    //随机生成姓名
    public void generateUserName() {
        String usname;
        String name;
        System.out.println(" " + UserNameConfig.surname.length);
        System.out.println(" " + UserNameConfig.name_male.length);
        System.out.println(" " + UserNameConfig.name_female.length);
        for (int i = 0; i < 100; i++) {
            usname = UserNameConfig.surname[(new Random().nextInt(UserNameConfig.surname.length))];
            if (i / 2 == 0) {
                name = UserNameConfig.name_male[(new Random().nextInt(UserNameConfig.name_male.length))];
            } else {
                name = UserNameConfig.name_female[(new Random().nextInt(UserNameConfig.name_female.length))];
            }
            System.out.println(i + " :" + (usname + name));
        }
    }

    //随机生成姓名
    public static String generateUserNames() {
        String usname = null;
        String name = null;
        System.out.println(" " + UserNameConfig.surname.length);
        System.out.println(" " + UserNameConfig.name_male.length);
        System.out.println(" " + UserNameConfig.name_female.length);
        for (int i = 0; i < 1; i++) {
            usname = UserNameConfig.surname[(new Random().nextInt(UserNameConfig.surname.length))];
            if (i / 2 == 0) {
                name = UserNameConfig.name_male[(new Random().nextInt(UserNameConfig.name_male.length))];
            } else {
                name = UserNameConfig.name_female[(new Random().nextInt(UserNameConfig.name_female.length))];
            }
        }
        return usname + name;
    }


    public static String generatePhone() {
        return PhoneConfig.phone[0];
    }

    public static String generateAvatar() {
        return AvatarConfig.avatar[(new Random().nextInt(AvatarConfig.avatar.length))];
    }

    private static int cityPosition = 0;

    public static String generateCity() {
        cityPosition = (new Random().nextInt(CityConfig.city.length));
        return CityConfig.city[cityPosition];
    }

    public static String generateLo() {
        return CityConfig.lo[cityPosition];
    }

    public static String generateLa() {
        return CityConfig.la[cityPosition];
    }
}

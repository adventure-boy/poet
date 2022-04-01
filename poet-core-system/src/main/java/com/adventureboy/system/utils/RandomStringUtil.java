package com.adventureboy.system.utils;

import java.util.Random;

public class RandomStringUtil {
    public static String getRandomString(Integer num) {
        //用于储存随机生成的字符
        char[] chars = new char[num];
        for (Integer i = 0; i < num; i++) {
            char randomNum = (char)new Random(1).nextInt();

        }
        return "";
    }
}

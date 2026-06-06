package ru.proba.authentication.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RedisKeyMaker {
    public static String concatenateKey(String str1, String str2){
        return str1 + ":" + str2;
    }
}

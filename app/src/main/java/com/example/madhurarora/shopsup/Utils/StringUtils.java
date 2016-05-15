package com.example.madhurarora.shopsup.Utils;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class StringUtils {
    public static boolean isNullorEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
    }
}

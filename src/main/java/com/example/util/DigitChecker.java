package com.example.util;

public class DigitChecker {

    public static String checkImeiDigit(String imei) {

        return imei.length() == 14 ? imei : imei.substring(0,14);
    }

}

package com.example.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

    public static String getDateTimeNow(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(dateTimeFormatter).toString();
    }

}

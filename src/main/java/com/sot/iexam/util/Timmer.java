package com.sot.iexam.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Kimbobo
 */
public class Timmer {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static int timeLong() {
        Long time = System.currentTimeMillis() / 1000L;
        return time.intValue();
    }

    public static String date(int timestamp, String... format) {
        if (timestamp == 0) {
            return " - ";
        } else {
            String formatString = "yyyy-MM-dd HH:mm:ss";
            if (format.length == 1) {
                formatString = format[0];
            }

            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
            return sdf.format(new Date((long) timestamp * 1000L));
        }
    }

    public static String now() {
        return formatter.format(LocalDateTime.now());
    }
}
    
package com.sot.iexam;

import com.sot.iexam.util.Timmer;
import sun.security.krb5.internal.PAData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;


public class test {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(Timmer.now());
        LocalDateTime parse = LocalDateTime.parse(Timmer.now(), formatter);
        LocalDateTime dateTime = parse.plusMinutes(1);
        System.out.println(dateTime.format(formatter));
    }
}
    
package com.demo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static String current(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}

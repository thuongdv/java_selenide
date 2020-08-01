package com.demo.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static String current(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}

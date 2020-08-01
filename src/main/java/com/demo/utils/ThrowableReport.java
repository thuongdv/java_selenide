package com.demo.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Arrays;

public class ThrowableReport {
    /**
     * This field is used to filter code lines which relate to the project, not libs
     * e.g. com.demo.pages.HomePage.goToRegisterPage(HomePage.java:21)
     */
    private static final String PROJECT_PACKAGE = "com.demo";

    public static Throwable newThrowable(Throwable t) {
        String detailMessage = getDetailMessage(t);
        Throwable nt = new Throwable(detailMessage);
        nt.setStackTrace(getStackTrace(t));

        return nt;
    }

    public static String getScreenshot(Throwable t) {
        return readField(t, "screenshot");
    }

    public static StackTraceElement[] getStackTrace(Throwable t) {
        return Arrays.stream(t.getStackTrace()).
            filter(e -> e.toString().startsWith(PROJECT_PACKAGE)).toArray(StackTraceElement[]::new);
    }

    public static String getDetailMessage(Throwable t) {
        String message = readField(t, "detailMessage");
        return message == null ? t.getMessage() : message;
    }

    private static String readField(Throwable t, String field) {
        try {
            return FieldUtils.readField(t, field, true).toString();
        } catch (Exception e) {
            return null;
        }
    }
}

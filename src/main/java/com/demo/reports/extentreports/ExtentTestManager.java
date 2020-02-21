package com.demo.reports.extentreports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static volatile ThreadLocal<ExtentTest> extentTests = new ThreadLocal<>();

    public static ExtentTest getExtentTest() {
        return extentTests.get();
    }

    public static void setExtentTest(String name) {
        extentTests.set(ExtentReportManager.getExtentReport().createTest(name));
    }
}

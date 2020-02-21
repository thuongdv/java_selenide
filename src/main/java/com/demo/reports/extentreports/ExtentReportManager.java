package com.demo.reports.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportManager {
    private static volatile ThreadLocal<ExtentReports> extentReports = new ThreadLocal<>();

    public static ExtentReports getExtentReport() {
        return extentReports.get();
    }

    public static void setExtentReport(String folder) {
        String projectPath = System.getProperty("user.dir");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(String.join(File.separator, projectPath, folder));
        ExtentReports extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);
        extentReports.set(extentReport);
    }
}

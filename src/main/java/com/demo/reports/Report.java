package com.demo.reports;

import com.demo.reports.extentreports.ExtentReportManager;
import com.demo.reports.extentreports.ExtentTestManager;

/**
 * This class is used as a report controller.
 * Once we change to use another report instead of ExtentReports, just change the content of these methods.
 */
public class Report {
    public static void info(String message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void debug(String message) {
        ExtentTestManager.getExtentTest().debug(message);
    }

    public static void setReport(String folder) {
        ExtentReportManager.setExtentReport(folder);
    }

    public static void flushReport() {
        ExtentReportManager.getExtentReport().flush();
    }

    public static void setReportTest(String testName) {
        ExtentTestManager.setExtentTest(testName);
    }
}

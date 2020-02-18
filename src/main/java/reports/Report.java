package reports;

import reports.extentreports.ExtentReportManager;
import reports.extentreports.ExtentTestManager;

public class Report {
    public static void info(String message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void setReport() {
        ExtentReportManager.setExtentReport();
    }

    public static void flushReport() {
        ExtentReportManager.getExtentReport().flush();
    }

    public static void setReportTest(String testName) {
        ExtentTestManager.setExtentTest(testName);
    }
}

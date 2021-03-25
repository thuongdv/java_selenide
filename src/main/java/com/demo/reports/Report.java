package com.demo.reports;


import com.demo.reports.extentreports.ExtentReportManager;
import com.demo.reports.extentreports.ExtentTestManager;
import com.demo.utils.ThrowableReport;
import lombok.extern.log4j.Log4j2;


/**
 * Singleton: https://www.journaldev.com/171/thread-safety-in-java-singleton-classes
 */
@Log4j2
public class Report implements IReport {
    private static volatile Report instance;
    private static final Object mutex = new Object();

    private Report() {
    }

    public static Report getInstance() {
        Report result = instance;
        if (result == null) {
            log.debug("Get report instance");
            synchronized (mutex) {
                result = instance;
                if (result == null) instance = result = new Report();
            }
        }
        return result;
    }

    @Override
    public void info(String message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    @Override
    public void fail(Throwable throwable) {
        ExtentTestManager.getExtentTest().fail(ThrowableReport.newThrowable(throwable));
    }

    @Override
    public void debug(String message) {
        ExtentTestManager.getExtentTest().debug(message);
    }

    @Override
    public void setReport(String folder) {
        ExtentReportManager.setExtentReport(folder);
    }

    @Override
    public void flushReport() {
        ExtentReportManager.getExtentReport().flush();
    }

    @Override
    public void setReportTest(String testName) {
        ExtentTestManager.setExtentTest(testName);
    }
}

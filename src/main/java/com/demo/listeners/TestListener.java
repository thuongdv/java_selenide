package com.demo.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.demo.reports.Report;
import com.demo.reports.extentreports.ExtentTestManager;
import com.demo.utils.ThrowableReport;
import com.google.common.io.Files;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Report.setReportTest(result.getMethod().getDescription());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String ba = ThrowableReport.usefulMessage(throwable);
        //ExtentTestManager.getExtentTest().fatal(ThrowableReport.usefulMessage(throwable));
        ExtentTestManager.getExtentTest().fatal(ThrowableReport.newThrowable(throwable));
        ExtentTest node = ExtentTestManager.getExtentTest().createNode("Debugger information");
        String screenshot = ThrowableReport.getScreenshot(throwable);
        try {
            String file = Files.getNameWithoutExtension(screenshot);
            node.fatal("", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            String pageSourceLink = """
                                        <a href="%s.html" target="_blank">Page source</a>
                                    """;
            node.fatal(String.format(pageSourceLink, file));
        } catch (Exception e) {
            node.fatal(ThrowableReport.newThrowable(e));
        }
    }
}

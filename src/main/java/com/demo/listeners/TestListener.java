package com.demo.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.demo.reports.Report;
import com.demo.reports.extentreports.ExtentTestManager;
import com.demo.utils.ThrowableReport;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Report.setReportTest(result.getMethod().getDescription());
    }

    @SneakyThrows
    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        ExtentTestManager.getExtentTest().fatal(ThrowableReport.newThrowable(throwable));
        String screenshot = ThrowableReport.getScreenshot(throwable);
        if (screenshot == null) return;

        ExtentTest node = ExtentTestManager.getExtentTest().createNode("Debugger information");
        String file = FilenameUtils.getBaseName(screenshot);
        node.fatal("", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        String pageSourceLink = "<a href=\"%s.html\" target=\"_blank\">Page source</a>";
        node.fatal(String.format(pageSourceLink, file));
    }
}

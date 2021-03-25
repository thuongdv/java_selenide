package com.demo.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.codeborne.selenide.Configuration;
import com.demo.reports.Report;
import com.demo.reports.extentreports.ExtentTestManager;
import com.demo.utils.Constants;
import com.demo.utils.ThrowableReport;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class TestListener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        log.debug("on start");
        String reportFolder = String.join(
            File.separator, Constants.REPORT_FOLDER_ROOT,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.REPORT_FOLDER_FORMAT))
        );
        Report.getInstance().setReport(reportFolder);
        Configuration.browserSize = Constants.BROWSER_SIZE;
        Configuration.reportsFolder = reportFolder;
    }

    @Override
    public void onTestStart(ITestResult result) {
        Report.getInstance().setReportTest(result.getMethod().getDescription());
    }

    @SneakyThrows
    @Override
    public void onTestFailure(ITestResult result) {
        val throwable = result.getThrowable();
        ExtentTestManager.getExtentTest().fatal(ThrowableReport.newThrowable(throwable));
        val screenshot = ThrowableReport.getScreenshot(throwable);
        if (screenshot == null) return;

        val node = ExtentTestManager.getExtentTest().createNode("Debugger information");
        val file = FilenameUtils.getBaseName(screenshot);
        node.fatal("", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
        val pageSourceLink = "<a href=\"%s.html\" target=\"_blank\">Page source</a>";
        node.fatal(String.format(pageSourceLink, file));
    }
}

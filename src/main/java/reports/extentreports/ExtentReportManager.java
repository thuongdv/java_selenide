package reports.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static volatile ThreadLocal<ExtentReports> extentReports = new ThreadLocal<>();

    public static ExtentReports getExtentReport() {
        return extentReports.get();
    }

    public static void setExtentReport() {
        String projectPath = System.getProperty("user.dir");
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MMM_dd-HH_mm_ss"));
        String reportFolder = "ExtentReport";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(String.join(File.separator, projectPath, reportFolder, currentTime));
        ExtentReports extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);
        extentReports.set(extentReport);
    }
}

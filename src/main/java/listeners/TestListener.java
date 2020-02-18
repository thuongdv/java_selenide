package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.Report;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Report.setReportTest(result.getMethod().getDescription());
    }
}

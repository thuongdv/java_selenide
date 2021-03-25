package com.demo.reports;

public interface IReport {
    void info(String message);

    void fail(Throwable throwable);

    void debug(String message);

    void setReport(String folder);

    void flushReport();

    void setReportTest(String testName);
}

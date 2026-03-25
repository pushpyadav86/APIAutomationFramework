package api.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentListenerClass implements ITestListener {

    ExtentSparkReporter htmlReporter;
    ExtentReports reports;
    ExtentTest test;

    public void configureReport() {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "MyAPITestReport-" + timestamp + ".html";

        String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + reportName;

        htmlReporter = new ExtentSparkReporter(reportPath);
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        // System info
        reports.setSystemInfo("Machine", "Local");
        reports.setSystemInfo("OS", System.getProperty("os.name"));
        reports.setSystemInfo("User", System.getProperty("user.name"));
        reports.setSystemInfo("Browser", "Chrome");

        // Report configuration
        htmlReporter.config().setDocumentTitle("Extent Report");
        htmlReporter.config().setReportName("Automation Test Execution Report");
        htmlReporter.config().setTheme(Theme.DARK);
    }

    @Override
    public void onStart(ITestContext context) {
        configureReport();
        System.out.println("Execution started");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = reports.createTest(result.getName());
        test.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test failed: " + result.getName());
        test.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Test skipped: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        reports.flush();
        System.out.println("Execution completed");
    }
}
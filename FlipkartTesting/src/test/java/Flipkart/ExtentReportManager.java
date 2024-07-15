package Flipkart;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener 
{

	public ExtentSparkReporter sparkReporter;   //UI of the report
	public ExtentReports extent;  //populate common info of the report
	public ExtentTest test; //creating test case entries in the report and update status of the test methods
	
	public void onStart(ITestContext context)
	{
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/myreport.html");
		
		sparkReporter.config().setDocumentTitle("Automation report");
		sparkReporter.config().setReportName("Functional testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Computer Name","localhost");
		extent.setSystemInfo("Environment","QA");
		extent.setSystemInfo("Tester name","Aastha");
		extent.setSystemInfo("os","Windows11");
		extent.setSystemInfo("Brwser name","Chrome");
	}
	

	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getName());
		test.log(Status.PASS,"Test case passed is:"+result.getName());
	}
	
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getName());
		test.log(Status.FAIL,"Test case failed is:"+result.getName());
		test.log(Status.FAIL,"Test case failed cause is:"+result.getThrowable());
		
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getName());
		test.log(Status.SKIP,"Test case skipped is:"+result.getName());
	}
	
	public void onFinish(ITestContext context)
	{
		extent.flush();
	}
	
}

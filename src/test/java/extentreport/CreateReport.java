package extentreport;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class CreateReport 
{
	WebDriver driver;
	ExtentHtmlReporter reporter;
	ExtentReports report;
	ExtentTest test; 

	@BeforeTest
	public void setup() 
	{
		reporter = new ExtentHtmlReporter("../SeleniumAuto/report/report1.html");
		reporter.config().setDocumentTitle("Selenium Report");
		reporter.config().setReportName("Get Title");
		reporter.config().setTheme(Theme.DARK);

		report=new ExtentReports();
		report.attachReporter(reporter);
		
		report.setSystemInfo("OS", "Windows");
		report.setSystemInfo("Browser", "Chrome");
		report.setSystemInfo("TESTER", "PAVAN");
	}

	@AfterTest
	public void flush() 
	{
		report.flush();
	}

	@BeforeMethod
	public void launch() 
	{
		System.setProperty("webdriver.chrome.driver", "../SeleniumAuto/src/main/resources/DriverExe/chromedriver.exe");
		driver= new ChromeDriver();
		driver.get("https://www.facebook.com/");
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void endReport(ITestResult result) throws IOException 
	{
		if(result.getStatus()==ITestResult.FAILURE) 
		{
			test.log(Status.FAIL, "TC failed" + result.getName()); // Name of the method
			test.log(Status.FAIL, "TC failed" + result.getThrowable()); // Error

			String imagepath=	CreateReport.getscreenshot(driver, result.getName());

			test.addScreenCaptureFromPath(imagepath);
		}

		else if(result.getStatus()==ITestResult.SUCCESS) 
		{
			test.log(Status.PASS, "TC Pass" + result.getName());
		}
		else if(result.getStatus()==ITestResult.SKIP) 
		{
			test.log(Status.SKIP, "TC SKIP" + result.getName());
		}
	}

	private static String getscreenshot(WebDriver driver, String name) throws IOException 
	{
		File srcfile =((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destfile= System.getProperty("user.dir")+"/Screenshots/"+name+".png";
		File finaldest = new File(destfile) ;
		FileUtils.copyFile(srcfile,finaldest);

		return destfile;
	}

	@Test
	public void getTitle() //passing
	{
		test = report.createTest("getTitle"); // Imp
		
		
		String title =	driver.getTitle();
		System.out.println(title);
		String exp ="Facebook – log in or sign up";

		Assert.assertEquals(title, exp);

	}

	@Test
	public void getTitle1() //fail
	{
		test = report.createTest("getTitle1");
		
		
		String title =	driver.getTitle();
		System.out.println(title);
		String exp ="Facebook – log in or sign";
		Assert.assertEquals(title, exp);
	}



}

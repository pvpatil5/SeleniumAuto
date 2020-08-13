package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Sample1 {

	public static void main(String[] args) 
	{
		System.setProperty("webdriver.chrome.driver" ,"../SeleniumAuto/src/main/resources/DriverExe/chromedriver.exe" );
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.facebook.com/");
		System.out.println(driver.getTitle());


	}

}

package com.codechiev.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestSeleniumChrome {

	@Test
	public void testChrome() throws InterruptedException {
		  // Optional, if not specified, WebDriver will search your path for chromedriver.
		  System.setProperty("webdriver.chrome.driver", "/Users/metasoft/Documents/projects/source/chromedriver");

		  WebDriver driver = new ChromeDriver();
		  driver.get("http://121.201.24.170:8080/data-center/");
		  Thread.sleep(5000);  // Let the user actually see something!
		  WebElement tenant = driver.findElement(By.cssSelector("input[name='tenant']"));
		  tenant.sendKeys("sysadmin");  
		  WebElement user = driver.findElement(By.cssSelector("input[name='username']"));
		  user.sendKeys("sysadmin");  
		  WebElement passwd = driver.findElement(By.cssSelector("input[name='password']"));
		  passwd.sendKeys("pwd"); 
		  ((HasInputDevices) driver).getKeyboard().sendKeys(Keys.RETURN);  
		  Thread.sleep(10000);  // Let the user actually see something!
		  driver.quit();
	}

	@Test
	public void remote() throws MalformedURLException{
		WebDriver driver = new RemoteWebDriver( new URL( "http://127.0.0.1:9515"), DesiredCapabilities.chrome());
		driver.get("http://baidu.com");
	}
}

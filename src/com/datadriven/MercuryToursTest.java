package com.datadriven;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.cofig.ExcelConfigdata;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;


import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class MercuryToursTest
{
  public WebDriver driver;
 
  @Test(dataProvider = "getdata")
  public void loginwithValidds(String username,String password)
  {
     driver.findElement(By.xpath("//input[@name='userName']")).sendKeys(username);
     driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
     driver.findElement(By.xpath("//input[@name='login']")).click();
     System.out.println("User has login into Mercury Tours application successfully");
     boolean act_flag= driver.findElement(By.xpath("//img[@src='/images/masts/mast_flightfinder.gif']")).isDisplayed();
     boolean exp_flag=true;
     Assert.assertEquals(act_flag, exp_flag);
     driver.findElement(By.linkText("SIGN-OFF")).click();
  
  }
  @BeforeMethod
  public void getAllCookies()
  {
	  Set<Cookie> cookies=driver.manage().getCookies();
	  for(Cookie cookie:cookies)
	  {
		  System.out.println(cookie.getName());
	  }
  }

  @AfterMethod
  public void captureScreenshot() throws IOException 
  {
	  System.out.println("In captureScreenshot method under AfterMethod");
	  File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  FileUtils.copyFileToDirectory(src, new File("F:\\Suvidya_data\\Java_Selenium\\DataDrivenMercuryTest\\Screenshots\\"));
	  System.out.println("Screenshot has captured successfully");
  }


  @DataProvider
  public Object[][] getdata() throws IOException 
  {
	  ExcelConfigdata config =new ExcelConfigdata();
	  config.readExcel("C:\\Users\\TANMAY\\Selenium\\DataDriven\\logindetails.xlsx");
	  
	  int rowno= config.getRowCount("Sheet1");
	  System.out.println("Total number of rows are:" +rowno);
	  
	  int columns=config.getColumnCount("Sheet1");
	  System.out.println("Total number of columns are:"+columns);
	  
	  Object[][] data= new Object[rowno] [columns];
	  
	  for(int i=0;i<rowno;i++)
	  {
		  for(int j=0;j<columns;j++)
		  {
			  data[i][j]= config.getData("Sheet1", i, j);
		  }
	  }
	return data;
	  
  }
  @BeforeClass
  public void maximizeBrowser() 
  {
	  driver.manage().window().maximize();
	  System.out.println("Browser has maximized successfully");
  }

  @AfterClass
  public void deleteAllCookies() 
  {
	  System.out.println("In deleteAllCookies method under AfterClass");
	  driver.manage().deleteAllCookies();
  }

  @BeforeTest
  public void enterApplicationURL() 
  {
	  System.out.println("In enterApplicationURL method under BeforeTest");
	  driver.get("http://newtours.demoaut.com/mercurywelcome.php");
	  System.out.println("Application url has enetered successfully");
  }

  @AfterTest
  public void db_Connectionclosed() 
  {
	  System.out.println("db_Connectionclosed method under AfterTest");
  }

  @BeforeSuite
  public void openBrowser() 
  {
	  System.setProperty("webdriver.chrome.driver",

				"C:\\Users\\TANMAY\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
  }

  @AfterSuite
  public void closeBrowser() 
  {
	  driver.close();
  }

}
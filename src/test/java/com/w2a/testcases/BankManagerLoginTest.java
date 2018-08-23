package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException{
			
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn_CSS"))).click();
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))));
		
		Thread.sleep(3000);
		
		Assert.fail("Login Not Successfull");
	}

}

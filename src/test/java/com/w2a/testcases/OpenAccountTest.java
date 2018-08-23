package com.w2a.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.utilities.TestUtil;
import com.w2a.base.TestBase;

public class OpenAccountTest extends TestBase {
	

	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void openAccountTest(String customer, String currency) throws InterruptedException{
		/*if(!TestUtil.isTestRunnable("openAccountTest", null)){
			throw new SkipException("Skipping the test " +"openAccountTest"+"as the Run mode is No ");
		}*/
		
		click("openAcc_CSS");
		select("customer_CSS",customer);
		select("currency_CSS",currency);
		click("process_CSS");
		
		Thread.sleep(2000);
		
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
	}


}

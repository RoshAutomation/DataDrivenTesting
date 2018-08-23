package com.w2a.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.utilities.TestUtil;
import com.w2a.base.TestBase;

public class AddCustomerTest extends TestBase {
	
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String PostCode,String alertext) throws InterruptedException{
		
		click("addCustBtn_CSS");
		type("fName_CSS",firstName);
		type("lName_CSS",lastName);
		type("postCode_CSS",PostCode);
		click("addBtn_CSS");
		
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(alertext));
		
		Thread.sleep(3000);
		
		alert.accept();
		
		Thread.sleep(3000);
		
	}
	
	

}

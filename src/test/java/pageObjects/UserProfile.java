package pageObjects;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cucumber.listener.Reporter;

import reusability.ReusableMethods;
import reusability.Utilities;

public class UserProfile {

	WebDriver driver;
	ReusableMethods res = new ReusableMethods();
	Utilities util = new Utilities();

	public UserProfile(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@name='firstname']")
	public WebElement txt_Firstname;

	@FindBy(xpath = "//input[@name='surname']")
	public WebElement txt_Surname;

	@FindBy(xpath = "//input[@placeholder='Phone Number']")
	public WebElement txt_Phone_Number;

	@FindBy(xpath = "//button[@name='confirm']")
	public WebElement btn_Change;

	@FindBy(xpath = "//span[contains(text(),'Update Personal Data & Password')]")
	public WebElement btn_Update_Personal_Data;

	@FindBy(xpath = "//input[contains(@name,'oldPassword')]")
	public WebElement txt_Old_Password;

	@FindBy(xpath = "//input[contains(@name,'userPassword')]")
	public WebElement txt_New_Password;

	@FindBy(xpath = "//input[contains(@name,'userPasswordConfirm')]")
	public WebElement txt_Confirm_Password;

	@FindBy(xpath = "//div[contains(@class,'privacy_buttons text-right')]//button[contains(text(),'Confirm')]")
	public WebElement btn_Confirm;

	@FindBy(xpath = "//a[contains(@class,'btn btn-ctl btn-sign-in')]//self::a[contains(text(),'OK')]")
	public WebElement btn_toast_Ok;

	@FindBy(xpath = "//*[contains(text(),'Update Personal Data & Password')]")
	public WebElement link_update_personal_data_and_password;

	@FindBy(xpath = "//p[contains(text(),'Congratulation! Your password has been changed')]")
	public WebElement txt_suceesful_change;
	
	@FindBy(xpath = "//span[contains(@class,'errormsg-lastrow')]")
	public WebElement txt_mimatch_password;
	
	@FindBy(xpath = "//span[contains(@class,'errormsg-large ng-scope')]")
	public WebElement txt_notComplex_password;
	
	@FindBy(xpath = "//div[@class='widget servaccpages subscription-details ng-scope']")
	public WebElement tbl_subscription_details;
	
	@FindBy(xpath = "//p[@class='mb-4 ng-scope']")
	public WebElement lbl_no_subscriptions;
	
	@FindBy(xpath = "//span[@class='ng-scope']//self::span[contains(text(),'Current Subscriptions')]")
	public WebElement lnk_current_subscriptions;
	
	@FindBy(xpath = "//a[@class='vodacom-link']//self::a[contains(text(),'Unsubscribe')]")
	public WebElement lnk_unsubscribe;
	
	@FindBy(xpath = "//p[@class='generic-p-popup ng-binding ng-scope']")
	public WebElement msg_popup_unsubscribe;
	
	@FindBy(xpath = "//a[@class='btn btn-ctl btn-sign-in ng-binding']")
	public WebElement btn_popup_unsubscribe_close;
	
	
	public void updateNewPassword(WebDriver driver, String password, String newPassword)
			throws InterruptedException, IOException {
		ReusableMethods res = new ReusableMethods();
		// Utilities util = new Utilities();
		res.wait(driver).until(ExpectedConditions.elementToBeClickable(btn_Update_Personal_Data));
		btn_Update_Personal_Data.click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		txt_Old_Password.sendKeys(password);
		Thread.sleep(1000);
		txt_New_Password.sendKeys(newPassword);
		Thread.sleep(1000);
		txt_Confirm_Password.sendKeys(newPassword);
		Thread.sleep(1000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		res.clickUsingJavascriptExecutor(driver, btn_Confirm);
		Thread.sleep(1000);
	}

	public void clickOkmessage(WebDriver driver) {
		ReusableMethods res = new ReusableMethods();
		res.clickUsingJavascriptExecutor(driver, btn_toast_Ok);
	}

	public void updateUserDetails(WebDriver driver, String Firstname, String Surname, String Phone_number)
			throws InterruptedException, IOException {
		res.wait(driver).until(ExpectedConditions.visibilityOf(link_update_personal_data_and_password));
		link_update_personal_data_and_password.click();
		Thread.sleep(1000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		Thread.sleep(1000);
		txt_Firstname.sendKeys(Firstname);
		Thread.sleep(1000);
		txt_Surname.sendKeys(Surname);
		txt_Phone_Number.clear();
		Thread.sleep(1000);
		txt_Phone_Number.sendKeys(Phone_number);
		Thread.sleep(1000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		res.clickUsingJavascriptExecutor(driver, btn_Change);
		Thread.sleep(1000);
	}
	
	public void passwordMismatch(WebDriver driver, String password, String newPassword1, String newPassword2)
			throws InterruptedException, IOException {
		// ReusableMethods res = new ReusableMethods();
		// Utilities util = new Utilities();
		btn_Update_Personal_Data.click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		txt_Old_Password.sendKeys(password);
		Thread.sleep(1000);
		txt_New_Password.sendKeys(newPassword1);
		Thread.sleep(1000);
		txt_Confirm_Password.sendKeys(newPassword2);
		Thread.sleep(1000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		res.clickUsingJavascriptExecutor(driver, btn_Confirm);
		Thread.sleep(1000);
	}
	
	public void clickOnCurrentSubscriptions()
	{
		lnk_current_subscriptions.click();
	}
}


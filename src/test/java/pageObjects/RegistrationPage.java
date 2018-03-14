package pageObjects;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import reusability.ReusableMethods;
import reusability.Utilities;

public class RegistrationPage {

	WebDriver driver;
	ReusableMethods res =new ReusableMethods();
	public RegistrationPage(WebDriver driver) {           
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	//@FindBy(xpath = "//input[contains(@placeholder,'Name')]")
	//@FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required']//self::input[@name='name']")
	@FindBy(xpath = "//input[@name = 'name']")
	public WebElement txt_Name;
	
	//@FindBy(xpath = "//input[contains(@placeholder,'Surname')]")
	@FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required']//self::input[@name='surname']")
	public WebElement txt_Surname;
	
	//@FindBy(xpath = "//input[contains(@placeholder,'Phone number')]")
	@FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required ng-valid-maxlength']//self::input[@name='phoneNumber']")
	public WebElement txt_Phone_Num;
	
	//@FindBy(xpath = "//input[@name='userPassword']")
	@FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required']//self::input[@name='userPassword']")
	public WebElement txt_Password;
	
	//@FindBy(xpath = "//input[@name='userPasswordConfirm']")
	@FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required']//self::input[@name='userPasswordConfirm']")
	public WebElement txt_PasswordConfirm;
	
	@FindBy(id = "terms-and-condition")
	public WebElement txt_TandC;
	
	@FindBy(xpath = "//button[@class='btn btn-ctl btn-block btn-registration submit']")
	public WebElement btn_nextStep;
	
	@FindBy(xpath = "//span[contains(text(),'PIN')]")
	public WebElement msg_OTP;
	
	@FindBy(xpath = "//div[contains(@ng-if,'registrationSuccess || resetPasswordDone')]/p")
	public WebElement msg_registration_success;
	
	@FindBy(xpath = "//a[contains(@class,'btn btn-ctl btn-sign-in ng-binding')]//self::a[contains(text(),'Login')]")
	public WebElement btn_login;
	
	@FindBy(xpath = "//a[contains(@class,'btn btn-ctl btn-sign-in ng-binding')]//self::a[contains(text(),'Submit PIN')]")
	public WebElement btn_submit_otp;
	
	@FindBy(xpath = "/html/body/div[8]/div[2]/div[2]/div[3]/a[1]")
	public WebElement btn_resend_otp;
	
	@FindBy(xpath = "//p[@class='generic-p-popup ng-binding ng-scope']")
	public WebElement msg_invaliduser;
	
	@FindBy(xpath = "//a[contains(@class,'btn btn-ctl btn-sign-in ng-binding')]//self::a[contains(text(),'OK')]")
	public WebElement btn_ok_invaliduser;
	
	@FindBy(xpath = "//*[contains(@class,'ng-modal-close')]")
	public WebElement btn_Close_PopUp;

	@FindBy(css = ".btn btn-ctl btn-block btn-registration submit")
	public WebElement btn_nextstep_disabled;

	@FindBy(xpath="//span[@class='ng-scope']//self::span[contains(text(),'The password you entered does not match, please try again.')]")
	public WebElement msg_password_mismatch;
	
	@FindBy(xpath="//span[@class='checkingMail ng-scope']")
	public WebElement msg_password_field_constraints;
	
	@FindBy(xpath="//span[@class='ng-binding']//self::span[contains(text(),'Phone number should consist of 11 digits')]")
	public WebElement msg_phonenumber_field_constraint;
	
	@FindBy(xpath="//span[@class='ng-binding']//self::span[contains(text(),'Please enter your name')]")
	public WebElement msg_name_field_constraint;
	
	@FindBy(xpath="//p[@class='generic-p-popup ng-binding ng-scope']")
	public WebElement msg_invalid_otp;
	
	@FindBy(xpath="//div[@class='ng-modal-close']")
	public WebElement dialog_enter_OTP_close;
	
	@FindBy(xpath="//span[@class='terms-and-conditions']")
	public WebElement lnk_TandC;
	
	@FindBy(xpath="//a[@class='btn btn-ctl btn-sign-in']//self::a[contains(text(),'Close')]")
	public WebElement btn_TandC_close;
	
	@FindBy(xpath="//div[@class='ng-modal-header ng-scope']//span[contains(text(),'Terms and Conditions')]")
	public WebElement lbl_TandC_heading;
	
	@FindBy(xpath="//p[@class='generic-p-popup ng-binding ng-scope']")
	public WebElement msg_user_already_exists;
	
	
	public void clickingOnRegistration(String name, String phoneNumber, String userPassword) throws InterruptedException, IOException
	{
		Utilities util = new Utilities();
		ReusableMethods reuse = new ReusableMethods();
		Thread.sleep(1000);
		txt_Name.sendKeys(name);
		Thread.sleep(1000);
		txt_Surname.sendKeys(name);
		Thread.sleep(1000);
		txt_Phone_Num.sendKeys(phoneNumber);
		Thread.sleep(1000);
		txt_Password.sendKeys(userPassword);
		Thread.sleep(1000);
		txt_PasswordConfirm.sendKeys(userPassword);
		Thread.sleep(1000);
		txt_TandC.click();
		Thread.sleep(3000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		res.pageScroll(driver, btn_nextStep);
		reuse.clickUsingJavascriptExecutor(driver,btn_nextStep);
	}

	public void clickOnRegistration(String name, String phoneNumber, String userPassword, String...strings) throws InterruptedException, IOException
	{
		Utilities util = new Utilities();
		txt_Name.sendKeys(name);
		Thread.sleep(1000);
		txt_Surname.sendKeys(name);
		Thread.sleep(1000);
		txt_Phone_Num.sendKeys(phoneNumber);
		Thread.sleep(1000);
		txt_Password.sendKeys(userPassword);
		Thread.sleep(1000);
		txt_PasswordConfirm.sendKeys(userPassword);
		Thread.sleep(1000);
		if (strings.length==0)
		{
			txt_TandC.click();
		}
		Thread.sleep(3000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		Thread.sleep(2000);
		res.pageScroll(driver, btn_nextStep);
		
		if (getDisabledPropForNextStep()==true)
		{
			btn_nextStep.click();
		}
		else
		{
			Reporter.addStepLog("Next step button disabled");
		}
	}
	
	public void clickOnLogin()
	{
		btn_login.click();
	}
	
	public void submitOTP()
	{
		btn_submit_otp.click();
	}
	
	public boolean getDisabledPropForNextStep()
	{	
		Boolean flag = false;
		String disable_value = btn_nextStep.getAttribute("disabled");
		if (disable_value.contains("true"))
		{	
			flag = true;
		}
		else
		{
			flag = false;
		}
		
		return flag;
	}
	
	public void clickOnRegistration_passwordMismatch(String name, String phoneNumber, String userPassword, String confirmPassword) throws InterruptedException, IOException
	{
		Utilities util = new Utilities();
		ReusableMethods reuse = new ReusableMethods();
		txt_Name.sendKeys(name);
		Thread.sleep(1000);
		txt_Surname.sendKeys(name);
		Thread.sleep(1000);
		txt_Phone_Num.sendKeys(phoneNumber);
		Thread.sleep(1000);
		txt_Password.sendKeys(userPassword);
		Thread.sleep(1000);
		txt_PasswordConfirm.sendKeys(confirmPassword);
		Thread.sleep(1000);
		txt_TandC.click();
		Thread.sleep(3000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		res.pageScroll(driver, btn_nextStep);
		reuse.clickUsingJavascriptExecutor(driver,btn_nextStep);
	}
	
	public void scrollDownTillEnd(){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
}

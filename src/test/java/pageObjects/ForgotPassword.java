package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import reusability.Utilities;

public class ForgotPassword {

	WebDriver driver;

	public ForgotPassword(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@name='resetPasswordUsername']")
	public WebElement tbx_phonenumber;

	@FindBy(xpath = "//input[@name='resetPasswordNewPass']")
	public WebElement tbx_newpassword;

	@FindBy(xpath = "//input[@name='resetPasswordNewPassRepeat']")
	public WebElement tbx_repeat_newpassword;

	@FindBy(xpath = "//button[@class='submit btn btn-block btn-sign-in']")
	public WebElement btn_submit;

	@FindBy(xpath = "//a[@class='submit btn btn-block btn-resend']")
	public WebElement btn_back;

	@FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-valid ng-empty']")
	public WebElement tbx_onetimepwd;

	@FindBy(xpath = "//a[@class='btn btn-ctl btn-sign-up ng-scope']")
	public WebElement btn_resetpin;

	@FindBy(xpath = "//a[@class='btn btn-ctl btn-sign-in ng-binding']")
	public WebElement btn_submitpin;

	@FindBy(xpath = "//a[@class='btn btn-ctl btn-sign-in ng-binding']")
	public WebElement btn_ok;

	@FindBy(xpath = "//div[contains(@ng-if,'registrationSuccess || resetPasswordDone')]/p")
	public WebElement msg_forgotPassword_success;

	@FindBy(xpath = "//div[@class='popup-msg-container']//self::div[contains(text(),'Passwords entered did not match. Please enter your new password')]")
	public WebElement msg_forgotPassword_mismatch;

	@FindBy(xpath = "//p[contains(text(),'Invalid Username. Please try again')]")
	public WebElement msg_forgotPassword_invalid_username;

	@FindBy(xpath = "//p[(contains(text(),'OTP is invalid or has expired. Please try again.'))]")
	public WebElement msg_forgotPassword_otp_expired;

	@FindBy(xpath = "//div[@class='popup-msg-container']//self::div[contains(text(),'Password does not meet criteria. Please enter your new password')]")
	public WebElement msg_forgotPassword_notMatchedCriterion;

	public void forgotPassword(String number, String password, String repeatpassword)
			throws InterruptedException, IOException {
		Utilities util = new Utilities();
		tbx_phonenumber.sendKeys(number);
		Thread.sleep(2000);
		tbx_newpassword.sendKeys(password);
		Thread.sleep(2000);
		tbx_repeat_newpassword.sendKeys(repeatpassword);
		Thread.sleep(2000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_submit.click();
		Thread.sleep(2000);
	}

	public void oneTimePin(String otp) throws InterruptedException {
		tbx_onetimepwd.sendKeys(otp);
		Thread.sleep(10000);
		btn_submitpin.click();
	}

}

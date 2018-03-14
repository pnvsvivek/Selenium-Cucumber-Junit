package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import reusability.Utilities;

public class LoginPage {

	WebDriver driver;
	public LoginPage(WebDriver driver) {           
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[contains(text(),'Sign Up')]")
	public WebElement btn_SignUp;
	
	@FindBy(id = "username_field")
	public WebElement txt_userName;
	
	@FindBy(id = "password_field")
	public WebElement txt_passWord;
	
	@FindBy(xpath = "//button[contains(@class,'btn btn-ctl btn-sign-in btn-block')]")
	public WebElement btn_login;
	
	@FindBy(xpath = "//button[@class='btn btn-ctl btn-sign-up btn-block']")
	public WebElement btn_signUp;
	
	@FindBy(xpath = "//a[text()='Forgot Password?']")
	public WebElement link_forgotpassword;
	
	@FindBy(xpath = "//p[@class='generic-p-popup ng-binding ng-scope']")
	public WebElement msg_invalidpassword;
	
	
	public void login(String username, String password) throws InterruptedException, IOException
	{
		Utilities util = new Utilities();
		txt_userName.sendKeys(username);
		Thread.sleep(1000);
		txt_passWord.sendKeys(password);
		Thread.sleep(1000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_login.click();
	}
	
	public void clickSignUp()
	{
		btn_signUp.click();
	}
}

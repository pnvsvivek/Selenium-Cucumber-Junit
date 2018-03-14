package pageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import reusability.Utilities;

public class LandingPage {

	WebDriver driver;
	public LandingPage(WebDriver driver) {           
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}

//	@FindBy(xpath = "//*[@id='betaField']")
	@FindBy(xpath = "//form[@id = 'betaForm']//input[@id='betaField']")
	public WebElement txt_passcode;
	
	@FindBy(xpath = "//button[@class='btn btn-ctl  button_login']")
	public WebElement btn_Enter;
	
	@FindBy(xpath = "//form[@id = 'betaForm']//input[@id='betaField']")
	public List<WebElement> list_txt_passcode;
	
	public void enterPasscode(String passcode) throws InterruptedException, IOException
	{
		Utilities util = new Utilities();
		txt_passcode.sendKeys(passcode);
		Thread.sleep(2000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_Enter.click();
	}
}

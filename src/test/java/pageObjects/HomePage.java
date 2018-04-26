package pageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cucumber.listener.Reporter;

import reusability.ReusableMethods;
import reusability.Utilities;

public class HomePage {

	WebDriver driver;
	Utilities util = new Utilities();

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@id='profileContainer']/li[1]/*[@class='svg-icon']")
	public WebElement icon_Search;

	@FindBy(tagName = "title")
	public List<WebElement> txt_title;

	// @FindBy(css = "span.b-profile__container-link")
	@FindBy(xpath = "//span[@class='b-profile__container-link']//*[@class='svg-icon']")
	// @FindBy(xpath =
	// "//li[contains(@class,'b-profile__container')]//span[contains(@class,'b-profile__container-link')]/*[contains(@class,'svg-icon')]")
	public WebElement icon_Login;

	@FindBy(xpath = "//span[contains(text(),'Hi')]/following::span[1][string-length(text()) > 0]")
	public WebElement icon_user_Greet;

	@FindBy(xpath = "//*[@class = 'b-profile__user-greet-link']")
	public WebElement icon_user_Details;

	@FindBy(xpath = "//span[contains(text(),'My Account')]")
	public WebElement btn_user_Account;

	@FindBy(xpath = "//span[contains(text(),'Sign Out')]")
	public WebElement btn_sign_out;

	@FindBy(xpath = "//span[contains(text(),'Series')]")
	public WebElement link_series;

	@FindBy(xpath = "//div[@class='new-hero-carousel-item-gradient-box']")
	public WebElement link_carousel;

	@FindBy(xpath = "//*[@class = 'b-profile']/li/*[@class='svg-icon']")
	public WebElement btn_Search_circle;

	@FindBy(xpath = "//*[@class = 'search_icon_container']/*[@class='svg-icon']")
	public WebElement btn_Search_circle_After_Text;

	@FindBy(xpath = "//*[contains(@id,'searchButton')]")
	public WebElement btn_Search_Text;

	@FindBy(xpath = "//img[contains(@class,'coverImg cont-list-img')]")
	public WebElement link_content_TVOD;

	@FindBy(xpath = "//span[contains(text(),'Log Out')]//self::span[contains(@class,'b-item-menu__sub-menu-link')]")
	public WebElement btn_signOut;

	@FindBy(xpath = "//img[contains(@class,'ctl-logo')]")
	public WebElement btn_Home;

	@FindBy(xpath = "//h1[contains(@ng-bind,'arraytitle')]")
	public List<WebElement> txt_Tray_Names;

	@FindBy(xpath = "//span[@class='b-profile__container-link']//*[@class='svg-icon']")
	public List<WebElement> list_icon_user_Login;

	@FindBy(xpath = "//h1[@class='purchases-arraylist-heading ng-binding mr-auto']//self::h1[contains(text(),'Most Popular')]")
	public WebElement lbl_mostpopular_rail;

	@FindBy(xpath = "//span[@class='b-item-menu__sub-menu-link']")
	public WebElement link_my_library;

	@FindBy(xpath = "//li[contains(text(),'My Favourites')]")
	public WebElement link_my_favourites;

	@FindBy(xpath = "//div[contains(@class,'modal-buttons-container d-sm-flex')]/a[1]")
	public List<WebElement> btn_Close_After_Register;

	public void clickLoginButton() throws InterruptedException {
		ReusableMethods res = new ReusableMethods();
		if (btn_Close_After_Register.size() != 0) {
			if (btn_Close_After_Register.get(0).isDisplayed()) {
				btn_Close_After_Register.get(0).click();
			}
		}
		Thread.sleep(4000);
		res.wait(driver).until(ExpectedConditions.elementToBeClickable(icon_Login));
		icon_Login.click();
	}

	public void clickMyAccount() throws InterruptedException {
		icon_user_Details.click();
		Thread.sleep(2000);
		btn_user_Account.click();
	}

	public void searchForTheContent(String content) throws InterruptedException, IOException {
		Utilities util = new Utilities();
		ReusableMethods res = new ReusableMethods();
		res.wait(driver).until(ExpectedConditions.elementToBeClickable(btn_Search_circle));
		btn_Search_circle.click();
		Thread.sleep(1000);
		btn_Search_Text.sendKeys(content);
		Thread.sleep(1000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_Search_circle_After_Text.click();
		Thread.sleep(2000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
	}

	public void clickOnContent() throws IOException, InterruptedException {
		link_content_TVOD.click();
		Thread.sleep(2000);
	}

	public void clickMyLibrary(String folder) throws InterruptedException, IOException {
		icon_user_Details.click();
		Thread.sleep(2000);
		link_my_library.click();
		Thread.sleep(2000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		if (folder.equalsIgnoreCase("MyFavourites")) {
			link_my_favourites.click();
			Thread.sleep(2000);
		} else {
			System.out.println("Need to implement for " + folder);
		}

	}

	public void signOut() throws InterruptedException, IOException {
		ReusableMethods res = new ReusableMethods();
		if (btn_Close_After_Register.size() != 0) {
			if (btn_Close_After_Register.get(0).isDisplayed()) {
				btn_Close_After_Register.get(0).click();
			}
		}
		res.wait(driver).until(ExpectedConditions.elementToBeClickable(icon_user_Details));
		icon_user_Details.click();
		res.wait(driver).until(ExpectedConditions.visibilityOf(btn_signOut));
		res.clickUsingJavascriptExecutor(driver, btn_signOut);
		if (btn_Close_After_Register.size() != 0) {
			if (btn_Close_After_Register.get(0).isDisplayed()) {
				btn_Close_After_Register.get(0).click();
			}
		}
		Thread.sleep(4000);
		res.wait(driver).until(ExpectedConditions.visibilityOf(btn_Home));
	}
}

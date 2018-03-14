package pageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cucumber.listener.Reporter;
import com.github.mkolisnyk.cucumber.assertions.LazyAssert;

import reusability.ReusableMethods;
import reusability.Utilities;

public class ContentDetailsPage {

	WebDriver driver;
	ReusableMethods res = new ReusableMethods();
	Utilities util = new Utilities();

	public ContentDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[contains(text(),'Rent')]")
	public WebElement btn_content_Rent;

	@FindBy(xpath = "//a[contains(text(),'Confirm')]//self::a[@class='btn btn-ctl btn-sign-in']")
	public WebElement btn_content_Rent_Confirm;

	@FindBy(xpath = "//span[contains(text(),'Watch')]")
	public WebElement btn_content_Rent_Watch;

	@FindBy(xpath = "//i[contains(@class,'md-icon dp48')]")
	public WebElement btn_content_Rent_Play;

	@FindBy(xpath = "//a[contains(@id,'close_player_detail_btn_VOD')]")
	public WebElement btn_content_Rent_Play_ClosePlayer;

	@FindBy(xpath = "//button[contains(text(),'Close')]")
	public WebElement btn_content_Rent_Play_Close;

	@FindBy(xpath = "//button[@class='btn btn-ctl btn-block ng-scope button_subscribe']")
	public WebElement btn_subscribe;

	@FindBy(xpath = "//button[@class='btn btn-ctl btn-sign-in text-center ng-binding ng-scope']")
	public WebElement btn_1day_subscription;

	@FindBy(xpath = "//button[@class='btn btn-ctl btn-sign-in text-center']")
	public WebElement btn_confirms;

	@FindBy(xpath = "//span[contains(text(),'Sign In')]")
	public WebElement btn_Sign_In_Content_Details;

	@FindBy(xpath = "//span[@class='rate ng-scope']")
	public WebElement link_rate;

	@FindBy(xpath = "//span[contains(text(),'Sort By')]")
	public WebElement link_sort_by;

	@FindBy(xpath = "//span[contains(text(),'Title (Z-A)')]")
	public WebElement link_sort_Z_to_A;

	@FindBy(xpath = "//span[contains(text(),'Title (A-Z)')]")
	public WebElement link_sort_A_to_Z;

	@FindBy(xpath = "//span[contains(text(),'Rating (H-L)')]")
	public WebElement link_sort_H_to_L;

	@FindBy(xpath = "//span[contains(text(),'Rating (L-H)')]")
	public WebElement link_sort_L_to_H;

	@FindBy(xpath = "//span[contains(text(),'No results found')]")
	public WebElement txt_no_result_found;

	@FindBy(xpath = "//span[@class='share ng-scope']")
	public WebElement link_share;

	@FindBy(xpath = "//a[@class='fa fa-facebook ng-isolate-scope']")
	public WebElement link_facebook;

	@FindBy(xpath = "//a[@class='fa fa-twitter ng-isolate-scope']")
	public WebElement link_twitter;

	@FindBy(xpath = "//span[@class='favourite ng-scope']")
	public WebElement link_Favourites;

	@FindBy(xpath = "//input[@class='favourite-tooltip__input form-control ng-pristine ng-valid ng-scope ng-empty ng-touched']")
	public WebElement tbx_favourite;

	@FindBy(xpath = "//button[@class='btn btn-ctl btn-block ng-scope']")
	public WebElement btn_create_playlist;

	@FindBy(xpath = "//p[@class='favourite-tooltip__playlist ng-binding active']")
	public WebElement link_favourite_tooltip;
	
	@FindBy(xpath = "//*[@class='ovd-control-bar-container ovd-control-bar-container-show ovd-bar-wrapper-hide']")
	public WebElement btn_video;
	
	@FindBy(xpath = "//p[@class='b-manage-bookmark__title ng-binding']//following::button[@class='btn btn-ctl btn-sign-in b-manage-bookmark__btn b-manage-bookmark__btn_start ng-scope']")
	public List<WebElement> btn_restart_video;
	
	@FindBy(xpath = "//div[@class='ovd-playPause']//i[@class='md-icon dp48']")
	public WebElement btn_video_Pause;
	
	@FindBy(xpath = "//div[@class='ovd-volume']//i[@class='md-icon dp48']")
	public WebElement btn_video_Volume;

	public void purchaseTVODContentforRent() throws InterruptedException, IOException {
		Utilities util = new Utilities();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_content_Rent.click();
		Thread.sleep(1000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_content_Rent_Confirm.click();
	}

	public void playTheContent() throws InterruptedException, IOException {
		Utilities util = new Utilities();
		ReusableMethods res = new ReusableMethods();
		res.wait(driver).until(ExpectedConditions.visibilityOf(btn_content_Rent_Watch));
		btn_content_Rent_Watch.click();
		res.wait(driver).until(ExpectedConditions.visibilityOf(btn_content_Rent_Play));
		btn_content_Rent_Play.click();
		Thread.sleep(15000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_content_Rent_Play_ClosePlayer.click();
		Thread.sleep(1000);
		btn_content_Rent_Play_Close.click();
	}

	public void rating(WebDriver driver, String star) throws IOException, InterruptedException {
		Actions action = new Actions(driver);
		action.moveToElement(link_rate).perform();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));

		// Adding code to handle rating dynamically
		String xpath = "//input[@id='detail-" + star + "']";

		WebElement rating = driver.findElement(By.xpath(xpath));
		action.moveToElement(rating);
		action.click().perform();
		Thread.sleep(3000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
	}

	public void searchResultsSorting(String sort) throws InterruptedException, IOException {
		link_sort_by.click();
		Thread.sleep(3000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));

		List<WebElement> list = driver.findElements(By.tagName("h5"));
		List<String> all_elements_text = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			all_elements_text.add(list.get(i).getText());
		}
		System.out.println("Before Sorting: " + all_elements_text);

		List<String> after_sorting = new ArrayList<>();

		if (sort.equalsIgnoreCase("Title (Z-A)")) {
			link_sort_Z_to_A.click();
			Thread.sleep(2000);
			Collections.sort(all_elements_text, Collections.reverseOrder());
			Thread.sleep(2000);
			List<WebElement> list1 = driver.findElements(By.tagName("h5"));
			for (int i = 0; i < list1.size(); i++) {
				after_sorting.add(list1.get(i).getText());
			}
			System.out.println("After Sorting: " + after_sorting);
			LazyAssert.assertEquals(true, all_elements_text.equals(after_sorting));
		} else if (sort.equalsIgnoreCase("Title (A-Z)")) {
			link_sort_A_to_Z.click();
			Collections.sort(all_elements_text);
			Thread.sleep(2000);
			List<WebElement> list1 = driver.findElements(By.tagName("h5"));
			for (int i = 0; i < list1.size(); i++) {
				after_sorting.add(list1.get(i).getText());
			}
			System.out.println("After Sorting: " + after_sorting);
			LazyAssert.assertEquals(true, all_elements_text.equals(after_sorting));
		}

		else if (sort.equalsIgnoreCase("Rating (H-L)")) {
			link_sort_H_to_L.click();
		} else if (sort.equalsIgnoreCase("Rating (L-H)")) {
			link_sort_L_to_H.click();
		}

		link_sort_by.click();
		Thread.sleep(3000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
	}

	public void launchTheAPP(String App) throws IOException, InterruptedException {
		String parent_window = driver.getWindowHandle();
		Actions action = new Actions(driver);
		action.moveToElement(link_share).perform();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));

		if (App.equalsIgnoreCase("Facebook")) {
			action.moveToElement(link_facebook);
			action.click().perform();
			Thread.sleep(2000);
		} else if (App.equalsIgnoreCase("twitter")) {
			action.moveToElement(link_twitter);
			action.click().perform();
			Thread.sleep(2000);
		}

		for (String winHandle : driver.getWindowHandles()) {
			// Switch to child window
			driver.switchTo().window(winHandle);
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		}

		if (App.equalsIgnoreCase("Facebook")) {
			LazyAssert.assertEquals("Facebook", driver.getTitle());
			System.out.println("Title: " + driver.getTitle());
		} else if (App.contains("Twitter")) {
			LazyAssert.assertEquals("Share a link on Twitter", driver.getTitle());
			System.out.println("Title: " + driver.getTitle());
		}
		driver.switchTo().window(parent_window);
		Thread.sleep(2000);
	}
	
	public void playTheContentAndPerformSomeActions() throws InterruptedException, IOException {
		Utilities util = new Utilities();
		ReusableMethods res = new ReusableMethods();
		res.wait(driver).until(ExpectedConditions.visibilityOf(btn_content_Rent_Watch));
		btn_content_Rent_Watch.click();
		if(res.verifyWhetherElementIsPresent(btn_restart_video))
		{
			btn_restart_video.get(0).click();
		}
		Thread.sleep(20000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_video.click();
		Thread.sleep(5000);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_video_Volume.click();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_video_Volume.click();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_video_Pause.click();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_video_Pause.click();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		btn_content_Rent_Play_ClosePlayer.click();
		Thread.sleep(1000);
		btn_content_Rent_Play_Close.click();
	}
}

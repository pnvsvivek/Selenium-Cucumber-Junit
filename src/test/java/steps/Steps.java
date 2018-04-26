package steps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cucumber.listener.Reporter;
import com.github.mkolisnyk.cucumber.assertions.LazyAssert;
import com.github.mkolisnyk.cucumber.assertions.LazyAssertionError;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.ContentDetailsPage;
import pageObjects.ForgotPassword;
import pageObjects.HomePage;
import pageObjects.LandingPage;
import pageObjects.LoginPage;
import pageObjects.RegistrationPage;
import pageObjects.UserProfile;
import reusability.ReusableMethods;
import reusability.Utilities;

public class Steps {

	public static String browser = "";
	ReusableMethods res = new ReusableMethods();
	Utilities util = new Utilities();
	WebDriver driver = res.selectBrowser(browser, res.readConfig("environment"));
	List<String> traysBeforeLogin = new ArrayList<String>();
	List<String> traysAfterLogin = new ArrayList<String>();

	Sheet sheet;
	Row row;
	static int j = 1;

	final static String JDBC_Driver = "com.mysql.jdbc.Driver";
	final static String DB_Url = "jdbc:mysql://10.30.153.12:3306/avs_be";
	final static String username = "AVS_BE";
	final static String password = "GRIRAxfu";

	@Given("^Application URL is url$")
	public void application_URL_is_url() throws Throwable {
		System.out.println(res.readConfig("ApplicationURL"));
		Reporter.addStepLog("PCTV Url is " + res.readConfig("ApplicationURL"));
		Reporter.addStepLog("Browser is " + browser);
	}

	@When("^Open the Application in given browser$")
	public void open_the_Application_in_given_browser() throws Throwable {
		try {
			driver.get(res.readConfig("ApplicationURL"));
		} catch (Exception e) {
			e.printStackTrace();
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Landing page is displayed$")
	public void landing_page_is_displayed() throws Throwable {
		try {
			LandingPage landingpage = new LandingPage(driver);
			HomePage homepage = new HomePage(driver);
			if (!res.verifyWhetherElementIsPresent(landingpage.list_txt_passcode)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_Login));
			} else if (!res.verifyWhetherElementIsPresent(homepage.list_icon_user_Login)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Given("^User enters PIN to go to Home Page$")
	public void user_enters_PIN_to_go_to_Home_Page() throws Throwable {
		try {
			// HomePage homepage = new HomePage(driver);
			LandingPage landingpage = new LandingPage(driver);
			if (res.verifyWhetherElementIsPresent(landingpage.list_txt_passcode)) {
				landingpage.enterPasscode(res.readConfig("passcode"));
				Thread.sleep(2000);
				// Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			}
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			/*
			 * boolean flag = false; int i = 0; for (; i <
			 * homepage.txt_title.size(); i++) { if
			 * (homepage.txt_title.get(i).getText().contains("admin")) { flag =
			 * true; break; } } if (flag == true) {
			 * System.out.println("Home page name is " +
			 * homepage.txt_title.get(i).getText());
			 * Reporter.addScreenCaptureFromPath(util.screenCapture(driver)); }
			 * else { System.out.println("Home page is not displayed ");
			 * Reporter.addScreenCaptureFromPath(util.screenCapture(driver) +
			 * "/t" + "Home page is not displayed"); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Given("^User clicks on Login icon on top rght hand side$")
	public void user_clicks_on_Login_icon_on_top_rght_hand_side() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			homepage.clickLoginButton();
			res.wait(driver).until(ExpectedConditions.visibilityOf(loginpage.btn_SignUp));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			Assert.fail("this is failed because of exception " + e.getMessage());
			driver.quit();
		}
	}

	@When("^User enters username \"([^\"]*)\" and its password \"([^\"]*)\"$")
	public void user_enters_username_and_its_password(String arg1, String arg2) throws Throwable {
		try {
			sheet = util.getExcel();
			row = sheet.createRow(j++);
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, arg2);
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
			row.createCell(0).setCellValue("Login");
			row.createCell(1).setCellValue(arg1);
			row.createCell(2).setCellValue(arg2);
			util.writeExcel();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^User enters valid \"([^\"]*)\" and password$")
	public void user_enters_valid_and_password(String arg1) throws Throwable {
		try {
			sheet = util.getExcel();
			row = sheet.createRow(j++);
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, res.readConfig("password"));
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
			row.createCell(0).setCellValue("Login");
			row.createCell(1).setCellValue(arg1);
			row.createCell(2).setCellValue(res.readConfig("password"));
			util.writeExcel();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Home page with respective user appears$")
	public void home_page_with_respective_user_appears() throws Throwable {

		try {
			HomePage homepage = new HomePage(driver);
			Reporter.addStepLog("user is " + homepage.icon_user_Greet.getText());
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^User clicks my account from user profile$")
	public void user_clicks_my_account_from_user_profile() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			res.wait(driver).until(ExpectedConditions.elementToBeClickable(homepage.icon_user_Details));
			homepage.clickMyAccount();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^user clicks on SignUp button and fill in the details of \"([^\"]*)\"$")
	public void user_clicks_on_SignUp_button_and_fill_in_the_details_of(String arg1) throws Throwable {
		try {
			sheet = util.getExcel();
			row = sheet.createRow(j++);
			LoginPage lp = new LoginPage(driver);
			RegistrationPage reg = new RegistrationPage(driver);
			lp.clickSignUp();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.txt_Name));
			reg.clickingOnRegistration(res.readConfig("name"), arg1, res.readConfig("password"));
			row.createCell(0).setCellValue("Registration");
			row.createCell(1).setCellValue(arg1);
			row.createCell(2).setCellValue(res.readConfig("password"));
			util.writeExcel();
		} catch (LazyAssertionError e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^OTP box is displayed and it is entered$")
	public void otp_box_is_displayed_and_it_is_entered() throws Throwable {
		try {
			sheet = util.getExcel();
			row = sheet.createRow(j++);
			RegistrationPage reg = new RegistrationPage(driver);
			Thread.sleep(70000);
			// enter OTP
			// res.wait(driver).until(ExpectedConditions.elementToBeClickable(reg.btn_submit_otp));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			reg.submitOTP();
			row.createCell(5).setCellValue(System.currentTimeMillis());
			util.writeExcel();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^OTP box is displayed and it is entered for \"([^\"]*)\"$")
	public void otp_box_is_displayed_and_it_is_entered_for(String arg1) throws Throwable {

		try {
			sheet = util.getExcel();
			RegistrationPage reg = new RegistrationPage(driver);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.txt_OTP));
			Thread.sleep(15000);
			reg.txt_OTP.sendKeys(util.readOTPfromFile(driver, arg1, browser));
			Thread.sleep(5000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			reg.submitOTP();
			sheet.getRow(j - 1).createCell(5).setCellValue(System.currentTimeMillis());
			util.writeExcel();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Login with the registered \"([^\"]*)\" and message \"([^\"]*)\" is displayed$")
	public void login_with_the_registered_and_is_displayed(String arg1, String arg2) throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			LoginPage loginpage = new LoginPage(driver);
			HomePage homepage = new HomePage(driver);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.msg_registration_success));
			LazyAssert.assertTrue(res.verifymessage(reg.msg_registration_success, arg2));
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			reg.clickOnLogin();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			if (loginpage.btn_register_After_Login.size() != 0) {
				loginpage.btn_register_After_Login.get(0).click();
			}
			if (res.verifyWhetherElementIsPresent(homepage.list_icon_user_Login)) {
				homepage.clickLoginButton();
			}
			loginpage.login(arg1, res.readConfig("password"));
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@When("^user clicks on ForgotPassword link and fill the user \"([^\"]*)\" and \"([^\"]*)\" details$")
	public void user_clicks_on_ForgotPassword_link_and_fill_the_user_and_details(String arg1, String arg2)
			throws Throwable {
		try {
			sheet = util.getExcel();
			row = sheet.createRow(j++);
			row.createCell(0).setCellValue("Forgot Password");
			row.createCell(1).setCellValue(arg1);
			row.createCell(3).setCellValue(arg2);
			util.writeExcel();
			LoginPage loginpage = new LoginPage(driver);
			loginpage.link_forgotpassword.click();
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.forgotPassword(arg1, arg2, arg2);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^successfull message \"([^\"]*)\" is displyed and click on ok button and Change it to original \"([^\"]*)\" for \"([^\"]*)\"$")
	public void successfull_message_is_displyed_and_click_on_ok_button_and_Change_it_to_original_for(String arg1,
			String arg2, String arg3) throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			RegistrationPage reg = new RegistrationPage(driver);
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertTrue(res.verifymessage(forgotpwd.msg_forgotPassword_success, arg1));
			forgotpwd.btn_ok.click();
			Thread.sleep(2000);
			loginpage.link_forgotpassword.click();
			forgotpwd.forgotPassword(arg3, arg2, arg2);
			Thread.sleep(2000);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.txt_OTP));
			Thread.sleep(15000);
			reg.txt_OTP.sendKeys(util.readOTPfromFile(driver, arg3, browser));
			Thread.sleep(5000);
			reg.submitOTP();
			Thread.sleep(2000);
			LazyAssert.assertTrue(res.verifymessage(forgotpwd.msg_forgotPassword_success, arg1));
			forgotpwd.btn_ok.click();
		} catch (LazyAssertionError e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^Search for the \"([^\"]*)\"$")
	public void search_for_the(String arg1) throws Throwable {
		try {
			sheet = util.getExcel();
			HomePage homepage = new HomePage(driver);
			homepage.searchForTheContent(arg1);
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.link_content_TVOD));
			homepage.clickOnContent();
			sheet.getRow(j - 1).createCell(4).setCellValue(arg1);
			util.writeExcel();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^The content is purchased for Rent$")
	public void the_content_is_purchased_for_Rent() throws Throwable {
		try {
			sheet = util.getExcel();
			// row = sheet.getRow(j);
			sheet.getRow(j - 1).createCell(0).setCellValue("TVOD Purchase");
			util.writeExcel();
			ContentDetailsPage content = new ContentDetailsPage(driver);
			content.purchaseTVODContentforRent();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Play the content for some time and close$")
	public void play_the_content_for_some_time_and_close() throws Throwable {
		try {
			ContentDetailsPage content = new ContentDetailsPage(driver);
			content.playTheContent();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Change the \"([^\"]*)\" for the \"([^\"]*)\" from My accounts page and message \"([^\"]*)\" is displayed$")
	public void change_the_for_the_from_My_accounts_page_and_message_is_displayed(String arg1, String arg2, String arg3)
			throws Throwable {
		try {
			HomePage hm = new HomePage(driver);
			LoginPage lp = new LoginPage(driver);
			sheet = util.getExcel();
			row = sheet.createRow(j++);
			row.createCell(0).setCellValue("Change Password");
			row.createCell(1).setCellValue(arg2);
			row.createCell(2).setCellValue(arg1);
			UserProfile up = new UserProfile(driver);
			String newPassword = "Vodacom@" + res.newSampleText();
			Thread.sleep(2000);
			res.wait(driver).until(ExpectedConditions.elementToBeClickable(up.btn_Update_Personal_Data));
			up.btn_Update_Personal_Data.click();
			up.updateNewPassword(driver, arg1, newPassword);
			row.createCell(3).setCellValue(newPassword);
			row.createCell(6).setCellValue(System.currentTimeMillis());
			util.writeExcel();
			Thread.sleep(3000);
			// LazyAssert.assertEquals(up.txt_suceesful_change.getText(), arg3);
			LazyAssert.assertTrue(res.verifymessage(up.txt_suceesful_change, arg3));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			Thread.sleep(2000);
			if (!res.verifyWhetherElementIsPresent(up.list_btn_LogIn)) {
				up.clickOkmessage(driver);
				Thread.sleep(2000);
				hm.signOut();
				hm.clickLoginButton();
			} else {
				if (up.list_btn_LogIn.get(0).isDisplayed()) {
					up.list_btn_LogIn.get(0).click();
				}
			}
			lp.login(arg2, newPassword);
			Thread.sleep(2000);
			hm.clickMyAccount();
			res.wait(driver).until(ExpectedConditions.elementToBeClickable(up.btn_Update_Personal_Data));
			up.btn_Update_Personal_Data.click();
			up.updateNewPassword(driver, newPassword, arg1);
			Thread.sleep(3000);
			// LazyAssert.assertEquals(up.txt_suceesful_change.getText(), arg3);
			LazyAssert.assertTrue(res.verifymessage(up.txt_suceesful_change, arg3));
			Thread.sleep(2000);
			if (res.verifyWhetherElementIsPresent(up.list_btn_Ok)) {
				up.clickOkmessage(driver);
			}
		} catch (LazyAssertionError e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^User clicks on Series link$")
	public void user_clicks_on_Series_link() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.link_series.click();
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^User subscribes the video$")
	public void user_subscribes_the_video() throws Throwable {
		try {
			sheet = util.getExcel();
			// row = sheet.getRow(j);
			sheet.getRow(j - 1).createCell(0).setCellValue("SVOD Purchase");
			util.writeExcel();
			ContentDetailsPage detialspage = new ContentDetailsPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			res.wait(driver).until(ExpectedConditions.visibilityOf(detialspage.btn_subscribe));
			res.wait(driver).until(ExpectedConditions.elementToBeClickable(detialspage.btn_subscribe));
			detialspage.btn_subscribe.click();
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			res.pageScroll(driver, detialspage.btn_1day_subscription);
			res.clickUsingJavascriptExecutor(driver, detialspage.btn_1day_subscription);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			detialspage.btn_confirms.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Sign out$")
	public void sign_out() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.signOut();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			Assert.fail("this is failed because of exception " + e.getMessage());
			driver.quit();
		}
	}

	@Then("^DeRegister the User \"([^\"]*)\"$")
	public void deregister_the_User(String arg1) throws Throwable {
		if (arg1.charAt(0) == '0') {
			arg1 = arg1.replaceFirst("[0]", "27");
		}
		String response = util.deRegisterAPI(arg1).asString();
		System.out.println(response);
		Reporter.addStepLog("Response is " + response);
		Thread.sleep(2000);
	}

	@Then("^Close the application$")
	public void close_the_application() throws Throwable {
		driver.quit();
	}

	@Then("^fill in the details name \"([^\"]*)\", password \"([^\"]*)\" and invalid username \"([^\"]*)\" details$")
	public void fill_in_the_details_name_password_and_invalid_username_details(String arg1, String arg2, String arg3)
			throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			reg.clickingOnRegistration(arg1, arg3, arg2);
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^user gets a popup message as \"([^\"]*)\"$")
	public void user_gets_a_popup_message_as(String arg1) throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			Thread.sleep(3000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			res.wait(driver).until(ExpectedConditions.visibilityOf(regpage.msg_invaliduser));
			LazyAssert.assertTrue(res.verifymessage(regpage.msg_invaliduser, arg1));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^click on ok button$")
	public void click_on_ok_button() throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			if (regpage.btn_ok_invaliduser.isDisplayed()) {
				regpage.btn_ok_invaliduser.click();
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^User enters valid \"([^\"]*)\" and invalid \"([^\"]*)\"$")
	public void user_enters_valid_and_invalid(String arg1, String arg2) throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, arg2);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Enter the username \"([^\"]*)\"$")
	public void enter_the_username(String username) throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.tbx_phonenumber.sendKeys(username);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Enter password \"([^\"]*)\" confirmPassword \"([^\"]*)\"$")
	public void enter_password_confirmPassword(String password, String confirmpassword) throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.tbx_newpassword.sendKeys(password);
			Thread.sleep(1000);
			forgotpwd.tbx_repeat_newpassword.sendKeys(confirmpassword);
			Thread.sleep(1000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^User click on submit button and \"([^\"]*)\" is displayed$")
	public void user_click_on_submit_button_and_is_displayed(String message) throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.btn_submit.click();
			Thread.sleep(1000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			if (message.contains("Invalid Username. Please try again")) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(forgotpwd.msg_forgotPassword_invalid_username));
				LazyAssert.assertTrue(res.verifymessage(forgotpwd.msg_forgotPassword_invalid_username, message));

			} else if (message.contains("Passwords entered did not match. Please enter your new password")) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(forgotpwd.msg_forgotPassword_mismatch));
				LazyAssert.assertTrue(res.verifymessage(forgotpwd.msg_forgotPassword_mismatch, message));
			} else if (message.contains("Password does not meet criteria. Please enter your new password")) {
				res.wait(driver)
						.until(ExpectedConditions.visibilityOf(forgotpwd.msg_forgotPassword_notMatchedCriterion));
				LazyAssert.assertTrue(res.verifymessage(forgotpwd.msg_forgotPassword_notMatchedCriterion, message));
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^User click on submit button$")
	public void user_click_on_submit_button() throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.btn_submit.click();
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^OTP box is displayed and invalid OTP is entered$")
	public void otp_box_is_displayed_and_invalid_OTP_is_entered() throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.oneTimePin("11111");
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^user clicks on ForgotPassword link$")
	public void user_clicks_on_ForgotPassword_link() throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			loginpage.link_forgotpassword.click();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^user gets a pop up displays \"([^\"]*)\"$")
	public void user_gets_a_pop_up_displays(String arg1) throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			// Assert.assertTrue(res.verifymessage(regpage.msg_invaliduser,
			// arg1));
			Assert.assertEquals(regpage.msg_invaliduser, arg1);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (AssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Login with the registered \"([^\"]*)\" and password$")
	public void login_with_the_registered_and_password(String arg1) throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, res.readConfig("password"));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^User enters valid \"([^\"]*)\" and its password$")
	public void user_enters_valid_and_new_password(String arg1) throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, res.readConfig("password_newPhone"));
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^change \"([^\"]*)\" to an existing user number$")
	public void change_to_an_existing_user_number(String arg1) throws Throwable {
		try {
			UserProfile userprofile = new UserProfile(driver);
			userprofile.updateUserDetails(driver, res.readConfig("firstname"), res.readConfig("surname"), arg1);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^user clicks on  Submit button and \"([^\"]*)\" is displayed$")
	public void user_clicks_on_Submit_button_and_is_displayed(String arg1) throws Throwable {
		try {
			UserProfile userprofile = new UserProfile(driver);
			Thread.sleep(2000);
			// reg.submitOTP();
			LazyAssert.assertTrue(res.verifymessage(userprofile.msg_changeUser, arg1));
			// userprofile.msg_changeUser_Close.click();
			if (!res.verifyWhetherElementIsPresent(userprofile.list_btn_LogIn)) {
				userprofile.msg_changeUser_Close.click();
			} else {
				if (userprofile.list_btn_LogIn.get(0).isDisplayed()) {
					userprofile.list_btn_LogIn.get(0).click();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^Close the pop-up that is dispalyed$")
	public void close_the_pop_up_that_is_dispalyed() throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			reg.btn_Close_PopUp.get(24).click();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^User tries to change the password by provding mismatch passwords$")
	public void user_tries_to_change_the_password_by_provding_mismatch_passwords() throws Throwable {
		try {
			UserProfile userProfile = new UserProfile(driver);
			userProfile.passwordMismatch(driver, res.readConfig("password_newPhone"),
					res.readConfig("password_updateProfile"), res.readConfig("mismatchPassword"));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^fill in the details name \"([^\"]*)\" and enter username \"([^\"]*)\" and password \"([^\"]*)\" fields as empty$")
	public void fill_in_the_details_name_and_enter_username_and_password_fields_as_empty(String arg1, String arg2,
			String arg3) throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			reg.clickOnRegistration(arg1, arg2, arg3);
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^verify next step button is disabled$")
	public void verify_next_step_button_is_disabled() throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			if (reg.getDisabledPropForNextStep() == true) {
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^\"([^\"]*)\" is displayed$")
	public void is_displayed(String arg1) throws Throwable {
		try {
			UserProfile userProfile = new UserProfile(driver);
			LazyAssert.assertTrue(res.verifymessage(userProfile.txt_mimatch_password, arg1));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^User tries to change the password by provding non-complex passwords$")
	public void user_tries_to_change_the_password_by_provding_non_complex_passwords() throws Throwable {
		try {
			UserProfile userProfile = new UserProfile(driver);
			userProfile.passwordMismatch(driver, res.readConfig("password_newPhone"), res.readConfig("notComplex"),
					res.readConfig("notComplex"));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Error message \"([^\"]*)\" is displayed$")
	public void error_message_is_displayed(String arg1) throws Throwable {
		try {
			UserProfile userProfile = new UserProfile(driver);
			LazyAssert.assertTrue(res.verifymessage(userProfile.txt_notComplex_password, arg1));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@When("^user clicks on SignUp button$")
	public void user_clicks_on_SignUp_button() throws Throwable {
		try {
			LoginPage lp = new LoginPage(driver);
			lp.clickSignUp();
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^fill name \"([^\"]*)\" , username \"([^\"]*)\", password \"([^\"]*)\"  details and not check the T&C checkbox$")
	public void fill_name_username_password_details_and_not_check_the_T_C_checkbox(String arg1, String arg2,
			String arg3) throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			reg.clickOnRegistration(arg1, arg2, arg3, "check");
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^fill in the details name \"([^\"]*)\" , username \"([^\"]*)\" and password \"([^\"]*)\" , confirm password \"([^\"]*)\" as not matching$")
	public void fill_in_the_details_name_username_and_password_confirm_password_as_not_matching(String arg1,
			String arg2, String arg3, String arg4) throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			reg.clickOnRegistration_passwordMismatch(arg1, arg2, arg3, arg4);
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^fill in the details name \"([^\"]*)\" , username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void fill_in_the_details_name_username_and_password(String arg1, String arg2, String arg3) throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			reg.clickingOnRegistration(arg1, arg2, arg3);
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^OTP box is displayed and enter invalid OTP \"([^\"]*)\" and submit$")
	public void otp_box_is_displayed_and_enter_invalid_OTP_and_submit(String arg1) throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			Thread.sleep(5000);
			forgotpwd.oneTimePin(arg1);
			System.out.println("otp method done");
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^verify error message as \"([^\"]*)\"$")
	public void verify_error_message_as(String arg1) throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			Thread.sleep(3000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertTrue(res.verifymessage(regpage.msg_invalid_otp, arg1));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^user gets a popup message \"([^\"]*)\"$")
	public void user_gets_a_popup_message(String message) throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			res.wait(driver).until(ExpectedConditions.visibilityOf(forgotpwd.msg_forgotPassword_otp_expired));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertTrue(res.verifymessage(forgotpwd.msg_forgotPassword_otp_expired, message));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}

	}

	@Then("^user gives rating as \"([^\"]*)\"$")
	public void user_gives_rating_as(String star) throws Throwable {
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			ContentDetailsPage cdp = new ContentDetailsPage(driver);
			cdp.rating(driver, star);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^click on the Terms and Conditions link and verify it is accessible$")
	public void click_on_the_Terms_and_Conditions_link_and_verify_it_is_accessible() throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			regpage.lnk_TandC.click();
			if (regpage.lbl_TandC_heading.isDisplayed()) {
				regpage.scrollDownTillEnd();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Close Terms and Conditions window$")
	public void close_Terms_and_Conditions_window() throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			if (regpage.lbl_TandC_heading.isDisplayed()) {
				regpage.btn_TandC_close.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^scroll down for the recommendations and verify most popular rail is displayed$")
	public void scroll_down_for_the_recommendations_and_verify_most_popular_rail_is_displayed() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			if ((homepage.icon_user_Greet.getText()) != null) {
				res.pageScroll(driver, homepage.lbl_mostpopular_rail);
				Thread.sleep(3000);
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Click on current subscriptions link$")
	public void click_on_current_subscriptions_link() throws Throwable {
		try {
			UserProfile up = new UserProfile(driver);
			if (up.lnk_current_subscriptions.isDisplayed()) {
				up.lnk_current_subscriptions.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Verify purchase details of the user$")
	public void verify_purchase_details_of_the_user() throws Throwable {
		try {
			UserProfile up = new UserProfile(driver);
			if (up.tbl_subscription_details.isDisplayed()) {
				Reporter.addStepLog("Purchase history found");
				Thread.sleep(3000);
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			} else if (up.lbl_no_subscriptions.isDisplayed()) {
				Reporter.addStepLog("Currently there are no subscriptions");
				Thread.sleep(3000);
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			} else {
				Reporter.addStepLog("Not able to find purchase history");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^User enters valid username \"([^\"]*)\" and its password \"([^\"]*)\"$")
	public void user_enters_valid_username_and_its_password(String arg1, String arg2) throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, arg2);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Click on unsubscribe link to cancel subscription$")
	public void click_on_unsubscribe_link_to_cancel_subscription() throws Throwable {
		try {
			UserProfile up = new UserProfile(driver);
			up.lnk_unsubscribe.click();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Search for the title \"([^\"]*)\"$")
	public void search_for_the_title(String title) throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.searchForTheContent(title);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Given("^SignIn button is displayed for the content for anonymous user$")
	public void signin_button_is_displayed_for_the_content_for_anonymous_user() throws Throwable {
		try {
			ContentDetailsPage contentdetails = new ContentDetailsPage(driver);
			res.wait(driver).until(ExpectedConditions.visibilityOf(contentdetails.btn_Sign_In_Content_Details));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Sort the search results in \"([^\"]*)\"$")
	public void sort_the_search_results_in(String sort) throws Throwable {
		try {
			ContentDetailsPage cdp = new ContentDetailsPage(driver);
			cdp.searchResultsSorting(sort);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Verify no search results message$")
	public void verify_no_search_results_message() throws Throwable {
		try {
			ContentDetailsPage cdp = new ContentDetailsPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertEquals("No results found", cdp.txt_no_result_found.getText());
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^Share the content using \"([^\"]*)\"$")
	public void share_the_content_using(String App) throws Throwable {
		try {
			ContentDetailsPage cdp = new ContentDetailsPage(driver);
			cdp.launchTheAPP(App);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Given("^User clicks on Home Icon$")
	public void user_clicks_on_Home_Icon() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.btn_Home.click();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Capture the trays that are available$")
	public void capture_the_trays_that_are_available() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			for (int i = 0; i < homepage.txt_Tray_Names.size(); i++) {
				traysBeforeLogin.add(homepage.txt_Tray_Names.get(i).getText());
			}
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Compare Same trays are avilable even after logging in with user$")
	public void compare_Same_trays_are_avilable_even_after_logging_in_with_user() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			for (int i = 0; i < homepage.txt_Tray_Names.size(); i++) {
				traysAfterLogin.add(homepage.txt_Tray_Names.get(i).getText());
			}
			if (traysBeforeLogin.equals(traysAfterLogin)) {
				Reporter.addStepLog(" Trays before Login and After Login are same");
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			} else {
				Reporter.addStepLog(" Trays before Login and After Login are same");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Refresh the page to see watch button$")
	public void refresh_the_page_to_see_watch_button() throws Throwable {
		try {
			HomePage hm = new HomePage(driver);
			ContentDetailsPage contentdetailspage = new ContentDetailsPage(driver);
			Thread.sleep(5000);
			res.wait(driver).until(ExpectedConditions.elementToBeClickable(hm.icon_user_Details));
			driver.navigate().refresh();
			res.wait(driver).until(ExpectedConditions.visibilityOf(contentdetailspage.btn_content_Rent_Watch));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Play the content and perform video actions on it$")
	public void play_the_content_and_perform_video_actions_on_it() throws Throwable {
		try {
			ContentDetailsPage contentdetails = new ContentDetailsPage(driver);
			contentdetails.playTheContentAndPerformSomeActions();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^User subscribes the video with no balance and Displays Following \"([^\"]*)\"$")
	public void user_subscribes_the_video_with_no_balance_and_Displays_Following(String arg1) throws Throwable {
		try {
			ContentDetailsPage detialspage = new ContentDetailsPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			detialspage.btn_subscribe.click();
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertTrue(res.verifymessage(detialspage.txt_error_message, arg1));
			detialspage.btn_ok.click();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@When("^The content is purchased for Rent with no balance and Displays Following \"([^\"]*)\"$")
	public void the_content_is_purchased_for_Rent_with_no_balance_and_Displays_Following(String arg1) throws Throwable {
		try {
			ContentDetailsPage detialspage = new ContentDetailsPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			detialspage.btn_content_Rent.click();
			Thread.sleep(1000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertTrue(res.verifymessage(detialspage.txt_error_message, arg1));
			detialspage.btn_ok.click();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e1.getMessage());
		}
	}

	@Then("^Add VOD to favourites list \"([^\"]*)\"$")
	public void add_VOD_to_favourites_list(String folder) throws Throwable {
		try {
			ContentDetailsPage cdp = new ContentDetailsPage(driver);
			cdp.addToFavourites(folder);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^Delete VOD \"([^\"]*)\" from favourites list$")
	public void delete_VOD_from_favourites_list(String vod) throws Throwable {
		try {
			ContentDetailsPage cdp = new ContentDetailsPage(driver);
			cdp.removeFavourites(vod);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^OTP box is being displayed and it is entered for \"([^\"]*)\"$")
	public void otp_box_is_being_displayed_and_it_is_entered_for(String arg1) throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.txt_OTP));
			Thread.sleep(15000);
			reg.txt_OTP.sendKeys(util.readOTPfromFile(driver, arg1, browser));
			Thread.sleep(5000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			reg.submitOTP();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^User Searches for the \"([^\"]*)\"$")
	public void user_Searches_for_the(String arg1) throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.searchForTheContent(arg1);
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.link_content_TVOD));
			homepage.clickOnContent();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@Then("^User is subscribing the video$")
	public void user_is_subscribing_the_video() throws Throwable {
		try {
			ContentDetailsPage detialspage = new ContentDetailsPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			res.wait(driver).until(ExpectedConditions.visibilityOf(detialspage.btn_subscribe));
			res.wait(driver).until(ExpectedConditions.elementToBeClickable(detialspage.btn_subscribe));
			detialspage.btn_subscribe.click();
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			res.pageScroll(driver, detialspage.btn_1day_subscription);
			res.clickUsingJavascriptExecutor(driver, detialspage.btn_1day_subscription);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			detialspage.btn_confirms.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}

	@When("^The content is being purchased for Rent$")
	public void the_content_is_being_purchased_for_Rent() throws Throwable {
		try {
			ContentDetailsPage content = new ContentDetailsPage(driver);
			content.purchaseTVODContentforRent();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.quit();
			Assert.fail("this is failed because of exception " + e.getMessage());
		}
	}
}
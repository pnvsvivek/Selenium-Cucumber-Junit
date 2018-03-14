package steps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.Status;
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

	public static String browser="";
	ReusableMethods res = new ReusableMethods();
	Utilities util = new Utilities();
	
	WebDriver driver = res.selectBrowser(browser);
	
	List<String> traysBeforeLogin = new ArrayList<String>();
	List<String> traysAfterLogin = new ArrayList<String>();

	final static String JDBC_Driver = "com.mysql.jdbc.Driver";
	final static String DB_Url = "jdbc:mysql://10.30.153.12:3306/avs_be";
	final static String username = "AVS_BE";
	final static String password = "GRIRAxfu";

	@Given("^Application URL is url$")
	public void application_URL_is_url() throws Throwable {
		System.out.println(res.readConfig("ApplicationURL"));
	}

	@When("^Open the Application in given browser$")
	public void open_the_Application_in_given_browser() throws Throwable {
		driver.get(res.readConfig("ApplicationURL"));
	}

	@Then("^Landing page is displayed$")
	public void landing_page_is_displayed() throws Throwable {
		try {
			LandingPage landingpage = new LandingPage(driver);
			HomePage homepage = new HomePage(driver);
			if (!res.verifyWhetherElementIsPresent(landingpage.list_txt_passcode)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_Login));
				Thread.sleep(10000);
				res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_Login));
			} else if (!res.verifyWhetherElementIsPresent(homepage.list_icon_user_Login)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
				Thread.sleep(10000);
				res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
				// if(!homepage.icon_user_Greet.isDisplayed())
				{
					// res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
					// Thread.sleep(15000);
					// res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@Given("^User enters PIN to go to Home Page$")
	public void user_enters_PIN_to_go_to_Home_Page() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			LandingPage landingpage = new LandingPage(driver);
			if (res.verifyWhetherElementIsPresent(landingpage.list_txt_passcode)) {
				landingpage.enterPasscode(res.readConfig("passcode"));
			}
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			boolean flag = false;
			int i = 0;
			for (; i < homepage.txt_title.size(); i++) {
				if (homepage.txt_title.get(i).getText().contains("admin")) {
					flag = true;
					break;
				}
			}
			if (flag == true) {
				System.out.println("Home page name is " + homepage.txt_title.get(i).getText());
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			} else {
				System.out.println("Home page is not displayed ");
				Reporter.addScreenCaptureFromPath(util.screenCapture(driver) + "/t" + "Home page is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@Given("^User clicks on Login icon on top rght hand side$")
	public void user_clicks_on_Login_icon_on_top_rght_hand_side() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			Thread.sleep(5000);
			homepage.clickLoginButton();
			res.wait(driver).until(ExpectedConditions.visibilityOf(loginpage.btn_SignUp));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@When("^User enters valid \"([^\"]*)\" and password$")
	public void user_enters_valid_and_password(String arg1) throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, res.readConfig("password"));
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
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
			driver.close();
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
			driver.close();
		}
	}

	@When("^user clicks on SignUp button and fill in the details of \"([^\"]*)\"$")
	public void user_clicks_on_SignUp_button_and_fill_in_the_details_of(String arg1) throws Throwable {
		try {
			LoginPage lp = new LoginPage(driver);
			RegistrationPage reg = new RegistrationPage(driver);
			lp.clickSignUp();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.txt_Name));
			reg.clickingOnRegistration(res.readConfig("name"), arg1, res.readConfig("password"));
		} catch (LazyAssertionError e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@Then("^OTP box is displayed and it is entered$")
	public void otp_box_is_displayed_and_it_is_entered() throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			Thread.sleep(70000);
			// enter OTP
			// res.wait(driver).until(ExpectedConditions.elementToBeClickable(reg.btn_submit_otp));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			reg.submitOTP();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
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
			loginpage.login(arg1, res.readConfig("password"));
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@When("^user clicks on ForgotPassword link and fill the user \"([^\"]*)\"details$")
	public void user_clicks_on_ForgotPassword_link_and_fill_the_user_details(String arg1) throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			loginpage.link_forgotpassword.click();
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.forgotPassword(arg1, res.readConfig("newpassword"), res.readConfig("newpassword"));
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@Then("^successfull message \"([^\"]*)\" is displyed and click on ok button$")
	public void successfull_message_is_displyed_and_click_on_ok_button(String arg1) throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertTrue(res.verifymessage(forgotpwd.msg_forgotPassword_success, arg1));
			res.setConfigValue("password", res.readConfig("newpassword"));
			forgotpwd.btn_ok.click();
		} catch (LazyAssertionError e) {
			e.printStackTrace();
			driver.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			driver.close();
		}
	}

	@Then("^Search for the \"([^\"]*)\"$")
	public void search_for_the(String arg1) throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.searchForTheContent(arg1);
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.link_content_TVOD));
			homepage.clickOnContent();
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@When("^The content is purchased for Rent$")
	public void the_content_is_purchased_for_Rent() throws Throwable {
		try {
			ContentDetailsPage content = new ContentDetailsPage(driver);
			content.purchaseTVODContentforRent();
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@Then("^Play the content for some time and close$")
	public void play_the_content_for_some_time_and_close() throws Throwable {
		try {
			ContentDetailsPage content = new ContentDetailsPage(driver);
			content.playTheContent();
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@Then("^Change the \"([^\"]*)\" from My accounts page and message \"([^\"]*)\" is displayed$")
	public void change_the_from_My_accounts_page_and_message_is_displayed(String arg1, String arg2) throws Throwable {
		try {
			UserProfile up = new UserProfile(driver);
			String newPassword = "Vodacom" + res.newSampleText();
			Thread.sleep(2000);
			System.out.println("new password " + res.readConfig(arg1));
			up.updateNewPassword(driver, res.readConfig(arg1), newPassword);
			Thread.sleep(2000);
			LazyAssert.assertEquals(up.txt_suceesful_change.getText(), arg2);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			Thread.sleep(2000);
			up.clickOkmessage(driver);
			res.setConfigValue(arg1, newPassword);
		} catch (LazyAssertionError e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
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
			driver.close();
		}
	}

	@Then("^User subscribes the video$")
	public void user_subscribes_the_video() throws Throwable {
		try {
			ContentDetailsPage detialspage = new ContentDetailsPage(driver);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			detialspage.btn_subscribe.click();
			Thread.sleep(2000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			detialspage.btn_1day_subscription.click();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			detialspage.btn_confirms.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@Then("^Sign out$")
	public void sign_out() throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.signOut();
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@Then("^DeRegister the User \"([^\"]*)\"$")
	public void deregister_the_User(String arg1) throws Throwable {
		String response = util.deRegisterAPI(arg1).asString();
		System.out.println(response);
		Reporter.addStepLog("Response is " + response);
		Thread.sleep(4000);
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
			reg.clickOnRegistration(arg1, arg3, arg2);
		} catch (Exception e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@Then("^user gets a popup message as \"([^\"]*)\"$")
	public void user_gets_a_popup_message_as(String arg1) throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			Thread.sleep(3000);
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			LazyAssert.assertTrue(res.verifymessage(regpage.msg_invaliduser, arg1));
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
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
			driver.close();
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
			driver.close();
		}
	}

	@Then("^Enter the username \"([^\"]*)\"$")
	public void enter_the_username(String username) throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.tbx_phonenumber.sendKeys(username);
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
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
			driver.close();
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
			}

		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
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
			driver.close();
		}
	}

	@Then("^OTP box is displayed and invalid OTP is entered$")
	public void otp_box_is_displayed_and_invalid_OTP_is_entered() throws Throwable {
		try {
			ForgotPassword forgotpwd = new ForgotPassword(driver);
			forgotpwd.oneTimePin("11111");
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@When("^user clicks on ForgotPassword link$")
	public void user_clicks_on_ForgotPassword_link() throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			loginpage.link_forgotpassword.click();
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@Then("^user gets a pop up displays \"([^\"]*)\"$")
	public void user_gets_a_pop_up_displays(String arg1) throws Throwable {
		try {
			RegistrationPage regpage = new RegistrationPage(driver);
			LazyAssert.assertTrue(res.verifymessage(regpage.msg_invaliduser, arg1));
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
	}

	@Then("^Login with the registered \"([^\"]*)\" and password$")
	public void login_with_the_registered_and_password(String arg1) throws Throwable {
		try {
			LoginPage loginpage = new LoginPage(driver);
			loginpage.login(arg1, res.readConfig("password"));
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
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
			driver.close();
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
			driver.close();
		}
	}

	@When("^user clicks on  Submit button and \"([^\"]*)\" is displayed$")
	public void user_clicks_on_Submit_button_and_is_displayed(String arg1) throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			Thread.sleep(2000);
			reg.submitOTP();
			Thread.sleep(1000);
			LazyAssert.assertTrue(res.verifymessage(reg.msg_invaliduser, arg1));
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			driver.close();
		}
	}

	@Then("^Close the pop-up that is dispalyed$")
	public void close_the_pop_up_that_is_dispalyed() throws Throwable {
		try {
			RegistrationPage reg = new RegistrationPage(driver);
			reg.btn_Close_PopUp.click();
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
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
			driver.close();
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
			driver.close();
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
			driver.close();
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
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
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
			driver.close();
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
			driver.close();
		} catch (LazyAssertionError e1) {
			e1.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
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
			driver.close();
		}

	}

	@Then("^user gives rating as \"([^\"]*)\"$")
	public void user_gives_rating_as(String star) throws Throwable {
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		ContentDetailsPage cdp = new ContentDetailsPage(driver);
		cdp.rating(driver, star);
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
			driver.close();
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
			driver.close();
		}
	}

	@Then("^Search for the title \"([^\"]*)\"$")
	public void search_for_the_title(String title) throws Throwable {
		try {
			HomePage homepage = new HomePage(driver);
			homepage.searchForTheContent(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Given("^SignIn button is displayed for the content for anonymous user$")
	public void signin_button_is_displayed_for_the_content_for_anonymous_user() throws Throwable {
		try
		{
		ContentDetailsPage contentdetails = new ContentDetailsPage(driver);
		res.wait(driver).until(ExpectedConditions.visibilityOf(contentdetails.btn_Sign_In_Content_Details));
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@Then("^Sort the search results in \"([^\"]*)\"$")
	public void sort_the_search_results_in(String sort) throws Throwable {
		ContentDetailsPage cdp = new ContentDetailsPage(driver);
		cdp.searchResultsSorting(sort);
	}

	@Then("^Verify no search results message$")
	public void verify_no_search_results_message() throws Throwable {
		ContentDetailsPage cdp = new ContentDetailsPage(driver);
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		LazyAssert.assertEquals("No results found", cdp.txt_no_result_found.getText());
	}
	
	@Then("^Share the content using \"([^\"]*)\"$")
	public void share_the_content_using(String App) throws Throwable {
	    ContentDetailsPage cdp = new ContentDetailsPage(driver);
	    cdp.launchTheAPP(App);
	}

	@Given("^User clicks on Home Icon$")
	public void user_clicks_on_Home_Icon() throws Throwable {
		try
		{
		HomePage homepage = new HomePage(driver);
		homepage.btn_Home.click();
		Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@Then("^Capture the trays that are available$")
	public void capture_the_trays_that_are_available() throws Throwable {
		try
		{
	    HomePage homepage = new HomePage(driver);
	    for(int i=0;i<homepage.txt_Tray_Names.size();i++)
	    {
	    	traysBeforeLogin.add(homepage.txt_Tray_Names.get(i).getText());
	    }
	    Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}

	@Then("^Compare Same trays are avilable even after logging in with user$")
	public void compare_Same_trays_are_avilable_even_after_logging_in_with_user() throws Throwable {
		try
		{
		HomePage homepage = new HomePage(driver);
	    for(int i=0;i<homepage.txt_Tray_Names.size();i++)
	    {
	    	traysAfterLogin.add(homepage.txt_Tray_Names.get(i).getText());
	    }
	    if(traysBeforeLogin.equals(traysAfterLogin))
	    {
	    	Reporter.addStepLog(Status.PASS+" Trays before Login and After Login are same");
	    	Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
	    }
	    else
	    {
	    	Reporter.addStepLog(Status.FAIL+" Trays before Login and After Login are same");
	    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(util.screenCapture(driver));
			driver.close();
		}
	}
	
	@Then("^Refresh the page to see watch button$")
	public void refresh_the_page_to_see_watch_button() throws Throwable {
		try
		{
		ContentDetailsPage contentdetailspage = new ContentDetailsPage(driver);
	    Thread.sleep(4000);
	    driver.navigate().refresh();
	    res.wait(driver).until(ExpectedConditions.visibilityOf(contentdetailspage.btn_content_Rent_Watch));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			driver.close();
		}
	}
	
	@Then("^Play the content and perform video actions on it$")
	public void play_the_content_and_perform_video_actions_on_it() throws Throwable {
		try
		{
		ContentDetailsPage contentdetails = new ContentDetailsPage(driver);
		contentdetails.playTheContentAndPerformSomeActions();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			driver.close();
		}
	}
}
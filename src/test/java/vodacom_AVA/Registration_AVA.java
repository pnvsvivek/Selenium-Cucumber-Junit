package vodacom_AVA;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.github.mkolisnyk.cucumber.assertions.LazyAssert;

import pageObjects.HomePage;
import pageObjects.LandingPage;
import pageObjects.LoginPage;
import pageObjects.RegistrationPage;
import reusability.ReusableMethods;
import reusability.Utilities;

public class Registration_AVA {

	ReusableMethods res = new ReusableMethods();
	Utilities util = new Utilities();
	WebDriver driver;
	static File file = null;
	List<String> list = new ArrayList<String>();

	public static void main(String args[]) throws IOException, InterruptedException, MessagingException {
		Registration_AVA registration = new Registration_AVA();
		ReusableMethods res = new ReusableMethods();
		registration.registerUser(res.readConfig("username_Register_AVA"));
	}

	public void registerUser(String phoneNumber) throws IOException, InterruptedException, MessagingException {
		Registration_AVA registration = new Registration_AVA();
		try {
			String step1 = "";
			String step2 = "";
			String step3 = "";
			file = new File("AVA/ava_requisite_file_Registration.txt");
			if (!file.exists()) {
				FileUtils.touch(file);
			}
			driver = res.selectBrowser("chrome", "local");
			driver.get(res.readConfig("ApplicationURL"));
			LandingPage landingpage = new LandingPage(driver);
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			if (!res.verifyWhetherElementIsPresent(landingpage.list_txt_passcode)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_Login));
				Thread.sleep(10000);
				res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_Login));
			} else if (!res.verifyWhetherElementIsPresent(homepage.list_icon_user_Login)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
				Thread.sleep(10000);
				res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
			}
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
			} else {
				System.out.println("Home page is not displayed ");
			}
			homepage.clickLoginButton();
			res.wait(driver).until(ExpectedConditions.visibilityOf(loginpage.btn_SignUp));
			RegistrationPage reg = new RegistrationPage(driver);
			loginpage.clickSignUp();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.txt_Name));
			reg.clickingOnRegistration(res.readConfig("name"), phoneNumber, res.readConfig("password"));
			// Reading OTP
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.txt_OTP));
			// reg.txt_OTP.sendKeys(util.readOTPfromEmail(phoneNumber));
			// Enter OTP
			Thread.sleep(70000);
			reg.submitOTP();
			Thread.sleep(3000);
			res.wait(driver).until(ExpectedConditions.visibilityOf(reg.msg_registration_success));
			LazyAssert.assertTrue(
					res.verifymessage(reg.msg_registration_success, "You have successfully registered your account"));
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			step1 += "ScenarioName=Registration||step=join||" + "username=" + phoneNumber + "||" + "ip="
					+ registration.getIPAddressOfHost() + "||"
					+ registration.splitString(driver.manage().getCookieNamed("avs_cookie").toString());
			list.add(step1);
			Thread.sleep(3000);
			reg.clickOnLogin();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			loginpage.login(phoneNumber, res.readConfig("password"));
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
			String cookie = registration.splitString(driver.manage().getCookieNamed("avs_cookie").toString());
			step2 += "ScenarioName=Registration||step=login||" + "username=" + phoneNumber + "||"
					+ registration.splitString(driver.manage().getCookieNamed("sessionId").toString()) + "||" + "ip="
					+ registration.getIPAddressOfHost() + "||" + cookie;
			list.add(step2);
			homepage.signOut();
			step3 += "ScenarioName=Registration||step=logout||" + "username=" + phoneNumber + "||"
					+ registration.splitString(driver.manage().getCookieNamed("sessionId").toString()) + "||" + "ip="
					+ registration.getIPAddressOfHost() + "||" + cookie;
			list.add(step3);
			Thread.sleep(4000);
			FileUtils.writeLines(file, list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}

	public String getIPAddressOfHost() {
		String ipAddress = null;
		try {
			InetAddress ipAddr = InetAddress.getLocalHost();
			ipAddress = ipAddr.getHostAddress();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
		return ipAddress;
	}

	public String splitString(String name) {
		String[] splitter = name.split(";");
		return splitter[0];
	}

}
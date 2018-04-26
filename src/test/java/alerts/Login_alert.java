package alerts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pageObjects.HomePage;
import pageObjects.LandingPage;
import pageObjects.LoginPage;
import reusability.ReusableMethods;
import reusability.Utilities;

public class Login_alert {

	ReusableMethods res = new ReusableMethods();
	Utilities util = new Utilities();
	WebDriver driver;

	public static void main(String[] args) throws IOException {
		ReusableMethods res = new ReusableMethods();
		Login_alert mis = new Login_alert();
		mis.login_PCTV(res.readConfig("username_Alert_login"), res.readConfig("password_Alert_login"));
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public void login_PCTV(String phoneNumber, String password) throws IOException {
		String imageTime = null;
		Login_alert mis = new Login_alert();
		String result = null;
		String directory = System.getProperty("user.dir");
		String extension = ".png";
		File dir = new File(directory);
		File[] filesToDelete = dir.listFiles();
		for (int i = 0; i < filesToDelete.length; i++) {
			if (filesToDelete[i].getName().endsWith(extension)) {
				filesToDelete[i].delete();
			}
		}
		long timeTaken = 0;
		long startTime = System.currentTimeMillis();
		String pageName = "Login";
		File file = new File("Login.json");
		if (!file.exists()) {
			FileUtils.touch(file);
		}
		FileWriter fileWriter = new FileWriter(file);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageName", pageName);
		try {
			driver = res.selectBrowser("chrome", "local");
			driver.get(res.readConfig("ApplicationURL"));
			LandingPage landingpage = new LandingPage(driver);
			HomePage homepage = new HomePage(driver);
			LoginPage loginpage = new LoginPage(driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if (!res.verifyWhetherElementIsPresent(landingpage.list_txt_passcode)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_Login));
			} else if (!res.verifyWhetherElementIsPresent(homepage.list_icon_user_Login)) {
				res.wait(driver).until(ExpectedConditions.visibilityOf(landingpage.txt_passcode));
			}
			landingpage.txt_passcode.sendKeys(res.readConfig("passcode"));
			Thread.sleep(2000);
			landingpage.btn_Enter.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			homepage.clickLoginButton();
			res.wait(driver).until(ExpectedConditions.visibilityOf(loginpage.btn_SignUp));
			loginpage.login(phoneNumber, password);
			res.wait(driver).until(ExpectedConditions.visibilityOf(homepage.icon_user_Greet));
			imageTime = mis.screenCapture(driver);
			long endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
			result = "OK";
			jsonObject.put("result", result);
			jsonObject.put("timeforopening", timeTaken);
			jsonObject.put("imagetime", imageTime);
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
			driver.quit();
		} catch (Exception e) {
			result = "KO";
			long endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
			e.printStackTrace();
			imageTime = mis.screenCapture(driver);
			jsonObject.put("result", result);
			jsonObject.put("timeforopening", timeTaken);
			jsonObject.put("imagetime", imageTime);
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
			driver.close();
		}
	}

	public String screenCapture(WebDriver driver) throws IOException {
		long name = System.currentTimeMillis();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File("Login_" + name + ".png");
		if (!destFile.exists()) {
			FileUtils.touch(destFile);
		}
		FileUtils.copyFile(scrFile, destFile);
		return String.valueOf(name);
	}
}

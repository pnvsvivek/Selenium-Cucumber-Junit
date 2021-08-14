package reusability;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
This is reusable methods class
 */
public class ReusableMethods {

	File file = new File("e2e_config.properties");

	public String readConfig(String key) {
		Properties prop = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			prop = new Properties();
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}

	public void setConfigValue(String key, String value) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		FileOutputStream fos = new FileOutputStream(file);
		prop.setProperty(key, value);
		prop.store(fos, null);
		fos.close();
	}

	public String newSampleText() {
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		return sdf.format(new Date());
	}

	public String newDateText() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		return sdf.format(new Date());
	}

	public WebDriver selectBrowser(String browser, String environment) {
		WebDriver driver = null;
		if (environment.equalsIgnoreCase("remote")) {
			try {
				if (browser.equalsIgnoreCase("chrome")) {
					System.out.println("this is chrome browser");
					System.setProperty("webdriver.chrome.driver", readConfig("ChromeDriver"));
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--no-sandbox");
					/*
					 * options.addArguments("headless");
					 * options.addArguments("window-size=1500x1000");
					 */
					DesiredCapabilities capabilities = DesiredCapabilities.chrome();
					capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					driver = new RemoteWebDriver(new URL(readConfig("Url_Remote_Webdriver")), capabilities);
					driver.manage().window().setSize(new Dimension(1280, 960));
					driver.manage().deleteAllCookies();
					System.out.println("Chrome browser launched");
				} else if (browser.equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver", readConfig("IEDriver"));
					DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
					cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
					cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					driver = new RemoteWebDriver(new URL(readConfig("Url_Remote_Webdriver")), cap);
					driver.manage().window().maximize();
				} else if (browser.equalsIgnoreCase("firefox")) {
					System.out.println("this is firefox browser");
					// System.setProperty("webdriver.gecko.driver",
					// readConfig("FirefoxDriver"));
					DesiredCapabilities cap = new DesiredCapabilities();
					cap.setCapability("browserName", "firefox");
					FirefoxProfile profile = new FirefoxProfile();
					profile.setPreference("network.proxy.type", 0);
					cap.setCapability(FirefoxDriver.PROFILE, profile);
					driver = new RemoteWebDriver(new URL(readConfig("Url_Remote_Webdriver")), cap);
					/*
					 * driver.manage().window().setSize(new Dimension(1280,
					 * 960)); driver.manage().deleteAllCookies();
					 */
					System.out.println("firefox browser launched");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (environment.equalsIgnoreCase("local")) {
			try {
				if (browser.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", readConfig("ChromeDriver"));
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--incognito");
					DesiredCapabilities capabilities = DesiredCapabilities.chrome();
					capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					driver = new ChromeDriver(capabilities);
					driver.manage().window().maximize();
					driver.manage().deleteAllCookies();
				} else if (browser.equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver", readConfig("IEDriver"));
					DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
					cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
					cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					driver = new InternetExplorerDriver(cap);
					driver.manage().window().maximize();
				} else if (browser.equalsIgnoreCase("firefox")) {
					System.setProperty("webdriver.gecko.driver", readConfig("FirefoxDriver"));
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
					driver.manage().deleteAllCookies();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return driver;
	}

	public WebDriverWait wait(WebDriver driver) {
		return new WebDriverWait(driver, 60);
	}

	public void clickUsingJavascriptExecutor(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public boolean verifymessage(WebElement element, String message) {
		boolean flag = false;
		if (element.getText().contains(message)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public void pageScroll(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public boolean verifyWhetherElementIsPresent(List<WebElement> element) {
		boolean flag = false;
		if (element.size() != 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
}

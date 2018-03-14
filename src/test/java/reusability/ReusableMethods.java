package reusability;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableMethods {
	
	File file = new File("e2e_config.properties");
	
	public String readConfig(String key) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public void setConfigValue(String key, String value) throws IOException
	{
		FileInputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		FileOutputStream fos = new FileOutputStream(file);
		prop.setProperty(key, value);
		prop.store(fos, null);
		fos.close();
	}
	
	public String newSampleText()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		return sdf.format(new Date());
	}
	
	public String newDateText()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		return sdf.format(new Date());
	}
	
	public WebDriver selectBrowser(String browser)
	{
		WebDriver driver = null;
		if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "drivers//chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver=new ChromeDriver(capabilities);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			}
		else if(browser.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", "drivers//IEDriverServer.exe");
			DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(cap);
			driver.manage().window().maximize();
		}
		return driver;
	}
	
	public WebDriverWait wait(WebDriver driver)
	{
		return new WebDriverWait(driver,120);
	}
	
	public void clickUsingJavascriptExecutor(WebDriver driver, WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	
	public boolean verifymessage(WebElement element, String message)
	{
		boolean flag = false;
		if(element.getText().contains(message))
		{
			flag = true;
		}
		else
		{
			flag=false;
		}
		return flag;
	}
	
	public void pageScroll(WebDriver driver,WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//This will scroll the page till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	public boolean verifyWhetherElementIsPresent(List<WebElement> element)
	{
		boolean flag=false;
		if(element.size()!=0)
		{
			flag=true;
		}
		else
		{
			flag=false;
		}
		return flag;
	}
}

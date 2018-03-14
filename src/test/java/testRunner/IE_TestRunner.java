package testRunner;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;

import cucumber.api.CucumberOptions;
import reusability.ReusableMethods;
import steps.Steps;

@RunWith(ExtendedCucumber.class)
@CucumberOptions(plugin = {"com.cucumber.listener.ExtentCucumberFormatter:"},features = {"src/test/resources/features/Registration.feature"},glue={"steps"},dryRun=false,tags="@RegisterWithInvalidOTP")
public class IE_TestRunner {
	static{
		Steps.browser="ie";
	}
	@BeforeClass
	public static void setup() {
		ReusableMethods res = new ReusableMethods();
		String tiemStamp = res.newDateText();
	    ExtentProperties extentProperties = ExtentProperties.INSTANCE;
	    extentProperties.setReportPath("C:\\Vodacom_Automation_Report\\IE_cucumber-reports\\Vodacom_PCTV_Automation_Report_"+tiemStamp+".html");
	}
	
	@AfterClass
	public static void endTests() throws IOException {
		Reporter.loadXMLConfig(new File("extent-config.xml"));
	}
}

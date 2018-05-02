package reusability;

import static io.restassured.RestAssured.given;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Utilities {

	ReusableMethods res = new ReusableMethods();
	static String path;
	String sheetname = "Fusion report";
	Workbook workbook;

	public Utilities() {
		try {
			path = res.readConfig("pathforExcelReport");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Response deRegisterAPI(String username) throws IOException {
		Response response = null;
		HashMap<String, String> mapper = new HashMap<String, String>();
		mapper.put("username", username);
		String channel = "PCTV";
		response = given().pathParam("channel1", channel).and().contentType(ContentType.JSON).body(mapper).when()
				.post(res.readConfig("ApplicationURL") + "/AGL/1.0/A/ENG/{channel1}/VIDEOPLAY_EMEA/FUSION/DEREGISTER")
				.then().contentType(ContentType.JSON).extract().response();
		return response;
	}

	public String screenCapture(WebDriver driver) throws IOException {
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		 * String name = sdf.format(new Date()); String destPath =
		 * res.readConfig("screenprints") + name + ".jpeg"; File scrFile =
		 * ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); File
		 * destFile = new File(destPath); if (!destFile.exists()) {
		 * FileUtils.touch(destFile); } FileUtils.copyFile(scrFile, destFile);
		 * return destPath;
		 */

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage src = ImageIO.read(scrFile);
		BufferedImage convertedImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
		convertedImg.getGraphics().drawImage(src, 0, 0, null);

		File output = new File("compressed.jpeg");
		OutputStream out = new FileOutputStream(output);

		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(out);
		writer.setOutput(ios);

		ImageWriteParam param = writer.getDefaultWriteParam();
		if (param.canWriteCompressed()) {
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.5f);
		}

		writer.write(null, new IIOImage(convertedImg, null, null), param);

		out.close();
		ios.close();
		writer.dispose();
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(output);
			byte[] bytes = new byte[(int) output.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.getEncoder().encode(bytes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "data:image/dib;base64," + encodedBase64;
	}

	public static HashMap<String, String> readDB(String JDBC_Driver, String DB_Url, String username, String password,
			String SqlQuery, String uniqueValue)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Class.forName(JDBC_Driver).newInstance();
		Connection con = DriverManager.getConnection(DB_Url, username, password);
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(SqlQuery);
		ResultSetMetaData rmd = res.getMetaData();
		int columncount = rmd.getColumnCount();
		while (res.next()) {
			HashMap<String, String> hm = new HashMap<String, String>();
			for (int i = 0; i < columncount; i++) {
				hm.put(rmd.getColumnLabel(i), res.getString(i));
			}
			list.add(hm);
		}
		HashMap<String, String> expHm = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			expHm = list.get(i);
			if (expHm.values().contains(uniqueValue)) {
				break;
			}
		}
		return expHm;
	}

	public void createExcelReport() throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			FileUtils.touch(file);
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(sheetname);
			Row rowhead = sheet.createRow(0);
			CellStyle style = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setColor(IndexedColors.GREEN.getIndex());
			style.setFont(headerFont);
			rowhead.createCell(0).setCellValue("Feature");
			rowhead.createCell(1).setCellValue("MSISDN");
			rowhead.createCell(2).setCellValue("Password");
			rowhead.createCell(3).setCellValue("New Password");
			rowhead.createCell(4).setCellValue("Purchased Content");
			rowhead.createCell(5).setCellValue("TimeStamp");
			rowhead.createCell(6).setCellValue("TimeStamp for Password Changed");
			for (int i = 0; i <= 6; i++) {
				sheet.autoSizeColumn(i);
				rowhead.getCell(i).setCellStyle(style);
			}
			FileOutputStream fileOut = new FileOutputStream(file);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		}
	}

	public Sheet getExcel() throws IOException {
		FileInputStream fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetname);
		return sheet;
	}

	public void writeExcel() throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		workbook.write(fos);
		fos.close();
	}

	public void deleteSheet() throws IOException {
		File file = new File(path);
		if (file.exists()) {
			Sheet sheet = getExcel();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i) != null) {
					sheet.removeRow(sheet.getRow(i));
				}
			}
			writeExcel();
		}
	}

	public String readOTPfromEmail(WebDriver driver, String phoneNumber, String browser) throws Exception {
		String actualotp = null;
		if (browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("firefox")) {
			String mainwindow = driver.getWindowHandle();
			((JavascriptExecutor) driver).executeScript("window.open();");
			try {
				Set<String> windows = driver.getWindowHandles();
				for (String s : windows) {
					if (!s.equalsIgnoreCase(mainwindow)) {
						driver.switchTo().window(s);
						List<WebElement> mailsObj = GetTheGmailInbox(driver);
						System.out.println("total no of unread mails are===" + mailsObj.size());
						// Iterating over emails
						if (phoneNumber.charAt(0) == '0') {
							phoneNumber = phoneNumber.replaceFirst("[0]", "27");
						}
						System.out.println("Phone Number is " + phoneNumber);
						for (WebElement m : mailsObj) {
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							String mail_body = m.getText();
							if (mail_body.contains(phoneNumber)) {
								String array1[] = mail_body.split("Pin:");
								String otp = array1[1].substring(1, 6);
								actualotp = otp;
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				driver.quit();
			} finally {
				driver.close();
				driver.switchTo().window(mainwindow);
			}
		} else if (browser.equalsIgnoreCase("ie")) {
			try {
				driver = res.selectBrowser("chrome", res.readConfig("environment"));
				List<WebElement> mailsObj = GetTheGmailInbox(driver);
				System.out.println("total no of unread mails are===" + mailsObj.size());
				// Iterating over emails
				if (phoneNumber.charAt(0) == '0') {
					phoneNumber = phoneNumber.replaceFirst("[0]", "27");
				}
				System.out.println("Phone Number is " + phoneNumber);
				for (WebElement m : mailsObj) {
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					String mail_body = m.getText();
					if (mail_body.contains(phoneNumber)) {
						String array1[] = mail_body.split("Pin:");
						String otp = array1[1].substring(1, 6);
						actualotp = otp;
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				driver.close();
			} finally {
				driver.close();
			}
		}
		return actualotp;
	}

	@SuppressWarnings("deprecation")
	public String readOTPfromFile(WebDriver driver, String phoneNumber, String browser) throws Exception {
		File file = null;
		try {
			file = new File(res.readConfig("pathforOTPfile"));
			if (!file.exists()) {
				FileUtils.touch(file);
			}
			List<String> list = new ArrayList<String>();
			list.add(readOTPfromEmail(driver, phoneNumber, browser));
			FileUtils.writeLines(file, list);
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			driver.close();
		}
		return FileUtils.readFileToString(file);
	}

	public List<WebElement> GetTheGmailInbox(WebDriver driver) throws IOException, InterruptedException {
		List<WebElement> mailsObj = null;
		driver.get("https://accounts.google.com/ServiceLogin?");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		List<WebElement> elements = driver.findElements(By.id("identifierId"));
		if (elements.size() != 0) {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("identifierId"))));
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			// gmail login
			driver.findElement(By.id("identifierId")).sendKeys(res.readConfig("email"));
			Reporter.addScreenCaptureFromPath(screenCapture(driver));
			Thread.sleep(2000);
			res.clickUsingJavascriptExecutor(driver,
					driver.findElement(By.xpath("//*[@id='identifierNext']/content/span")));
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			// driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//input[contains(@name,'password')]"))));
			driver.findElement(By.xpath("//input[contains(@name,'password')]"))
					.sendKeys(res.readConfig("email_password"));
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			Reporter.addScreenCaptureFromPath(screenCapture(driver));
			driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			Reporter.addScreenCaptureFromPath(screenCapture(driver));
		}
		List<WebElement> recovery_email = driver
				.findElements(By.xpath("//input[@aria-label='Enter recovery email address']"));
		if (res.verifyWhetherElementIsPresent(recovery_email)) {
			Thread.sleep(15000);
			WebElement rec = driver.findElement(By.xpath("//input[@aria-label='Enter recovery email address']"));
			rec.sendKeys(res.readConfig("recovery_gmail"));
			driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			System.out.println("Account page is displayed");
			Reporter.addScreenCaptureFromPath(screenCapture(driver));
			Thread.sleep(15000);
			Reporter.addScreenCaptureFromPath(screenCapture(driver));
		}
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='gbwa']/div/a"))));
		driver.findElement(By.xpath("//*[@id='gbwa']/div/a")).click();
		Reporter.addScreenCaptureFromPath(screenCapture(driver));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(" //*[@id='gb23']/span[1]"))));
		driver.findElement(By.xpath(" //*[@id='gb23']/span[1]")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@title,'Inbox')]"))));
		Reporter.addScreenCaptureFromPath(screenCapture(driver));
		mailsObj = driver.findElements(By.xpath("//*[@class='zA zE']"));
		return mailsObj;
	}
}

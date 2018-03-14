package reusability;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.restassured.http.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class Utilities {
	
	ReusableMethods res = new ReusableMethods();
	
	public Response deRegisterAPI(String username) throws IOException
	{
		Response response = null;
		HashMap<String, String> mapper = new HashMap<String, String>();
		mapper.put("username",username);
		String channel = "PCTV";
		response= given().pathParam("channel1", channel).and().contentType(ContentType.JSON).body(mapper).when().post(res.readConfig("ApplicationURL")+"/AGL/1.0/A/ENG/{channel1}/FUSION/DEREGISTER").
		then().contentType(ContentType.JSON).extract().response();
		return response;
	}
	
	public String screenCapture(WebDriver driver) throws IOException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_hh_mm_ss");
		String name = sdf.format(new Date());
		String destPath = "C:\\screen-prints"+"\\"+name+".jpeg";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File(destPath);
		FileUtils.copyFile(scrFile, destFile);
		return destPath;
	}
	
	public static HashMap<String, String> readDB(String JDBC_Driver, String DB_Url, String username, String password, String SqlQuery, String uniqueValue) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Class.forName(JDBC_Driver).newInstance();
		Connection con = DriverManager.getConnection(DB_Url, username, password);
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(SqlQuery);
		ResultSetMetaData rmd = res.getMetaData();
		int columncount = rmd.getColumnCount();
		while(res.next())
		{
			HashMap<String, String> hm = new HashMap<String, String>();
			for(int i=0; i<columncount; i++)
			{
				hm.put(rmd.getColumnLabel(i),res.getString(i));
			}
			list.add(hm);
		}
		HashMap<String, String> expHm = new HashMap<String, String>();
		for(int i=0; i< list.size(); i++)
		{
			expHm = list.get(i);
			if(expHm.values().contains(uniqueValue))
			{
				break;
			}
		}
		return expHm;
	}
}

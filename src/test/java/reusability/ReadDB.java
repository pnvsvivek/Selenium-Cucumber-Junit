package reusability;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ReadDB {
	
	
	final static String JDBC_Driver = "com.mysql.jdbc.Driver";
	final static String DB_Url = "jdbc:mysql://10.30.153.12:3306/avs_be";
	final static String username="AVS_BE";
	final static String password = "GRIRAxfu";
	
	public static void main(String args[]) throws SQLException, IOException
	{
	Connection conn = DriverManager.getConnection(DB_Url,username,password);
    Statement stmt = conn.createStatement();
    ResultSet res = stmt.executeQuery("select * from avs_be.account_user");
    while(res.next())
    {
    	System.out.println(res.getString("user_id"));
    }
	}
}

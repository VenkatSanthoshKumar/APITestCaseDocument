package com.Ninza.HrmGenericUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DataBaseUtility {
    public static Connection conn;
	FileUtility flib=new FileUtility();

	public void getDataBaseConnection(String url, String userName, String password) throws Throwable {
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			Connection conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {

		}
	}

	public void getDBConnection() {

		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			Connection conn = DriverManager.getConnection(flib.getdatafrompropertyfile("DBURL"),flib.getdatafrompropertyfile("DBUserName"),flib.getdatafrompropertyfile("DBPassword"));
		} catch (Exception e) {

		}
	}

	public void closeConnection() throws Throwable {
		conn.close();
	}

	public ResultSet executeNonSelectQuery(String query) throws Throwable {
		ResultSet result = null;
		try {
			Statement stat = conn.createStatement();
			result = stat.executeQuery(query);
		} catch (Exception e) {
		}
		return result;
	}

	public boolean executeSelectQueryAndValidate(String query, int columnName, String expectedData) throws Throwable {
	 boolean flag= false;
	 ResultSet resultset = conn.createStatement().executeQuery(query);
		while (resultset.next()) {
			String actprojectname = resultset.getString(columnName);
			if (expectedData.equals(actprojectname)) {
				flag = true;
				
				break;
			}
		}
	  if(flag) {
		  System.out.println(expectedData + "is avaliable");
		  return true;  
	  }
	  else
		  System.out.println(expectedData + "is not avaliable");
	  return false;
	}
}



package com.Ninza.Hrm.Api.Employetest;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.Ninza.Hrm.api.baseclass.Baseclass;
import com.Ninza.Hrm.api.constant.endpoint.IEndpoint;
import com.Ninza.Hrm.api.pojoclass.Emp_pojo;
import com.Ninza.Hrm.api.pojoclass.Project_Pojo;
import com.Ninza.HrmGenericUtility.DataBaseUtility;
import com.Ninza.HrmGenericUtility.FileUtility;
import com.Ninza.HrmGenericUtility.JavaUtility;
import com.mysql.cj.jdbc.Driver;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Employetest extends Baseclass {
	
	@Test
	public void addEmploye() throws SQLException, Throwable {
		
		String baseURl=flib.getdatafrompropertyfile("BASEURL");
		int Ram =jlib.getRandonNum();

		String ProjectName = "Samantha_" + Ram;
		String username = "User_" + Ram;

		// Api-01====> add a project
		Project_Pojo pobj = new Project_Pojo(ProjectName, "Manager", "Divorced", 7);
		Response resp = given().spec(reqSpecBuilder).body(pobj).when()
				.post(IEndpoint.AddProject);
		resp.then().log().all();

		// capture projectName from the response
		String projectName = resp.jsonPath().getString("projectName");

		System.out.println(projectName);
		// Api-02====> Add Emp To Project

		// String designation, String dob, String email, String empName, int experience,
		// String mobileNo,
		// String role, String username
		Emp_pojo eObj = new Emp_pojo("Manager", "28/12/1996", "venkatsanthoshkumar0@gmail.com", username + Ram, 03,
				"8660663551", projectName, "ROLE_EMPLOYEE", "Sam" + Ram);
		given().spec(reqSpecBuilder).body(eObj).when().post(baseURl+IEndpoint.AddEmploye).then()
				.assertThat().statusCode(201).assertThat().spec(specResBuilder).time(Matchers.lessThan(3000L))
				.log().all();

		// verify the project is created in database
		dblib.getDBConnection();
		boolean flag=dblib.executeSelectQueryAndValidate("select * from employee", 5, username);
		dblib.closeConnection();
		
//		boolean flag = false;
//
//		Driver driverref = new Driver();
//		DriverManager.registerDriver(driverref);
//		Connection conn = DriverManager.getConnection(flib.getdatafrompropertyfile("DBURL"),flib.getdatafrompropertyfile("DBUserName"),flib.getdatafrompropertyfile("DBPassword"));
//		Statement stat = conn.createStatement();
//		ResultSet resultset = stat.executeQuery("select * from employee");
//		while (resultset.next()) {
//			// String actprojectname = resultset.getString(4);
//			// if (expectedresult.equals(actprojectname)) {
//			if (resultset.getString(5).equals(username)) {
//				flag = true;
//				break;
//			}
//		}
//		conn.close();
		Assert.assertTrue(flag, "Employee in db is not verified");
	}

	@Test
	public void addEmployeWithOutEmail() throws Throwable {
		String baseURl=flib.getdatafrompropertyfile("BASEURL");
		int Ram =jlib.getRandonNum();

		String ProjectName = "Samantha_" + Ram;
		String username = "User_" + Ram;

		// Api-01====> add a project
		Project_Pojo pobj = new Project_Pojo(ProjectName, "Manager", "Divorced", 7);
		Response resp = given().spec(reqSpecBuilder).body(pobj).when()
				.post(IEndpoint.AddProject);
		resp.then().log().all();

		// capture projectName from the response
		String projectName = resp.jsonPath().getString("projectName");

		System.out.println(projectName);
		// Api-02====> Add Emp To Project

		// String designation, String dob, String email, String empName, int experience,
		// String mobileNo,
		// String role, String username
		Emp_pojo eObj = new Emp_pojo("Manager", "28/12/1996", "", username + Ram, 03, "8660663551", projectName,
				"ROLE_EMPLOYEE", "Sam" + Ram);
		given().spec(reqSpecBuilder).body(eObj).when().post(IEndpoint.AddEmploye).then()
				.assertThat().statusCode(500).assertThat().time(Matchers.lessThan(300L))
				.spec(specResBuilder).log().all();

		// verify the project is created in database

	}
}
package com.Ninza.Hrm.Api.Projecttest;

import static io.restassured.RestAssured.given;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import com.Ninza.Hrm.api.baseclass.Baseclass;
import com.Ninza.Hrm.api.constant.endpoint.IEndpoint;
import com.Ninza.Hrm.api.pojoclass.Project_Pojo;
import com.Ninza.HrmGenericUtility.DataBaseUtility;
import com.Ninza.HrmGenericUtility.FileUtility;
import com.Ninza.HrmGenericUtility.JavaUtility;
import com.mysql.cj.jdbc.Driver;

import java.sql.DriverManager;
import org.openqa.selenium.WebDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Projecttest extends Baseclass {
	String ProjectName;
	Project_Pojo pobj;
		

	@Test
	public void addSingleProjectWithCreated() throws Throwable {
       
		String baseURl=flib.getdatafrompropertyfile("BASEURL");
		int Ram =jlib.getRandonNum();   
		String ProjectName = "Samantha_" + Ram;
		// add project to ninza hrm
		pobj = new Project_Pojo(ProjectName, "created", "Samantha", 7);
		Response resp = given().spec(reqSpecBuilder).body(pobj).when()
				.post(IEndpoint.AddProject);
		resp.then().assertThat().statusCode(201).assertThat().time(Matchers.lessThan(3000L)).assertThat()
				.spec(specResBuilder).log().all();

		String actualresult = resp.jsonPath().get("msg");

		// verify the expected result and actual result

		String expectedresult = "Successfully Added";

		Assert.assertEquals(actualresult, expectedresult);

		// verify the project is created in database
		boolean flag=dblib.executeSelectQueryAndValidate("select * from project", 4, expectedresult);
		dblib.closeConnection();
		
		
//		
//		boolean flag = false;
//
//		Driver driverref = new Driver();
//		DriverManager.registerDriver(driverref);
//		Connection conn = DriverManager.getConnection(flib.getdatafrompropertyfile("DBURL"),flib.getdatafrompropertyfile("DBUserName"),flib.getdatafrompropertyfile("DBPassword"));
//		Statement stat = conn.createStatement();
//		ResultSet resultset = stat.executeQuery("select * from project");
//		while (resultset.next()) {
//			String actprojectname = resultset.getString(4);
//			if (expectedresult.equals(actprojectname)) {
//				flag = true;
//				System.out.println(expectedresult + "is avaliable");
//			}
//		}
//		conn.close();
		Assert.assertTrue(flag, "project in db is not verified");
	}

	@Test(dependsOnMethods = "addSingleProjectWithCreated")
	public void addDuplicateProject() throws Throwable {
		String baseURl=flib.getdatafrompropertyfile("BASEURL");

		Response resp = given().spec(reqSpecBuilder).body(pobj).when()
				.post(IEndpoint.AddProject);
		resp.then().assertThat().statusCode(409);
	}
	
	@AfterSuite
	public void aftersuite() throws Throwable {
		dblib.closeConnection();
		System.out.println("===== DB  end=========");
	}
}
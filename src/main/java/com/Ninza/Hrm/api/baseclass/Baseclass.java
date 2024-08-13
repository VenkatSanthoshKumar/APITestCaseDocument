package com.Ninza.Hrm.api.baseclass;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.Ninza.HrmGenericUtility.DataBaseUtility;
import com.Ninza.HrmGenericUtility.FileUtility;
import com.Ninza.HrmGenericUtility.JavaUtility;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Basic;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Baseclass {
	
   public FileUtility flib=new FileUtility();
   public JavaUtility jlib=new JavaUtility();
   public DataBaseUtility dblib=new DataBaseUtility();
   public RequestSpecification reqSpecBuilder;
   public ResponseSpecification specResBuilder;
	
	@BeforeSuite
	public void beforesuite() throws Throwable {
		dblib.getDBConnection();
		System.out.println("===== DB  start========="); 
		RequestSpecBuilder builder=new RequestSpecBuilder();
		builder.setContentType(ContentType.JSON);
		//builder.setAuth(RestAssured.basic("username", "password"));
		//builder.addHeader("", "");
		builder.setBaseUri(flib.getdatafrompropertyfile("BASEURL"));
	    reqSpecBuilder=builder.build();
	    
	    ResponseSpecBuilder ResBuilder=new ResponseSpecBuilder();
	    ResBuilder.expectContentType(ContentType.JSON);
	    specResBuilder=ResBuilder.build();
	    
	    
		
		
		
	}
	
	@AfterSuite
	public void aftersuite() throws Throwable {
		dblib.closeConnection();
		System.out.println("===== DB  end=========");
	}


}

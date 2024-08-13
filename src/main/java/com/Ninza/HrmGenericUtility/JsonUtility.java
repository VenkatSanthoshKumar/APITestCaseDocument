package com.Ninza.HrmGenericUtility;

import static io.restassured.RestAssured.given;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JsonUtility { 
	
	static FileUtility flib=new FileUtility();
	
	
	public String getDataFromJsonFile(String Key) throws IOException, ParseException
	{   
		//step1 : parse Json Physical file into java using JSonPArse class
	 	FileReader filer=new FileReader("./ConfigAppData/comcommonData.json");
		JSONParser parser=new JSONParser();	
		Object obj=parser.parse(filer);
		
		//Step 2:-convert java object into JSONObject by DownCasting
		JSONObject map=(JSONObject)obj;
		String data=(String)map.get(Key);
		return data;
	}
	
	
	
	public String getDataOnjsonPath(Response resp,String jsonpath) {
		List<Object> list=JsonPath.read(resp.asString(), jsonpath);
		return list.get(0).toString();
	}
	
	public String getDataOnXpathPath(Response resp,String xmlpath) {
		return resp.xmlPath().get(xmlpath);
		
	}
	
	public boolean verifyDataFromXpath(Response resp,String jsonpath,String expectedData) {
		List<String> list=JsonPath.read(resp.asString(), jsonpath);
		boolean flag=false;
		for(String str : list) {
			if(str.equals(expectedData)) {
				System.out.println(expectedData +  "is avaliable");
				flag=true;
			}
			else {
				System.out.println(expectedData +  "is not avaliable");
			}
		}
		return flag;
	}
  public String getAcessToken() throws Throwable {
	  Response resp=given()
			  .formParam("client_id", flib.getdatafrompropertyfile("ClientID"))
			  .formParam("client_secret", flib.getdatafrompropertyfile("ClientSecret"))
			  .formParam("grant_type", "client_credentials")
			.when()
			    .post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");
			  resp.then().log().all();
			
			 //capture the token from the response
			 String  token=resp.jsonPath().get("access_token");
			  return token;
  }
}

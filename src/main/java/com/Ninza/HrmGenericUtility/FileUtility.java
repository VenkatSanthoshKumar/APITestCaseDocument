package com.Ninza.HrmGenericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtility {
 public String getdatafrompropertyfile(String key) throws IOException 
 {
	FileInputStream fis=new FileInputStream("./Config_Env_Data/configration.properties");
	Properties pobj=new Properties();
	pobj.load(fis);
	String data=pobj.getProperty(key);
	return data;
	 
 }
}

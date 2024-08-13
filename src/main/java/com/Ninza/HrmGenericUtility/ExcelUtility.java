package com.Ninza.HrmGenericUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.microsoft.schemas.office.visio.x2012.main.CellType;

public class ExcelUtility {
 public String getDataFromExcel(String sheetName, int rowNum, int celNum) throws EncryptedDocumentException, IOException
 {
	 FileInputStream fis=new FileInputStream("./TestData/testscriptdata1.xlsx");
	 Workbook wb=WorkbookFactory.create(fis);
	 String data=wb.getSheet(sheetName).getRow(rowNum).getCell(celNum).getStringCellValue();
	 return data;
  }
 public int getRowCount(String sheetName) throws Throwable
 {
	 FileInputStream fis=new FileInputStream("./TestData/testscriptdata1.xlsx");
	 Workbook wb=WorkbookFactory.create(fis);
	 int data=wb.getSheet(sheetName).getLastRowNum();
	 return data;
 }
 public void setdataIntoExcel(String sheetName, int rowNum, int celNum, String data) throws Throwable
 {
	 FileInputStream fis=new FileInputStream("./TestData/testscriptdata1.xlsx");
	 Workbook wb=WorkbookFactory.create(fis);
	 Cell cel=wb.getSheet(sheetName).getRow(rowNum).createCell(celNum);
	 cel.setCellValue(data);
	 
	 FileOutputStream fos=new FileOutputStream("./TestData/testscriptdata1.xlsx");
	 wb.write(fos);
	 wb.close();
 }
}

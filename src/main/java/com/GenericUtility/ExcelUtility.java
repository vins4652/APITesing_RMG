 package com.GenericUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	/**
	 * This method is used to read the String data from the given excel sheet
	 * @param sheetName
	 * @param rownum
	 * @param cellnum
	 * @return String dataInCell
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author VinayKumar Mannur
	 */
	public String getStringTestdata(String sheetName, int rownum, int cellnum) throws EncryptedDocumentException, IOException {
		try {
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rownum);
		return row.getCell(cellnum).getStringCellValue();
		} catch(EncryptedDocumentException e) {
			return "File is Encrypted or File not present";
		} catch(IOException i) {
			return "Error occured during reading the data";
		}
		
	}
	/**
	 * This method is used to read the Numeric data from the given excel sheet
	 * @param sheetName
	 * @param rownum
	 * @param cellnum
	 * @return double
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public double getNumericTestdata(String sheetName, int rownum, int cellnum) throws EncryptedDocumentException, IOException {
		
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rownum);
		return row.getCell(cellnum).getNumericCellValue();
	}
	
	/**
	 * this method is used to write the data in to the excel sheet
	 * @param sheetName
	 * @param rownum
	 * @param cellnum
	 * @param value
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public void setStringData(String sheetName, int rownum, int cellnum, Date value) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.createRow(rownum);
		row.createCell(cellnum).setCellValue(value);
		FileOutputStream fos = new FileOutputStream(IPathConstant.EXCELFILEPATH);
		workbook.write(fos);
	}
	/**
	 * This method will give the last row Number in the given sheet and index starts from 1
	 * @param sheetName
	 * @return int NumberOfRows
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public int getLastRowNumber(String sheetName) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getLastRowNum();
	}
	
	/**
	 * This method will give the last cell Number in the given sheet given row and index starts from 1
	 * @param sheetName
	 * @param rownum
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public short getLastCellNumber(String sheetName, int rownum) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getRow(rownum).getLastCellNum();
	}
	
	/**
	 * this method will give the value stored in excel sheet in the form of key value use getkey("key") and getKey("key").getvalue()
	 * to fetch the data from map but it will not follow the order
	 * @param sheetName
	 * @return map having key value stored in the excel
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public HashMap<String,String> getMultipleDataFromExcel(String sheetName) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		int lastRownum = sheet.getLastRowNum();
		HashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i <= lastRownum; i++) {
			String key = sheet.getRow(i).getCell(0).getStringCellValue();
			String value = sheet.getRow(i).getCell(1).getStringCellValue();
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * this method will give the value stored in excel sheet for the respective key passed
	 * to fetch the data from map but it will not follow the order
	 * @param sheetName
	 * @return String expData
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public String getExpectedDataFromExcel(String sheetName, String expDataKey) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		int lastRownum = sheet.getLastRowNum();
		HashMap<String, String> map = new HashMap<String, String>();	
		for (int i = 0; i <= lastRownum; i++) {
			String key = sheet.getRow(i).getCell(0).getStringCellValue();
			String value = sheet.getRow(i).getCell(1).getStringCellValue();
			map.put(key, value);
		}
		return map.get(expDataKey);
	}
	/**
	 * This method will return the Object[][] to the @dataProvider annotated method to supply the data from excel
	 * @param sheetName
	 * @return Object[][] obj;
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public Object[][] readMultipledataForDataProvider(String sheetName) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(IPathConstant.EXCELFILEPATH);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheetName);
		int lastRow = sh.getLastRowNum()+1;
		int lastCell = sh.getRow(0).getLastCellNum();
		
		Object[][] obj = new Object[lastRow][lastCell];
		for(int i=0;i<lastRow;i++) {
			for(int j =0;j<lastCell;j++) {
				obj[i][j]=sh.getRow(i).getCell(j).getStringCellValue();
			}
		}
		return obj;
	}
	
	
}

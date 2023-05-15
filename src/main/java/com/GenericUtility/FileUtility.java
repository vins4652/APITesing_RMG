package com.GenericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtility {

	/**
	 * This method will give the value of respected key passed and stored in properties file
	 * @param key
	 * @return String Value
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public String getProperyFileData(String key) throws IOException {
		FileInputStream fis = new FileInputStream(IPathConstant.PROPERTYFILEPATH);
		Properties pObj = new Properties();
		pObj.load(fis);
		return pObj.getProperty(key);
	}
}

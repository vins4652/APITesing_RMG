package com.GenericUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class DBUtility {

	private Connection con = null;

	public void connectToDB() throws SQLException {

		Driver db = new Driver();
		DriverManager.registerDriver(db);
		con = DriverManager.getConnection(IPathConstant.DBURL, IPathConstant.DBUSERNAME, IPathConstant.DBPASSWORD);
	}

	public String executeQueryAndReturnData(String query, int columnIndex, String expData) throws SQLException {
		Statement stmt = con.createStatement();
		try {
			ResultSet result = stmt.executeQuery(query);
			String data = null;
			boolean flag = false;
			while (result.next()) {
				data = result.getString(columnIndex);
				if (data.equals(expData)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				System.out.println(data + " and " + expData + " is matching");
				return data;
			} else {
				System.out.println(data + " and " + expData + " is not matching");
				return null;
			}
		} catch (SQLException e) {
			return "Incorrect query";
		}
	}

	public String updateDataToDB(String query) throws SQLException {
		Statement stmt = con.createStatement();
		try {
			int affectedLines = stmt.executeUpdate(query);
			if (affectedLines != 0) {
				return "Database sucessfully updated with" + affectedLines + "lines";
			} else {
				return "Query executed but not affected any lines";
			}

		} catch (SQLException e) {
			return "Incorrect query";
		}
	}

	public void closeDBConnection() throws SQLException {
		con.close();
	}

}

package com.GenericUtility;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class APIBaseClass {

	public DBUtility DB = new DBUtility();
	public JavaUtility jLib = new JavaUtility();
	public RestAssuredLibrary rLib = new RestAssuredLibrary();
	
	public RequestSpecification requestSpec;
	public ResponseSpecification responseSpec;
	
	@BeforeSuite
	public void beforeSuiteConfig() throws SQLException {
		
		RestAssured.baseURI="http://rmgtestingserver";
		RestAssured.port=8084;
		
		DB.connectToDB();
		System.out.println("connected to DB");
		
		requestSpec = new RequestSpecBuilder()
			.setContentType(ContentType.JSON).build();
		
		responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
		
	}
	@AfterSuite
	public void afterSuiteConfug() throws SQLException {
		
		DB.closeDBConnection();
		
	}
	
}

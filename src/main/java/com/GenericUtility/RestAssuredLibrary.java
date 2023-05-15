package com.GenericUtility;

import io.restassured.response.Response;

public class RestAssuredLibrary {

	
	public String getJSONValue(Response response, String name) {
		String value=response.jsonPath().get(name);	
		return value;
	}
	
}

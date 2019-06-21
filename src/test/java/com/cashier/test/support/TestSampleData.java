package com.cashier.test.support;

public class TestSampleData {

	public static final String REGISTER_USER_JSON = "{" + 
			"\"username\": \"BobFrench\"," + 
			"\"password\": \"Password1\"," + 
			"\"dob\": \"1980-02-21\"," + 
			"\"paymentCardNumber\": \"349293081054422\"" + 
			"} ";
	
	public static final String buildRegisterRequest(String username) {
		return String.format("{" + 
				"\"username\": \"%s\"," + 
				"\"password\": \"Password1\"," + 
				"\"dob\": \"1980-02-21\"," + 
				"\"paymentCardNumber\": \"349293081054422\"" + 
				"} ", username);
	}
	
	public static final String buildRegisterRequest(String username, String password, String dob, String card) {
		return String.format("{" + 
				"\"username\": \"%s\"," + 
				"\"password\": \"%s\"," + 
				"\"dob\": \"%s\"," + 
				"\"paymentCardNumber\": \"%s\"" + 
				"} ", username, password, dob, card);
	}
}

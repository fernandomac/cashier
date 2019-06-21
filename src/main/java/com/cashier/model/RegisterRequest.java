package com.cashier.model;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class RegisterRequest {

	@ApiModelProperty(
		value = "Username - alphanumeric, no spaces",
		example = "BobFrench", 
		required=true
	)
	private String username;
	
	@ApiModelProperty(
		value = "Password – min length 4, at least one upper case letter & number",
		example = "Password1",
		required=true
	)
	private String password;
	
	@ApiModelProperty(
		value = "DoB (Date of Birth) - ISO 8601 format",
		example = "1980-02-21", 
		required=true
	)
	private LocalDate dob;
	
	@ApiModelProperty(
		value = "Payment Card Number – between 15 and 19 digits",
		example = "0349293081054422", 
		required=true
	)
	private String paymentCardNumber;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPaymentCardNumber() {
		return paymentCardNumber;
	}

	public void setPaymentCardNumber(String paymentCardNumber) {
		this.paymentCardNumber = paymentCardNumber;
	}

	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", password=*******" + ", dob=" + dob
				+ ", paymentCardNumber=" + paymentCardNumber + "]";
	}
	
}

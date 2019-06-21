package com.cashier.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserProfile {

	private String username;
	private String password;
	private LocalDate dob;
	private String paymentCardNumber;
	
	@Pattern(regexp="^[a-zA-Z0-9]*$")
	@NotNull
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	@Pattern(regexp="^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{4,}$")
	@NotNull
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotNull
	public LocalDate getDob() {
		return dob;
	}
	
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	@Pattern(regexp="\\d{15,19}")
	@NotNull
	public String getPaymentCardNumber() {
		return paymentCardNumber;
	}
	
	public void setPaymentCardNumber(String paymentCardNumber) {
		this.paymentCardNumber = paymentCardNumber;
	}
	
}

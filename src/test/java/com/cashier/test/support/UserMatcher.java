package com.cashier.test.support;

import java.time.LocalDate;

import org.mockito.ArgumentMatcher;

import com.cashier.domain.UserProfile;

public class UserMatcher implements ArgumentMatcher<UserProfile> {

	private final String username;
	private final String password;
	private final LocalDate dob;
	private final String paymentCardNumber;	
	
	public UserMatcher(String username, String password, LocalDate dob, String paymentCardNumber) {
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.paymentCardNumber = paymentCardNumber;
	}

	@Override
	public boolean matches(UserProfile profile) {
		return profile.getUsername().equals(username) 
				&& profile.getPassword().equals(password)
				&& profile.getDob().equals(dob)
				&& profile.getPaymentCardNumber().equals(paymentCardNumber);
	}

}

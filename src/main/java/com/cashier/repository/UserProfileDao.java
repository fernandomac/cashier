package com.cashier.repository;

import java.util.Optional;

import com.cashier.domain.UserProfile;

public interface UserProfileDao {

	void add(UserProfile userProfile);
	
	Optional<UserProfile> find(String username);
}

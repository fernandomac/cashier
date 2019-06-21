package com.cashier.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.cashier.domain.UserProfile;

@Repository
public class MemoryUserProfileRepository implements UserProfileDao {

	private final Map<String, UserProfile> userProfileRepo = new ConcurrentHashMap<>();

	@Override
	public void add(UserProfile userProfile) {
		userProfileRepo.put(userProfile.getUsername().toUpperCase(), userProfile);
	}

	@Override
	public Optional<UserProfile> find(String username) {
		UserProfile userProfile = userProfileRepo.get(username.toUpperCase());
		return Optional.ofNullable(userProfile);
	}
	
	

}

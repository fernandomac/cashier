package com.cashier.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cashier.domain.UserProfile;
import com.cashier.exception.ContentNotAcceptableException;
import com.cashier.exception.DataFormatException;
import com.cashier.exception.DuplicatedException;
import com.cashier.exception.ForbiddenException;
import com.cashier.model.RegisterRequest;
import com.cashier.repository.UserProfileDao;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	private static final int MINIMUN_AGE = 18;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);
	
	private final ModelMapper mapper;
	private final Validator validator;
	private final UserProfileDao userProfileDate;
	private final List<String> cardIssuerBlocked;
	
	@Autowired
	public UserProfileServiceImpl(
			UserProfileDao userProfileDate, 
			ModelMapper mapper, 
			Validator validator,
			@Value("#{'${card.issuer.blocked.list}'.split(',')}") List<String> cardIssuerBlocked) {
		
		this.userProfileDate = userProfileDate;
		this.mapper = mapper;
		this.validator = validator;
		this.cardIssuerBlocked = cardIssuerBlocked;
		LOGGER.info("Card issuer blocked list loaded: {}", this.cardIssuerBlocked);
	}

	@Override
	public void register(RegisterRequest request) {
		UserProfile profile = mapper.map(request, UserProfile.class);
		validateProfile(profile);
		userProfileDate.add(profile);
	}

	private void validateProfile(UserProfile profile) {
		validateDataFormat(profile);
		validateMinimumAge(profile);
		validateDuplicated(profile);
		validateCardIssuerBlocked(profile);
	}

	private void validateCardIssuerBlocked(UserProfile profile) {
		String issuer = profile.getPaymentCardNumber().substring(0, 6);
		if (cardIssuerBlocked.contains(issuer)) {
			throw new ContentNotAcceptableException("Card issuer not allowed");
		}
	}

	private void validateDuplicated(UserProfile profile) {
		Optional<UserProfile> duplicated = userProfileDate.find(profile.getUsername());
		if (duplicated.isPresent()) {
			throw new DuplicatedException("Username already in use");
		}
	}

	private void validateMinimumAge(UserProfile profile) {
		if (profile.getDob().isAfter(LocalDate.now().minusYears(MINIMUN_AGE)) ) {
			throw new ForbiddenException("Minimum age is 18");
		}
	}

	private void validateDataFormat(UserProfile profile) {
		Set<ConstraintViolation<Object>> violations = validator.validate(profile, Default.class);
		
		if (!violations.isEmpty()) {
			List<String> invalids = violations.stream()
					.map(e -> e.getPropertyPath().toString())
					.collect(Collectors.toList());
			
			throw new DataFormatException("Invalid or missing field: " + invalids );
		}
	}
	

}

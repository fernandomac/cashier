package com.cashier.service;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.cashier.domain.UserProfile;
import com.cashier.exception.ContentNotAcceptableException;
import com.cashier.exception.DataFormatException;
import com.cashier.exception.DuplicatedException;
import com.cashier.exception.ForbiddenException;
import com.cashier.model.RegisterRequest;
import com.cashier.repository.UserProfileDao;
import com.cashier.test.support.UserMatcher;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

	@Mock
	private UserProfileDao userProfileDao;
	
	private UserProfileServiceImpl service;
	
	@Before
	public void setUp() {
		List<String> cardIssuerBlockedList = new ArrayList<>();
		cardIssuerBlockedList.add("123456");
		service = new UserProfileServiceImpl(userProfileDao, new ModelMapper(), 
				Validation.buildDefaultValidatorFactory().getValidator(), cardIssuerBlockedList);
	}
	
	@Test
	public void shouldRegisterUser() {
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pas1");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername("UserTest");
		
		service.register(request);
		
		verify(userProfileDao).find(eq("UserTest"));
		verify(userProfileDao).add(argThat(new UserMatcher("UserTest", "Pas1", LocalDate.of(2000, 5, 25), "349293081054422")));
		verifyNoMoreInteractions(userProfileDao);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidNameWhitespace() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass1234");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername("User Test");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidNameSpecialChar() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass1234");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername("User%Test");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidNameNull() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass1234");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername(null);
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidCardNumberTooShort() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass1234");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("34929308105442");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidCardNumberTooLong() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass1234");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("34929308105442233344");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidCardNumberNull() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass1234");
		request.setUsername("UserTest");
		request.setPaymentCardNumber(null);
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidCardNumberNotNumeric() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass1234");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("34929308105442A");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidPasswordTooShort() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pa1");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("349293081054425");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidPasswordMissingUpperCaseLetter() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("pas1");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("349293081054425");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidPasswordMissingNumber() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Passone");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("349293081054425");
		
		service.register(request);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidPasswordWhitespace() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pass 1");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("349293081054425");
		
		service.register(request);
	}
	
	@Test
	public void shouldRegisterUserStrongPasswordSpecialChars() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pas1#!@&ˆ*={]()=#%$#%$");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername("UserTest");
		
		service.register(request);
		
		verify(userProfileDao).find(eq("UserTest"));
		verify(userProfileDao).add(argThat(new UserMatcher("UserTest", "Pas1#!@&ˆ*={]()=#%$#%$", LocalDate.of(2000, 5, 25), "349293081054422")));
		verifyNoMoreInteractions(userProfileDao);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserInvalidPasswordNull() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setUsername("UserTest");
		request.setPaymentCardNumber("349293081054422");
		request.setPassword(null);
		
		service.register(request);
		
		verify(userProfileDao).find(eq("UserTest"));
		verifyNoMoreInteractions(userProfileDao);
	}
	
	@Test(expected=DataFormatException.class)
	public void shouldNotRegisterUserDoBNull() {
		
		RegisterRequest request = new RegisterRequest();
		request.setPassword("Pass01");
		request.setUsername("UserTest");
		request.setPaymentCardNumber("349293081054422");
		request.setDob(null);
		
		service.register(request);
		
		verify(userProfileDao).find(eq("UserTest"));
		verifyNoMoreInteractions(userProfileDao);
	}
	
	@Test(expected=ForbiddenException.class)
	public void shouldNotRegisterUserYoungerThen18() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.now().minusYears(18).plusDays(1L));
		request.setPassword("Pas1");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername("UserTest");
		
		service.register(request);
	}
	
	@Test
	public void shouldRegisterUserAt18Anniversary() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.now().minusYears(18));
		request.setPassword("Pas1");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername("UserTest");
		
		service.register(request);
		
		verify(userProfileDao).find(eq("UserTest"));
		verify(userProfileDao).add(argThat(new UserMatcher("UserTest", "Pas1", LocalDate.now().minusYears(18), "349293081054422")));
		verifyNoMoreInteractions(userProfileDao);
	}
	
	@Test(expected=DuplicatedException.class)
	public void shouldNotRegisterUserSameUsernameTwice() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.now().minusYears(18));
		request.setPassword("Pas1");
		request.setPaymentCardNumber("349293081054422");
		request.setUsername("UserTest");
		
		when(userProfileDao.find(eq("UserTest"))).thenReturn(Optional.of(new UserProfile()));
		
		service.register(request);
	}
	
	@Test(expected=ContentNotAcceptableException.class)
	public void shouldNotRegisterUserCardIssuerBlocked() {
		
		RegisterRequest request = new RegisterRequest();
		request.setDob(LocalDate.of(2000, 5, 25));
		request.setPassword("Pas1");
		request.setPaymentCardNumber("123456081054422");
		request.setUsername("UserTest");
		
		service.register(request);
	}
	
}

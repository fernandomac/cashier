package com.cashier.web.endpoint;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cashier.exception.ContentNotAcceptableException;
import com.cashier.exception.DataFormatException;
import com.cashier.exception.DuplicatedException;
import com.cashier.exception.ForbiddenException;
import com.cashier.model.RegisterRequest;
import com.cashier.service.UserProfileService;
import com.cashier.test.support.RegisterMatcher;
import com.cashier.test.support.TestSampleData;

public class UserProfileControllerTest {
	 
	@InjectMocks
	private UserProfileController controller;
	
	@Mock
	private UserProfileService service;
	
	protected MockMvc mockMvc;
	protected HttpHeaders httpHeaders;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
    public void shouldRegisterNewUser() throws Exception {
		
		mockMvc.perform(
				post("/register")
					.headers(httpHeaders)
					.content(TestSampleData.REGISTER_USER_JSON))
		
				.andExpect(status().isCreated());
		
		verify(service).register(argThat(new RegisterMatcher("BobFrench", "Password1" , LocalDate.of(1980, 02, 21), "349293081054422")));
		verifyNoMoreInteractions(service);
    }
	
	@Test
    public void shouldNotRegisterInvalidDataFormat() throws Exception {
		Long id = 12345L;
		
		doThrow(new DataFormatException("validation error")).when(service).register(any(RegisterRequest.class));
		
		mockMvc.perform(
				post("/register", id)
					.headers(httpHeaders)
					.content(TestSampleData.REGISTER_USER_JSON))
		
				.andExpect(status().isBadRequest());
		
		verify(service).register(argThat(new RegisterMatcher("BobFrench", "Password1" , LocalDate.of(1980, 02, 21), "349293081054422")));
		verifyNoMoreInteractions(service);
    }
	
	@Test
    public void shouldNotRegisterDuplicated() throws Exception {
		Long id = 12345L;
		
		doThrow(new DuplicatedException("duplicated error")).when(service).register(any(RegisterRequest.class));
		
		mockMvc.perform(
				post("/register", id)
					.headers(httpHeaders)
					.content(TestSampleData.REGISTER_USER_JSON))
		
				.andExpect(status().isConflict());
		
		verify(service).register(argThat(new RegisterMatcher("BobFrench", "Password1" , LocalDate.of(1980, 02, 21), "349293081054422")));
		verifyNoMoreInteractions(service);
    }
	
	@Test
    public void shouldNotRegisterInvalidAge() throws Exception {
		Long id = 12345L;
		
		doThrow(new ForbiddenException("age error")).when(service).register(any(RegisterRequest.class));
		
		mockMvc.perform(
				post("/register", id)
					.headers(httpHeaders)
					.content(TestSampleData.REGISTER_USER_JSON))
		
				.andExpect(status().isForbidden());
		
		verify(service).register(argThat(new RegisterMatcher("BobFrench", "Password1" , LocalDate.of(1980, 02, 21), "349293081054422")));
		verifyNoMoreInteractions(service);
    }
	
	@Test
    public void shouldNotRegisterCardIssuerBlocked() throws Exception {
		Long id = 12345L;
		
		doThrow(new ContentNotAcceptableException("not acceptable error")).when(service).register(any(RegisterRequest.class));
		
		mockMvc.perform(
				post("/register", id)
					.headers(httpHeaders)
					.content(TestSampleData.REGISTER_USER_JSON))
		
				.andExpect(status().isNotAcceptable());
		
		verify(service).register(argThat(new RegisterMatcher("BobFrench", "Password1" , LocalDate.of(1980, 02, 21), "349293081054422")));
		verifyNoMoreInteractions(service);
    }
	
	
}

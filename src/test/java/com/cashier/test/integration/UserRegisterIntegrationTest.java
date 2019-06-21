package com.cashier.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cashier.test.support.TestSampleData;
import com.cashier.web.config.ApplicationConfig;
import com.cashier.web.endpoint.UserProfileController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class)
public class UserRegisterIntegrationTest {
	
	@Autowired
	private UserProfileController controller;
	
	protected MockMvc mockMvc;
	protected HttpHeaders httpHeaders;
	
	@BeforeClass
	public static void setProperties() {
		System.setProperty("card.issuer.blocked.list", "123456,789123");
	}
	
	@Before
	public void setUp() {
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
    public void shouldRegisterNewUser() throws Exception {
		mockMvc.perform(
				post("/register")
					.headers(httpHeaders)
					.content(TestSampleData.buildRegisterRequest("username1")))
				.andExpect(status().isCreated());
    }
	
	@Test
    public void shouldNotRegisterSameUsernameTwice() throws Exception {
		mockMvc.perform(
				post("/register")
					.headers(httpHeaders)
					.content(TestSampleData.buildRegisterRequest("username2")))
				.andExpect(status().isCreated());
		
		mockMvc.perform(
				post("/register")
					.headers(httpHeaders)
					.content(TestSampleData.buildRegisterRequest("username2")))
				.andExpect(status().isConflict());
    }
	
	@Test
    public void shouldNotRegisterInvalidDataFormat() throws Exception {
		mockMvc.perform(
				post("/register")
					.headers(httpHeaders)
					.content(TestSampleData.buildRegisterRequest("username3", "invalidpass", "1983-07-04", "888456789101112")))
				.andExpect(status().isBadRequest());
    }
	
	@Test
    public void shouldNotRegisterBlockedCardIssuer() throws Exception {
		mockMvc.perform(
				post("/register")
					.headers(httpHeaders)
					.content(TestSampleData.buildRegisterRequest("username4", "ValidPass1", "1983-07-04", "123456789101112")))
				.andExpect(status().isNotAcceptable());
    }
	
	@Test
    public void shouldNotRegisterYoungUser() throws Exception {
		mockMvc.perform(
				post("/register")
					.headers(httpHeaders)
					.content(TestSampleData.buildRegisterRequest("username4", "ValidPass1", "2012-07-04", "8888456789101112")))
				.andExpect(status().isForbidden());
    }
	

}

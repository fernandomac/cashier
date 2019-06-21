package com.cashier.web.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cashier.model.RegisterRequest;
import com.cashier.service.UserProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping
@Api(value = "User profile API.")
public class UserProfileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private UserProfileService service;
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus( HttpStatus.CREATED)
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Successfully created a new user"),
	    @ApiResponse(code = 400, message = "Invalid information provided"),
	    @ApiResponse(code = 403, message = "User younger than expected"),
	    @ApiResponse(code = 406, message = "Issuer identification number blocked"),
	    @ApiResponse(code = 409, message = "Username already in use")
	})
    public void register(@RequestBody RegisterRequest request) {
		
		LOGGER.debug("NEW USER: {}", request);
		
		service.register(request);
    }
}

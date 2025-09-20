package com.in28minutes.hbs.jwt;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class JwtController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private RSAKey rsaKey;
	
	@GetMapping("/hello")
	@PreAuthorize("hasRole('ADMIN')")
	public String getMethodName() {
		return "Hello World";
	}
	
	
	@PostMapping("/authenticate")
	public JwtResponse authenticate(Authentication authentication) {
		System.out.println(authentication);
		return new JwtResponse(jwtService.authenticateUser(authentication));
		
	}
	
	@PostMapping("/register")
	public String newUser(@RequestBody RegisterRequest registerRequest) {
		return jwtService.addUser(registerRequest);
	}
	
	@PostMapping("/createadmin/{user}")
	@PreAuthorize("hasRole('ADMIN')")
	public String createAdmin(@PathVariable String user) {
		
		return jwtService.createAdmin(user);
	}
	
	@GetMapping("/publicKey")
	public String retrievePublicKey() throws JOSEException {
		return rsaKey.toRSAPublicKey().toString();
	}
	
	
}

record JwtResponse(String token) {}
	
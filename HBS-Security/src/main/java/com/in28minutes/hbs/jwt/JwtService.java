package com.in28minutes.hbs.jwt;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	
	@Autowired
	private JwtSecurityConfig jwtSecurityConfig;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Autowired
	private JwtEncoder jwtEncoder;

	public String authenticateUser(Authentication authentication) {
		
		var claims = JwtClaimsSet.builder()
								.issuer("self")
								.issuedAt(Instant.now())
								.expiresAt(Instant.now().plusSeconds(15*60))
								.subject(authentication.getName())
								.claim("Scope", createScope(authentication))
								.build();
		
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	private String createScope(Authentication authentication) {
		return authentication
					.getAuthorities()
					.stream()
					.map(a -> a.getAuthority())
					.collect(Collectors.joining(" "));
	}

	public String addUser(RegisterRequest registerRequest) {
		if(jwtSecurityConfig.getJdbcUserDetailsManager().userExists(registerRequest.getUsername()))
			return "User already exists!";
		
		UserDetails newUserDetails = User.withUsername(registerRequest.getUsername())
											.password(passwordEncoder.encode(registerRequest.getPassword()))
											.roles("USER")
											.build();
		
		jwtSecurityConfig.getJdbcUserDetailsManager().createUser(newUserDetails);
		return "User registered successfully!";
	}

	public String createAdmin(String user) {
		if(!jwtSecurityConfig.getJdbcUserDetailsManager().userExists(user))
			return "User not found!";
		UserDetails userDetails = jwtSecurityConfig.getJdbcUserDetailsManager().loadUserByUsername(user);
		UserDetails updatedUserDetails = User.withUserDetails(userDetails)
				.roles("USER","ADMIN")
				.build();
		jwtSecurityConfig.getJdbcUserDetailsManager().updateUser(updatedUserDetails);
		return String.format("%s is now admin", user);
	}

	public String getPublicKey() {
		
		return "";
	}

}

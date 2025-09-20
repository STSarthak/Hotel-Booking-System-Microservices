package com.in28minutes.hbs.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityConfig {
	
	private JdbcUserDetailsManager jdbcUserDetailsManager;
	
	
	
    public JdbcUserDetailsManager getJdbcUserDetailsManager() {
		return jdbcUserDetailsManager;
	}

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http.
					authorizeHttpRequests(auth -> auth
							 					.requestMatchers("/authenticate","/register","/h2-console/**").permitAll()
							 					.requestMatchers("/createadmin/**").hasRole("ADMIN")
												.anyRequest()
												.authenticated()							
											)
					.csrf(csrf -> csrf.disable())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.httpBasic(Customizer.withDefaults())
					.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
					.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
					.build();
	}
	
	
	
    @Bean
    public DataSource dataSource() {
    	return new EmbeddedDatabaseBuilder()
    				.setType(EmbeddedDatabaseType.H2)
    				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
    				.build();
    }
    
//    @Bean
//	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
//		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//		return jdbcUserDetailsManager;
//	}
    
    
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
    	
    	 jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    	if(!jdbcUserDetailsManager.userExists("user")) {
	    	UserDetails user = User.withUsername("user")
	    					.password(passwordEncoder().encode("user"))
	    					.roles("USER")
	    					.build();
	    	jdbcUserDetailsManager.createUser(user);
    	}
    	
    	if(!jdbcUserDetailsManager.userExists("admin")) {
    		UserDetails admin = User.withUsername("admin")
    								.password(passwordEncoder().encode("user"))
    								.roles("USER","ADMIN")
    								.build();
    		jdbcUserDetailsManager.createUser(admin);
    	}
    	
    	return jdbcUserDetailsManager;
    	
    }

    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
	
    @Bean
    public KeyPair keyPair() {
    	try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
    	
    }
    
    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
    	return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
    					.privateKey(keyPair.getPrivate())
    					.keyID(UUID.randomUUID().toString())
    					.build();
    }
    
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
    	JWKSet jwkSet = new JWKSet(rsaKey);
    	return (jwkSelector,context) -> jwkSelector.select(jwkSet);
    }
    
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
    	return new NimbusJwtEncoder(jwkSource);
    }
    
    @Bean 
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
    	return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    
    
    
    
    
    
}

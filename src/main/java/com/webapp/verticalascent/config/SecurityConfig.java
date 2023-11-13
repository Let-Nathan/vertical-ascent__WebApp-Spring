package com.webapp.verticalascent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Implementation of Spring security.
 * Allow to filter url and perform authentication .
 *
 * @author Nathan L.
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
	
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	/**
	 * Security configuration for the application.
	 * This configuration defines access rules, authentication management,
	 * and session management for different URLs in the application.
	 * - Static resources (CSS, JavaScript, images) are publicly accessible.
	 * - Access to the "/account" page requires the "USER" role.
	 * - Other URLs require authentication.
	 * - The login page is customized for "/signin".
	 * - Password encoding is managed with BCryptPasswordEncoder.
	 * - Logout redirects to "/login?logout".
	 */
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/account").hasRole("USER")
				.requestMatchers("/**", "/logout").permitAll()
				.requestMatchers(
					"/css/**", "/javascript/**", "/images/**"
				).permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(httpSecurityFormLoginConfigurer ->
				httpSecurityFormLoginConfigurer
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/")
					.usernameParameter("email")
					.passwordParameter("password")
			)
			.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			)
			.sessionManagement(Customizer.withDefaults());
		return http.build();
	}
	
	/**
	 * Configuration for password encryption.
	 * Use to store secured password.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

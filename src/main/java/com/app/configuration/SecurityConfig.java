package com.app.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.jwt.JwtAuthenticationFilter;
import com.app.service.UserDetailsServiceImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//aqui se pondran los bean (@ ejemplo entity,service etc)
//@EnableWebSecurity(debug = true)
@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> 
												auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
												.requestMatchers(HttpMethod.GET, 
																		"/api/ping", 
																		"/doc/swagger-ui/**", 
																		"/doc/swagger-ui.html", 
																		"/v3/api-docs/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/scrapyardparts/scrapyards/{scrapYardId}").hasRole("SCRAPYARD")
				.requestMatchers(HttpMethod.DELETE, "/api/scrapyardparts/scrapyards").hasRole("SCRAPYARD")
				.requestMatchers(HttpMethod.POST, "/api/scrapyardparts").hasRole("SCRAPYARD")
				.requestMatchers(HttpMethod.POST, "/api/reservation").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/reservation").hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/api/reservation/{reservationId}").hasRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/**").permitAll()
				.requestMatchers(HttpMethod.PUT, "/api/**").permitAll()
				//.requestMatchers(HttpMethod.GET, "/api/scrapyards").permitAll()

							// .requestMatchers(HttpMethod.GET, "/api/writers/onlycreate").hasAuthority("CREATE")
				.anyRequest().authenticated()
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);

	    return source;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { // se puede inyectar por constructor el authCon

		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);

		return provider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {

		// return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
/**
	@Bean
	UserDetailsService userDetailService() {
		UserDetails userDetails = User.withUsername("alexis")
				.password("1234")
				.roles("ADMIN")
				.authorities("READ", "CREATE")
				.build();
		
		List<UserDetails> userDetailsList = new ArrayList<>();
		userDetailsList.add(userDetails);
		
		userDetailsList.add(User.withUsername("pepe")
				.password("pepe")
				.roles("USER")
				.authorities("READ")
				.build());

		return new InMemoryUserDetailsManager(userDetailsList);
	}
	
**/
}
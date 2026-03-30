package com.example.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;





@Configuration
public class SecurityConfig {
	
	@Bean
	@Autowired
	SecurityFilterChain securityFilterChain(HttpSecurity http,JWTValidationFilter jwtValidationFilter) throws Exception {
		
		//En una API Rest Full no existen las sesiones
		http
		.sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http
		.authorizeHttpRequests(auth ->{
			
			
			
			
			//auth.anyRequest().hasRole("USER");//Es obligatorio para spring que en la bbdd este anotado como "ROLE_'LOQUESEA'"
			//y aqui "LOQUESEA"
			//auth.requestMatchers("usuario/usuarioList").hasRole("ADMIN");

			
			auth.requestMatchers("/authenticate","usuario/usuarioList").permitAll();
			//auth.requestMatchers("/asistencia/**").authenticated();
			
			auth.anyRequest().permitAll();
			
			
			
				
			
		});//.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
		
	http.addFilterAfter(jwtValidationFilter,BasicAuthenticationFilter.class);
		
		http.cors(cors->corsConfigurationSource());
		//http.cors(cors -> cors.disable());
		
		http.csrf(csrf->csrf.disable());
		
		return http.build();
		
	}
	
	
	/*
	 * Una aplicación Spring Security solo puede tener un password encoder.
	 * Y además es obligatorio que lo tenga.
	 * Al estar anotado como @Bean esta permanentemente "cargado" en
	 * la aplicación y por lo tanto es el que está activo.
	 * 
	 */
	
	@Bean  //Este método devuelve un @Bean que es password encoder que hemos elegido y que será el que utilicemos hallá donde lo necesitemos
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		
		return configuration.getAuthenticationManager();
		
	}
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		var config = new CorsConfiguration();
		
		config.setAllowedOrigins(List.of("*"));
		config.setAllowedMethods(List.of("*"));
		config.setAllowedHeaders(List.of("*"));
		
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
		
		
	}
	
	

	
	

}

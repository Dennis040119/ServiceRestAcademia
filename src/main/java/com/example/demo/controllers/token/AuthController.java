package com.example.demo.controllers.token;



import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.security.JWTRequest;
import com.example.demo.security.JWTResponse;
import com.example.demo.security.JWTService;
import com.example.demo.security.JWTUserDetailsService;


@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	
	private final AuthenticationManager authenticationManager = null;
	
	private final JWTUserDetailsService jwtUserDetailsService = new JWTUserDetailsService();
	
	private final JWTService jwtService = new JWTService();
	
	@PostMapping("/authenticate")
	public ResponseEntity<?>  postToken(@RequestBody JWTRequest request){
		
		this.authenticate(request);
		
		var userDetails = this.jwtUserDetailsService.loadUserByUsername(request.getUsername());
		
		final String token = jwtService.generateToken(userDetails);
		
		HttpHeaders headers=new HttpHeaders();
		
		headers.set("Authorization", "Bearer " + token);
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(new JWTResponse(token));
		
	}
	
	
	
	private void authenticate(JWTRequest request) {
		
		try {
			System.out.println(">>>>>>>"+request);
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
			
		}catch(BadCredentialsException e) {
			
			System.out.println(e);
			throw new RuntimeException("Credenciales erroneas, usuario no registrado");
			
		}catch(DisabledException e) {
			
			throw new RuntimeException(e.getMessage());
		}
		
		
	}
	
	

}

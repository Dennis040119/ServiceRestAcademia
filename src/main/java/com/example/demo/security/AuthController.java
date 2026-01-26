package com.example.demo.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Controller
public class AuthController {
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private final JWTUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private final JWTService jwtService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?>  postToken(@RequestBody JWTRequest request){
		
		this.authenticate(request);
		
		var userDetails = this.jwtUserDetailsService.loadUserByUsername(request.getUsername());
		
		final String token = jwtService.generateToken(userDetails);
		
		HttpHeaders headers = new HttpHeaders();
		
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

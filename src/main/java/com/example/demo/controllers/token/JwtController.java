package com.example.demo.controllers.token;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@Controller
@RequestMapping("/token")
@CrossOrigin(origins = "http://localhost:4200")
public class JwtController {
	
	@GetMapping("/generate-token")		
	@ResponseBody		
	public String GenerateToken() {
		
		Date fecha = new Date();
		Instant instante = Instant.now();
		Instant nuevoInstante= instante.plus(Duration.ofMinutes(15));
		
		final String SECRET_KEY =
		        "esta_es_una_clave_segura_de_32_caracteres_minimo_1234";

		final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		try {
			
		
		SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
		String stringSigAlg =sigAlg.toString();
		
		//Creando header del token 
		HashMap<String, Object> header = new HashMap<String,Object>();
	    header.put("alg", stringSigAlg);
	    header.put("typ","JWT");
	    
	    //Seteando el header al token
	    JwtBuilder token = Jwts.builder()
	    .setHeader(header)
	    .setId("1")
	    .setIssuer("http://trustyapp.com/")
	    .setSubject("www.Academia")
	    .claim("name", "Dennis Gonza")
	    .claim("scope", "admin")
	    .setIssuedAt(fecha.from(Instant.now()))
		// Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
		.setExpiration(fecha.from(nuevoInstante))
		.signWith(key, SignatureAlgorithm.HS256);
	    
	    String tokenString = token.compact();
	    
	    
		
		return tokenString;
	    
		} catch (Exception e) {
			return "Error creando el token ";
		}
	    
	    
	}


}

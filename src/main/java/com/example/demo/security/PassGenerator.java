package com.example.demo.security;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassGenerator {
	
	 public static void main(String ...args) {
	    BCryptPasswordEncoder Crypt = new BCryptPasswordEncoder(4);
	    BCrypt cripto = new BCrypt();
	    
	    
		System.out.println(PassGenerator.desecryp("123","$2a$04$p9LI0cX5D/52dYHSG5NaE.kcaR.80tt94QwMgoMaFLs7Mutj2uSXS"));
		
	    }
	 
	 public static String CrearContra(String clave)
	 {
		 BCryptPasswordEncoder Crypt = new BCryptPasswordEncoder(4);
		 BCrypt cripto = new BCrypt();
		 
		 String cla=Crypt.encode(clave);
		 return cla;
	 }
	 public static Boolean desecryp(String frontPass,String BdPass)
	 {
		 BCryptPasswordEncoder Crypt = new BCryptPasswordEncoder(4);
		 Boolean bol=Crypt.matches(frontPass,BdPass);
		 
		 return bol;
	 }
	 
	

}

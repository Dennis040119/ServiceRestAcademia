package com.example.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuariosRepository;







@Service
public class JWTUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuariosRepository usuarioDAO;
	
	/*
	 * Tenemos anotado un objeto de la clase UserDetailsService como @Servicio
	 * y es lo único que necesita SpringSecurity para tener un User ACTIVO
	 * que se suele cargar generalmente desde una BBDD.
	 * Es muy importante porque es así como SpringSecurity conoce el nombre
	 * del usuario (ACTIVO), la contraseña etc (en nuestro caso el e-mail que
	 * va a actuar como usuario y consigue realizar el proceso de autenticación.
	 * */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Usuario usu = new Usuario(); 
		
		
	
		return usuarioDAO.findOneByUsername(username).map(usuario ->{
		
			//Si la BBDD devuelve un solo valor de un campo "rol" de la BBDD
			var authorities = List.of(new SimpleGrantedAuthority(usuario.getRol()));
			
			//Si la BBDD devuelve un set o un list...es decir varios roles...
			/*var roles = usuario.getRoles();//Existiendo en la tabla de usuarios una relación de muchos a muchos con la tabla roles
			var authorities = roles.stream()
					.map(rol -> new SimpleGrantedAuthority(rol.getName())
					.collect(Collectors.toList()));*/
				
			
			usu.setUsername(usuario.getUsername());
			usu.setPassword(usuario.getPassword());
			usu.setRol(usuario.getRol());
			//User extiende UserDetails 
			System.out.println(">>>>>>>>>>"+usuarioDAO.findOneByUsername(username).get());
			return new User(usuario.getUsername(),usuario.getPassword(),authorities);
			
		}).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado en la BBDD: "+usu.toString()));
		
		
	}

}
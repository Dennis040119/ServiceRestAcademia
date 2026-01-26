package com.example.demo.controllers.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Usuario;
import com.example.demo.security.PassGenerator;
import com.example.demo.services.UsuarioServiceImp;

@RestController
@Controller
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuariosController {
	
	@Autowired
	UsuarioServiceImp service;
	
	@GetMapping("/usuarioList")
	@ResponseBody
	public ResponseEntity<?> listaUsuario() {
		
		Map<String, Object> response = new HashMap<>();
		List<Usuario> lista = null;
		try {
			 lista = service.listar();
		}
		catch(DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la petición en la BBDD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
	}
	
	
	@GetMapping("/buscarByUsername/{user}")
	@ResponseBody
	public Optional<Usuario> buscarUsuarioByUsername(@PathVariable String user) {
		
		Map<String, Object> response = new HashMap<>();
		Optional<Usuario> usu = null;
		try {
			 usu = service.BuscarPorUser(user);
			System.out.println(usu);
			 if(usu.isPresent()) {
				 return usu;
			 }else {
				 return usu;
			 }
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return usu;
			
		}	
		
		
	}
	
	
	
	@PostMapping("/usuarioSave")
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> SaveUser(@RequestBody Usuario obj) {
		
		//CREAMOS UN MAP, QUE ALMACENARA LOS MENSAJES DE EXITOS O ERRORES
		Map<String, Object> salida = new HashMap<>();
		//Intentamos la transaction
		
		Optional<Usuario> usu = service.BuscarPorUser(obj.getUsername());
		if(usu.isPresent() ){
			salida.put("mensaje", "Ya existe el Usuario");
			
		}else {
			System.out.println("\n"+obj);
			try {
				
				obj.setUserid(Usuario.generarcodigo(service.listar().size()));
				String claveencryp=PassGenerator.CrearContra(obj.getPassword());
				obj.setPassword(claveencryp);
				
				
				service.save(obj);
				salida.put("obj", obj);
				salida.put("mensaje", "Registrado usuario correctamente");
			} catch (Exception e) {salida.put("mensaje", "Error al registrar Usuario: " +e);}
		}
		
		
		
		return ResponseEntity.ok(salida);
	}

}

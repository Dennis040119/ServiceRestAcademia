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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Usuario;
import com.example.demo.security.PassGenerator;
import com.example.demo.services.impl.UsuarioServiceImp;

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
	
	

			@PutMapping("/usuarioPut/{id}")
			@ResponseBody
			public ResponseEntity<Map<String, Object>> updateUsuario(
			        @PathVariable("id") String userid,
			        @RequestBody Usuario cambios) {
			
			    Map<String, Object> salida = new HashMap<>();
			
			    try {
			        // 1) Buscar existente por userid
			        Optional<Usuario> opt = service.buscar(userid); // Asegúrate de tener este método por ID
			        if (opt.isEmpty()) {
			            salida.put("mensaje", "No existe el Usuario con id " + userid);
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(salida);
			        }
			
			        Usuario existente = opt.get();
			
			        // 2) Inmutables: userid y estado
			        final String estadoFijo = existente.getEstado(); // se conserva
			        final String idFijo = existente.getUserid();     // por claridad
			
			        // 3) Si cambia username, validar que no exista en otro registro
			        if (cambios.getUsername() != null && !cambios.getUsername().equalsIgnoreCase(existente.getUsername())) {
			            Optional<Usuario> porUser = service.BuscarPorUser(cambios.getUsername());
			            if (porUser.isPresent() && !porUser.get().getUserid().equals(idFijo)) {
			                salida.put("mensaje", "El nombre de usuario ya está en uso");
			                return ResponseEntity.status(HttpStatus.CONFLICT).body(salida);
			            }
			            existente.setUsername(cambios.getUsername());
			        }
			
			        // 4) Password: si viene no vacía, re-encriptar; si no, mantener
			        if (cambios.getPassword() != null && !cambios.getPassword().isBlank()) {
			            String claveencryp = PassGenerator.CrearContra(cambios.getPassword());
			            existente.setPassword(claveencryp);
			        }
			        // Si viene null o en blanco, no tocamos la password
			
			        // 5) Campos actualizables (si vienen no nulos)
			        if (cambios.getEmail() != null)  existente.setEmail(cambios.getEmail());
			        if (cambios.getRol() != null)    existente.setRol(cambios.getRol());
			        if (cambios.getImagen() != null) existente.setImagen(cambios.getImagen());
			        // NOTA: cambios.getEstado() se ignora para mantener el estado original
			
			        // 6) Reafirmar inmutables
			        existente.setUserid(idFijo);
			        existente.setEstado(estadoFijo);
			
			        // 7) Guardar
			        service.save(existente);
			
			        salida.put("obj", existente);
			        salida.put("mensaje", "Usuario actualizado correctamente");
			        return ResponseEntity.ok(salida);
			
			    } catch (Exception e) {
			        salida.put("mensaje", "Error al actualizar Usuario: " + e.getMessage());
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(salida);
			    }
			}


}

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

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Docente;
import com.example.demo.entity.Usuario;
import com.example.demo.services.DocenteServiceImpl;

@RestController
@Controller
@RequestMapping("/docente")
@CrossOrigin(origins = "http://localhost:4200")
public class DocenteController {
	
	@Autowired
	DocenteServiceImpl service;
	
	@GetMapping("/docenteList")
	@ResponseBody
	public ResponseEntity<?> listaUsuario() {
		
		Map<String, Object> response = new HashMap<>();
		List<Docente> lista = null;
		try {
			 lista = service.listar();
			 System.out.println(lista);
		}
		catch(DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la petición en la BBDD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		System.out.println(lista);
		return new ResponseEntity<List<Docente>>(lista, HttpStatus.OK);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/buscarByNombre/{nombre}")
	@ResponseBody
	public Optional<Docente> buscarUsuarioByUsername(@PathVariable String nombre) {
		
		Map<String, Object> response = new HashMap<>();
		Optional<Docente> usu = null;
		try {
			 usu = service.BuscarPorNombre(nombre);
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
	///////////////////////////////////////////////////
	@GetMapping("/buscarById/{id}")
	@ResponseBody
	public Optional<Docente> buscarPorId(@PathVariable String id) {
		
		Map<String, Object> response = new HashMap<>();
		Optional<Docente> usu = null;
		try {
			 usu = service.buscar(id);
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
	//////////////////////////////////////////////////
	@PostMapping("/docenteSave")
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> SaveDoc(@RequestBody Docente obj) {
		
		//CREAMOS UN MAP, QUE ALMACENARA LOS MENSAJES DE EXITOS O ERRORES
		Map<String, Object> salida = new HashMap<>();
		//Intentamos la transaction
		
		Optional<Docente> doc = service.buscar(obj.getId_docentes());
		if(doc.isPresent() ){
			salida.put("mensaje", "Ya existe el Usuario");
			
		}else {
			
			try {
				
				obj.setId_docentes(Docente.generarcodigo(service.listar().size()));
				
				
				System.out.println("\n"+obj);
				service.save(obj);
				salida.put("obj", obj);
				salida.put("mensaje", "Registrado Docente correctamente");
			} catch (Exception e) {salida.put("mensaje", "Error al registrar: " +e);}
		}
		return ResponseEntity.ok(salida);
		
	}

}

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
import com.example.demo.entity.Usuario;
import com.example.demo.security.PassGenerator;
import com.example.demo.services.impl.AlumnoServiceImpl;

@RestController
@Controller
@RequestMapping("/alumno")
@CrossOrigin(origins = "http://localhost:4200")
public class AlumnoController {
	
	@Autowired
	AlumnoServiceImpl service;
	
	@GetMapping("")
	@ResponseBody
	public ResponseEntity<?> listaAlumno() {
		
		Map<String, Object> response = new HashMap<>();
		List<Alumno> lista = null;
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
		return new ResponseEntity<List<Alumno>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{grado}")
	@ResponseBody
	public ResponseEntity<?> listarByGrado(@PathVariable String grado) {
		
		Map<String, Object> response = new HashMap<>();
		List<Alumno> lista = null;
		try {
			 lista = service.listarPorGrado(grado);
			 System.out.println(lista);
		}
		catch(DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la petición en la BBDD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		System.out.println(lista);
		return new ResponseEntity<List<Alumno>>(lista, HttpStatus.OK);
	}
	
	
	@GetMapping("/buscarByUserNombre/{nombre}")
	@ResponseBody
	public Optional<Alumno> buscarAlumnoByname(@PathVariable String nombre) {
		
		Optional<Alumno> usu = null;
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
	
	
	@PostMapping("/alumnoSave")
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> SaveAlu(@RequestBody Alumno obj) {
		
		//CREAMOS UN MAP, QUE ALMACENARA LOS MENSAJES DE EXITOS O ERRORES
		Map<String, Object> salida = new HashMap<>();
		//Intentamos la transaction
		
		Optional<Alumno> alu = service.buscar(obj.getId());
		if(alu.isPresent() ){
			salida.put("mensaje", "Ya existe el Usuario");
			
		}else {
			System.out.println("\n"+obj);
			try {
				
				obj.setId(Alumno.generarcodigo(service.listar().size()));
				
				
				
				service.save(obj);
				salida.put("obj", obj);
				salida.put("mensaje", "Registrado Alumno correctamente");
			} catch (Exception e) {salida.put("mensaje", "Error al registrar: " +e);}
		}
		
		
		
		return ResponseEntity.ok(salida);
	}

}

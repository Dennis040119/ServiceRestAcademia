package com.example.demo.controllers.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.example.demo.entity.Notas;
import com.example.demo.entity.Usuario;
import com.example.demo.security.PassGenerator;
import com.example.demo.services.impl.NotaServiceImpl;
import com.example.demo.services.impl.UsuarioServiceImp;

@RestController
@Controller
@RequestMapping("/notas")
@CrossOrigin(origins = "http://localhost:4200")
public class NotasController {
	
	@Autowired
	NotaServiceImpl service;
	
	@GetMapping("/notasList")
	@ResponseBody
	public ResponseEntity<?> listaNota() {
		
		Map<String, Object> response = new HashMap<>();
		List<Notas> lista = null;
		try {
			 lista = service.listar();
		}
		catch(DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la petición en la BBDD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		
		return new ResponseEntity<List<Notas>>(lista, HttpStatus.OK);
	}
	
	
	@GetMapping("/buscarNota/{alumnoid}/{cursoid}")
	@ResponseBody
	public ResponseEntity<?> buscarNotas(@PathVariable String alumnoid,@PathVariable String cursoid) {
		
		Map<String, Object> response = new HashMap<>();
		List<Notas> lista = null;
		List<Notas> lista2 = null;
		
		System.out.println("La alumno id es: " +alumnoid + "  el curso id es: "+cursoid);
		
		Notas nota= new Notas();
		try {
			 lista = service.listar();
			 lista2=lista.stream().filter(a -> (a.getAlumno_id().equals(alumnoid)  && a.getCurso_id().equals(cursoid)) )
					 .collect(Collectors.toList());
			
			 if(lista2.isEmpty()) {
				 
			 }else {
				 nota = lista2.get(0);
			 }
			 
		}
		catch(DataAccessException e) {
			
			response.put("mensaje", "Error al realizar la petición en la BBDD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
		
		return new ResponseEntity<Object>(nota, HttpStatus.OK);
	}
	
	
	@PostMapping("/notaSave")
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> SaveNota(@RequestBody Notas obj) {
		
		//CREAMOS UN MAP, QUE ALMACENARA LOS MENSAJES DE EXITOS O ERRORES
		Map<String, Object> salida = new HashMap<>();
		//Intentamos la transaction
		
		
			System.out.println("\n"+obj);
			try {
				
				
				
				
				service.save(obj);
				salida.put("obj", obj);
				salida.put("mensaje", "Registrado nota correctamente");
			} catch (Exception e) {salida.put("mensaje", "Error al registrar: " +e);}
		
		
		
		
		return ResponseEntity.ok(salida);
	}
	
	
	
	@PutMapping("/notaEdit")
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> EditNota(@RequestBody Notas obj) {
		
		//CREAMOS UN MAP, QUE ALMACENARA LOS MENSAJES DE EXITOS O ERRORES
		Map<String, Object> salida = new HashMap<>();
		//Intentamos la transaction
		
		
			System.out.println("\n"+obj);
			try {
				
				if(this.service.buscar(obj.getId_notas())!=null){
					
					service.Actualizar(obj);
					salida.put("obj", obj);
					salida.put("mensaje", "Actualizado nota correctamente");
					
				}else {
					salida.put("obj", obj);
					salida.put("mensaje", "Nota no encontrada en la base de datos");
				}
				
				
				
			} catch (Exception e) {salida.put("mensaje", "Error al Actualizar: " +e);}
		
		
		
		
		return ResponseEntity.ok(salida);
	}

}

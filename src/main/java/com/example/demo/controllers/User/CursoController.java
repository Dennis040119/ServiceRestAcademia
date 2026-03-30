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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Docente;
import com.example.demo.entity.Usuario;
import com.example.demo.services.impl.CursoServiceImpl;
import com.example.demo.services.impl.DocenteServiceImpl;

@RestController
@Controller
@RequestMapping("/curso")
@CrossOrigin(origins = "http://localhost:4200")
public class CursoController {
	
	@Autowired
	CursoServiceImpl service;
	
	@GetMapping("/cursoList")
	@ResponseBody
	public ResponseEntity<?> listaUsuario() {
		
		Map<String, Object> response = new HashMap<>();
		List<Curso> lista = null;
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
		return new ResponseEntity<List<Curso>>(lista, HttpStatus.OK);
	}
	
	
	@GetMapping("/buscarById/{id}")
	@ResponseBody
	public Optional<Curso> buscarCursoById(@PathVariable String id) {
		
		Map<String, Object> response = new HashMap<>();
		Optional<Curso> usu = null;
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

}

package com.example.demo.controllers.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Docente;
import com.example.demo.entity.Grado;
import com.example.demo.services.GradoServiceImpl;

@RestController
@Controller
@RequestMapping("/grado")
@CrossOrigin(origins = "http://localhost:4200")
public class GradoController {
	
	@Autowired
	GradoServiceImpl service;
	
	@GetMapping("/gradoList")
	@ResponseBody
	public ResponseEntity<?> listaUsuario() {
		
		Map<String, Object> response = new HashMap<>();
		List<Grado> lista = null;
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
		return new ResponseEntity<List<Grado>>(lista, HttpStatus.OK);
	}

}

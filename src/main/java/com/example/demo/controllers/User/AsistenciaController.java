package com.example.demo.controllers.User;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Asistencia;
import com.example.demo.entity.AsistenciaSesion;
import com.example.demo.entity.Curso;
import com.example.demo.services.AsistenciaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@Controller
@RequestMapping("/asistencia")
@CrossOrigin(origins = "http://localhost:4200")
public class AsistenciaController {
	

	private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
        
        
    }
    
    
    ///////////////////////////////////////

		/** GET /api/asistencias */
	    @GetMapping
	    public ResponseEntity<List<Asistencia>> listar() {
	        return ResponseEntity.ok(asistenciaService.listar());
	    }
	
	    /** GET /api/asistencias/{id} */
	    @GetMapping("/{id}")
	    public ResponseEntity<Asistencia> buscarPorId(@PathVariable Long id) {
	        return asistenciaService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }
	    
	    

	    /** POST /api/asistencias */
    @PostMapping
    public ResponseEntity<Asistencia> registrar(@Valid @RequestBody Asistencia asistencia) {
        Asistencia creada = asistenciaService.registrar(asistencia);
        return ResponseEntity.created(URI.create("/asistencia/" + creada.getId())).body(creada);
    }
    

    //** POST /api/asistencias/lote */
    @PostMapping("/asistenciaFecha")
    public ResponseEntity<List<Asistencia>> registrarLote(
            @Valid @RequestBody List<Asistencia> request) {
    	
    	

    	System.out.println("el primer elem ento es: " +request.get(0).getAlumno().toString());
    	
    	List<Asistencia> creado = new ArrayList<Asistencia>();
    	for (int i = 0; i < request.size(); i++) {
    		Asistencia reg = new Asistencia();
    		
    		reg = request.get(i);
    		
    		this.asistenciaService.registrar(reg);
    		creado.add(reg);
    		
			
		}

        // 201 y Location opcionalmente apuntando al recurso de colección por curso/fecha
        return ResponseEntity
                .created(URI.create(String.format("/asistencia",
                        request.get(0).getAlumno(), request.get(0).getSesion()   )))
                .body(creado);
    }

    
    

    /** PUT /api/asistencias/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> editar(@PathVariable Long id, @Valid @RequestBody Asistencia asistencia) {
        try {
            Asistencia actualizada = asistenciaService.editar(id, asistencia);
            return ResponseEntity.ok(actualizada);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /api/asistencias/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            asistenciaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------- Consultas --------------------



/**
     * GET /api/asistencias/por-fecha-curso?fecha=2026-02-26&cursoId=10
     */
    @GetMapping("/por-sesion")
    public ResponseEntity<List<Asistencia>> listarSesion(@RequestParam Long sesionId) {

        AsistenciaSesion as = new AsistenciaSesion(); // Instancia mínima con el ID
        as.setId(sesionId);
        
        List<Asistencia> lista = asistenciaService.listarPorSesion(as);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/asistencias/por-alumno?alumnoId=5
     */
    @GetMapping("/por-alumno")
    public ResponseEntity<List<Asistencia>> listarPorAlumno(@RequestParam String alumnoId) {
        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);
        return ResponseEntity.ok(asistenciaService.listarPorAlumno(alumno));
    }

    /**
     * GET /api/asistencias/por-fecha-alumno-curso?fecha=2026-02-26&alumnoId=5&cursoId=10
     */
    @GetMapping("/por-sesion-alumno")
    public ResponseEntity<Asistencia> listarPorFechaAlumnoYCurso(
            @RequestParam String alumnoId,
            @RequestParam Long sesionId) {

        Alumno alumno = new Alumno();
        alumno.setId(alumnoId);
        AsistenciaSesion as = new AsistenciaSesion(); // Instancia mínima con el ID
        as.setId(sesionId);

        Optional<Asistencia> opt = asistenciaService.listarPorAlumnoAndSesion(as, alumno);
        return opt.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }



}

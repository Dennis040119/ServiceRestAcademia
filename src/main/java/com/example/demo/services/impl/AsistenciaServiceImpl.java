package com.example.demo.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Asistencia;
import com.example.demo.entity.AsistenciaSesion;
import com.example.demo.entity.Curso;
import com.example.demo.repository.AsistenciaRepository;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.services.AsistenciaService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class AsistenciaServiceImpl implements AsistenciaService {
	
	@Autowired
	AsistenciaRepository repo;
	
	@Autowired
	UsuariosRepository userRepo;

	@Override
	public List<Asistencia> listar() {
		// TODO Auto-generated method stub
		return this.repo.findAll();
	}

	@Override
	public Optional<Asistencia> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return this.repo.findById(id);
	}

	@Override
	public Asistencia registrar(Asistencia asistencia) {
		
		
		// TODO Auto-generated method stub
		

		asistencia.setActualizadoPor(usuarioActual());
		asistencia.setActualizadoEn(LocalDateTime.now());

		return this.repo.save(asistencia);
	}

	@Override
	public Asistencia editar(Long id, Asistencia asistencia) {

		Asistencia existente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asistencia no encontrada con id: " + id));

        // Copiar valores del objeto recibido
        
        existente.setActualizadoPor(usuarioActual());
        existente.setActualizadoEn(LocalDateTime.now());
        existente.setEstado(asistencia.getEstado());
        existente.setObservacion(asistencia.getObservacion());
          
        
        return repo.save(existente);

	}

	@Override
	public void eliminar(Long id) {

			if (!repo.existsById(id)) {
            throw new EntityNotFoundException("No existe asistencia con id: " + id);
        }
        repo.deleteById(id);

		
	}

	

	@Override
	public List<Asistencia> listarPorAlumno(Alumno alumno) {
		// TODO Auto-generated method stub
		return repo.findByAlumno(alumno);
	}

	
	@Override
	public List<Asistencia> listarPorSesion(AsistenciaSesion as) {
		// TODO Auto-generated method stub
		return repo.findBySesion(as);
	}

	@Override
	public Optional<Asistencia> listarPorAlumnoAndSesion(AsistenciaSesion as, Alumno alumno) {
		// TODO Auto-generated method stub
		return repo.findBySesionIdAndAlumno_Id(as.getId(), alumno.getId());
	}
	

	private String usuarioActual() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    

    // Si no hay autenticación o es anónima, devolvemos "sistema"
     if (auth == null || !auth.isAuthenticated()) {
    	 System.out.println("No hay autenticacion o es anonima");
         return "sistema";
     }

     
     System.out.println("El getname de auth es: "+auth.getName());
	String username = auth.getName();
	    // En algunos casos el principal puede ser "anonymousUser"
	    if (username == null || "anonymousUser".equalsIgnoreCase(username)) {
	    	System.out.println("Username es null o es anonymousUser");
	        return "sistema";
	    }
	    
	    System.out.println("Paso los dos if");
	    // Haz UNA sola consulta y maneja el Optional correctamente
	    return userRepo.findOneByUsername(username)
	            .map(u -> u.getUserid()) // o el atributo que necesites
	            .orElse(username);   

	}

	


}

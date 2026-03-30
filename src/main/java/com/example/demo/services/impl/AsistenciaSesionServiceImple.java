package com.example.demo.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Asistencia;
import com.example.demo.entity.AsistenciaSesion;
import com.example.demo.entity.Curso;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.repository.AsistenciaRepository;
import com.example.demo.repository.AsistenciaSesionRepository;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.UsuariosRepository;
import com.example.demo.services.AsistenciaService;
import com.example.demo.services.AsistenciaSesionService;
import com.example.demo.utils.Enums.EstadoSesion;




@Service
public class AsistenciaSesionServiceImple implements AsistenciaSesionService {
	
	@Autowired
	AsistenciaSesionRepository repo;
	
	@Autowired
	CursoRepository cursorepo;
	
	@Autowired
	AlumnoRepository alumnorepo;
	
	@Autowired
	UsuariosRepository userRepo;

	@Override
	public List<AsistenciaSesion> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}


	@Override
    @Transactional(readOnly = true)
    public List<AsistenciaSesion> listarPorCursoYFecha(String idCurso, LocalDate fecha) {
        // Crea este método en tu repo si quieres exacto (idCurso+fecha)
        // return sesionRepo.findByIdCursoAndFecha(idCurso, fecha);
        // Alternativa rápida:
        return repo.findAll().stream()
                .filter(s -> s.getCurso().getId().equals(idCurso) && s.getFecha().equals(fecha))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaSesion> listarPorCurso(String idCurso) {
        // Crea en repo: List<AsistenciaSesion> findByIdCurso(Long idCurso);
        return repo.findAll().stream()
                .filter(s -> s.getCurso().getId().equals(idCurso))
                .toList();
    }



		@Override
	    @Transactional( readOnly = true)
	    public Optional<AsistenciaSesion> findById(Long idSesion) {
	        return repo.findById(idSesion);
	    }



	@Override
    @Transactional(readOnly = true)
    public Optional<AsistenciaSesion> findByIdCursoAndFecha(String idCurso, LocalDate fecha) {
        return repo.findByCurso_IdAndFecha(idCurso, fecha);
    }



	@Override
    public AsistenciaSesion cambiarEstado(Long idSesion, EstadoSesion nuevoEstado) {
        AsistenciaSesion sesion = repo.findById(idSesion)
                .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));

        sesion.setEstado(nuevoEstado);
        return repo.save(sesion);
    }
	

	@Override
    public AsistenciaSesion cerrarSesion(Long idSesion) {
        return cambiarEstado(idSesion, EstadoSesion.CERRADA);
    }

    @Override
    public AsistenciaSesion bloquearSesion(Long idSesion) {
        return cambiarEstado(idSesion, EstadoSesion.BLOQUEADA);
    }



	@Override
	public AsistenciaSesion abrirSesion(String idCurso, LocalDate fecha) {
		
		

		Curso curso = cursorepo.findById(idCurso)
        .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));


		
		
		repo.findByCurso_IdAndFecha(idCurso, fecha)
                .ifPresent(s -> { throw new IllegalStateException("La sesión ya existe para ese curso/fecha/periodo"); });

        AsistenciaSesion s = new AsistenciaSesion();
        s.setCurso(curso);
        s.setFecha(fecha);
        
        s.setEstado(EstadoSesion.ABIERTA);
        s.setActualizadaPor(usuarioActual());
        return repo.save(s);

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

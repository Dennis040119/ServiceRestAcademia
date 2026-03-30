package com.example.demo.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Asistencia;
import com.example.demo.entity.AsistenciaSesion;
import com.example.demo.entity.Curso;
import com.example.demo.utils.Enums.EstadoAsistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
	



	 @Override
	    Optional<Asistencia> findById(Long id);

	    @Override
	    List<Asistencia> findAll();

	    // --- Por sesión ---
	    List<Asistencia> findBySesionId(Long idSesion);
	    List<Asistencia> findBySesion(AsistenciaSesion sesion);

	    // --- Por alumno (ahora usando Alumno.id) ---
	    List<Asistencia> findByAlumno_Id(String alumnoId);
	    List<Asistencia> findByAlumno(Alumno alumno);

	    // --- Por sesión y alumno (clave de negocio) ---
	    Optional<Asistencia> findBySesionIdAndAlumno_Id(Long idSesion, String alumnoId);
	    boolean existsBySesionIdAndAlumno_Id(Long idSesion, String alumnoId);
	    long countBySesionId(Long idSesion);

	    // --- Filtros por estado ---
	    List<Asistencia> findBySesionIdAndEstado(Long idSesion, EstadoAsistencia estado);
	    List<Asistencia> findByAlumno_IdAndEstado(String alumnoId, EstadoAsistencia estado);

	    // --- Rango de fechas a través de la fecha de la sesión ---
	    // (requiere que AsistenciaSesion tenga 'fecha' mapeada como LocalDate)
	    List<Asistencia> findByAlumno_IdAndSesion_FechaBetween(String alumnoId, LocalDate desde, LocalDate hasta);
	    List<Asistencia> findBySesion_FechaBetween(LocalDate desde, LocalDate hasta);

	    // --- Operaciones de borrado específicas ---
	    void deleteBySesionIdAndAlumno_Id(Long idSesion, String alumnoId);
	    long deleteBySesionId(Long idSesion);

	    // --- Cargas con relaciones (evita N+1) ---
	    // Carga alumno y sesion en la misma consulta cuando necesitas ambos
	    @EntityGraph(attributePaths = {"alumno", "sesion"})
	    List<Asistencia> findWithAlumnoAndSesionBySesionId(Long idSesion);

	    // --- Útiles adicionales ---
	    // Último registro por fecha de actualización (auditoría)
	    Optional<Asistencia> findFirstBySesionIdOrderByActualizadoEnDesc(Long idSesion);

	    // Listar por varias sesiones
	    List<Asistencia> findBySesionIdIn(List<Long> idsSesion);

	    // Listar por varios alumnos (usando Alumno.id)
	    List<Asistencia> findByAlumno_IdIn(List<String> idsAlumno);

 


}

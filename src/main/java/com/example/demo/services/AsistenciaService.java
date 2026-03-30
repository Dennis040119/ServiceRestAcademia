package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Asistencia;
import com.example.demo.entity.AsistenciaSesion;
import com.example.demo.entity.Curso;

public interface AsistenciaService {
	

	 /** Lista todas las asistencias. */
	    public List<Asistencia> listar();

	    /** Busca una asistencia por su ID. */
	    Optional<Asistencia> buscarPorId(Long id);

	    /** Registra (crea) una nueva asistencia. */
	    Asistencia registrar(Asistencia asistencia);

	    /** Edita (actualiza) una asistencia existente. */
	    Asistencia editar(Long id, Asistencia asistencia);

	    /** Elimina una asistencia por su ID. */
	    void eliminar(Long id);

	    // -------- Consultas específicas --------
	    /** Lista asistencias por fecha y curso. */
	    List<Asistencia> listarPorSesion(AsistenciaSesion as);

	    /** Lista todas las asistencias de un alumno. */
	    List<Asistencia> listarPorAlumno(Alumno alumno);

	    /** Obtiene la asistencia por fecha, alumno y curso (si existe). */
	    Optional<Asistencia> listarPorAlumnoAndSesion(AsistenciaSesion as, Alumno alumno);


}

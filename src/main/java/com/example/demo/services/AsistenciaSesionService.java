package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.AsistenciaSesion;
import com.example.demo.utils.Enums.EstadoSesion;

public interface AsistenciaSesionService {
	
	List<AsistenciaSesion> listar();
	
	List<AsistenciaSesion> listarPorCurso(String idCurso);
	
	List<AsistenciaSesion> listarPorCursoYFecha(String idCurso, LocalDate fecha);
	
	Optional<AsistenciaSesion> findById(Long idSesion);

    /**
     * Busca la sesión por clave de negocio única (idCurso, fecha, periodo).
     */
    Optional<AsistenciaSesion> findByIdCursoAndFecha(String idCurso, LocalDate fecha);
    
    AsistenciaSesion cambiarEstado(Long idSesion, EstadoSesion nuevoEstado);

    AsistenciaSesion abrirSesion(String idCurso, LocalDate fecha);
    

    AsistenciaSesion cerrarSesion(Long idSesion);

    
    AsistenciaSesion bloquearSesion(Long idSesion);

}

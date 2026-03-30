	package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AsistenciaSesion;

public interface AsistenciaSesionRepository extends JpaRepository<AsistenciaSesion, Long> {
	
	
	Optional<AsistenciaSesion> findByCurso_IdAndFecha(String idCurso, LocalDate fecha);
}

package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Archivo;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
	

	 	Optional<Archivo> findByNombre(String nombre);

	    List<Archivo> findByCarpeta(String carpeta);
	    
	    List<Archivo> findByCursoid(String curso);

	    boolean existsByNombre(String nombre);


}

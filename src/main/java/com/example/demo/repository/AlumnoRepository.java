package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Usuario;

public interface AlumnoRepository extends JpaRepository<Alumno, String> {
	
	Optional<Alumno> findOneByNombre(String nombre);//DERIVED QUERY METHODS
	

	



	List<Alumno> findByGrado_IdGrado(String idGrado);

    // si quieres también por nombre de grado:
    List<Alumno> findByGrado_Nombre(String nombreGrado);



}

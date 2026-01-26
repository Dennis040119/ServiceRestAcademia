package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Usuario;

public interface AlumnoRepository extends JpaRepository<Alumno, String> {
	
	Optional<Alumno> findOneByNombre(String nombre);//DERIVED QUERY METHODS

}

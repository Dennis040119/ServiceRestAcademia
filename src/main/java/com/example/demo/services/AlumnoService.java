package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Alumno;

public interface AlumnoService {
	
	public List<Alumno> listar();
	public Optional<Alumno> buscar(String id);
	public Optional<Alumno> BuscarPorNombre(String id);
	public void save(Alumno Alumno);
	public void Actualizar(Alumno Alumno);
	public void delete(String id);

}

package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Docente;

public interface CursoService {
	
	public List<Curso> listar();
	public Optional<Curso> buscar(String id);
	public Optional<Curso> BuscarPorNombre(String id);
	public void save(Curso curso);
	public void Actualizar(Curso curso);
	public void delete(String id);

}

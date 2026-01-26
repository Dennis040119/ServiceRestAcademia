package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Docente;

public interface DocenteService {
	public List<Docente> listar();
	public Optional<Docente> buscar(String id);
	public Optional<Docente> BuscarPorNombre(String id);
	public void save(Docente Docente);
	public void Actualizar(Docente Docente);
	public void delete(String id);

}

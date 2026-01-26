package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Grado;

public interface GradoService {
	
	public List<Grado> listar();
	public Optional<Grado> buscar(String id);
	public Optional<Grado> BuscarPorNombre(String id);
	public void save(Grado grado);
	public void Actualizar(Grado grado);
	public void delete(String id);

}

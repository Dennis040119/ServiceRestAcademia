package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Notas;

public interface NotasService {
	public List<Notas> listar();
	public Optional<Notas> buscar(String id);
	public Optional<Notas> BuscarPorNombre(String id);
	public void save(Notas nota);
	public void Actualizar(Notas nota);
	public void delete(String id);

}

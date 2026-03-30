package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Curso;
import com.example.demo.repository.CursoRepository;
import com.example.demo.services.CursoService;

@Service
public class CursoServiceImpl implements CursoService {
	
	@Autowired
	CursoRepository repo;

	@Override
	public List<Curso> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Curso> buscar(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Optional<Curso> BuscarPorNombre(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Curso curso) {
		// TODO Auto-generated method stub
		repo.save(curso);
	}

	@Override
	public void Actualizar(Curso curso) {
		// TODO Auto-generated method stub
		repo.save(curso);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}

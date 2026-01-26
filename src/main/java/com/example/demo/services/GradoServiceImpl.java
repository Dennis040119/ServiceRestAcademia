package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Grado;
import com.example.demo.repository.GradoRepository;

@Service
public class GradoServiceImpl implements GradoService {
	
	@Autowired
	GradoRepository repo;

	@Override
	public List<Grado> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Grado> buscar(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Optional<Grado> BuscarPorNombre(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Grado grado) {
		// TODO Auto-generated method stub
		repo.save(grado);	}

	@Override
	public void Actualizar(Grado grado) {
		// TODO Auto-generated method stub
		repo.save(grado);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}

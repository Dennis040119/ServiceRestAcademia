package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Notas;
import com.example.demo.repository.NotasRepository;

@Service
public class NotaServiceImpl implements NotasService {
	
	@Autowired
	NotasRepository repo;

	@Override
	public List<Notas> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Notas> buscar(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Optional<Notas> BuscarPorNombre(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Notas nota) {
		// TODO Auto-generated method stub
		repo.save(nota);
	}

	@Override
	public void Actualizar(Notas nota) {
		// TODO Auto-generated method stub
		repo.save(nota);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}

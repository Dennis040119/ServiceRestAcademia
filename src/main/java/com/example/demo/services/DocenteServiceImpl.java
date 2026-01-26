package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Docente;
import com.example.demo.repository.Docenterepository;


@Service
public class DocenteServiceImpl implements DocenteService {
	
	@Autowired
	Docenterepository repo;

	@Override
	public List<Docente> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Docente> buscar(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Optional<Docente> BuscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return repo.BuscarXNombre(nombre);
	}

	@Override
	public void save(Docente docente) {
		// TODO Auto-generated method stub
		repo.save(docente);
	}

	@Override
	public void Actualizar(Docente docente) {
		// TODO Auto-generated method stub
		repo.save(docente);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}

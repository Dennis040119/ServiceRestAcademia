package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Alumno;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.services.AlumnoService;


@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	@Autowired
	private AlumnoRepository repo;

	@Override
	public List<Alumno> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}
	
	@Override
	public List<Alumno> listarPorGrado(String gradoId) {
		// TODO Auto-generated method stub
		return repo.findByGrado_IdGrado(gradoId);
	}

	@Override
	public Optional<Alumno> buscar(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Optional<Alumno> BuscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return repo.findOneByNombre(nombre);
	}

	@Override
	public void save(Alumno Alumno) {
		// TODO Auto-generated method stub
		repo.save(Alumno);
	}

	@Override
	public void Actualizar(Alumno Alumno) {
		// TODO Auto-generated method stub
		repo.save(Alumno);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}

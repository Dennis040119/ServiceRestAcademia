package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuariosRepository;


@Service
public class UsuarioServiceImp implements UsuarioService {
	
	@Autowired
	private UsuariosRepository repo;

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public List<Usuario> listarActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> buscar(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> BuscarPorUser(String id) {
		// TODO Auto-generated method stub
		return repo.findbyUser(id);
	}

	@Override
	public void save(Usuario usuario) {
		// TODO Auto-generated method stub
		repo.save(usuario);
	}

	@Override
	public void Actualizar(Usuario Usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}

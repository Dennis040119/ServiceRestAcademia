package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Docente;
import com.example.demo.entity.Usuario;

public interface Docenterepository extends JpaRepository<Docente, String> {
	
	
	@Query("from Docente t where t.nombres = :nombre")
	 Optional<Docente> BuscarXNombre(@Param("nombre") String username);
	
	

		@Query("SELECT d FROM Docente d JOIN FETCH d.usuario")
		List<Docente> findAllConUsuario();
		

		 // Para ver si un usuario ya tiene docente
		    boolean existsByUsuarioUserid(String userid);

		    // Buscar por usuario
		    Optional<Docente> findByUsuarioUserid(String userid);



}

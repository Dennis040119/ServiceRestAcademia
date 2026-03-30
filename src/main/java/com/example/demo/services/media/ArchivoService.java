package com.example.demo.services.media;



import com.example.demo.entity.Archivo;

import java.util.List;
import java.util.Optional;

public interface ArchivoService {

    Archivo guardar(Archivo archivo);

   
    Optional<Archivo> buscarPorId(Long id);

    Optional<Archivo> buscarPorNombre(String nombre);

    List<Archivo> listarTodos();

    List<Archivo> listarPorCarpeta(String carpeta);
    
    List<Archivo> listarPorCurso(String carpeta);

    boolean existePorNombre(String nombre);

    Archivo actualizar(Long id, Archivo cambios);

    void eliminarPorId(Long id);

    long contar();

	Archivo crear(String nombre, String nombrepila, String tipo, String carpeta, Long tamanio, String cursoid);
}

package com.example.demo.services.media;



import com.example.demo.entity.Archivo;
import com.example.demo.repository.ArchivoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;

    public ArchivoServiceImpl(ArchivoRepository archivoRepository) {
        this.archivoRepository = archivoRepository;
    }

    @Override
    public Archivo guardar(Archivo archivo) {
        return archivoRepository.save(archivo);
    }


	@Override
	public Archivo crear(String nombre,String nombrepila,String tipo,String carpeta,Long tamanio,String cursoid) {
	    Archivo archivo = new Archivo();
	    archivo.setNombre(nombre);
	    archivo.setNombrepila(nombrepila); // nuevo campo
	    archivo.setTipo(tipo);
	    archivo.setCarpeta(carpeta);
	    archivo.setTamanio(tamanio);
	    archivo.setCursoid(cursoid);       // nuevo campo
	
	    // fechaCreacion: si tienes @PrePersist en la entidad, no hace falta setearlo aquí.
	    // Si NO lo tienes, puedes descomentar la siguiente línea:
	    // archivo.setFechaCreacion(LocalDateTime.now());
	
	    return archivoRepository.save(archivo);
	}

    @Override
    @Transactional(readOnly = true)
    public Optional<Archivo> buscarPorId(Long id) {
        return archivoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Archivo> buscarPorNombre(String nombre) {
        return archivoRepository.findByNombre(nombre);
    }

  
    @Override
	public List<Archivo> listarPorCurso(String carpeta) {
		// TODO Auto-generated method stub
    	return archivoRepository.findByCursoid(carpeta);
	}

    @Override
    @Transactional(readOnly = true)
    public List<Archivo> listarTodos() {
        return archivoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Archivo> listarPorCarpeta(String carpeta) {
        return archivoRepository.findByCarpeta(carpeta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorNombre(String nombre) {
        return archivoRepository.existsByNombre(nombre);
    }

    @Override
    public Archivo actualizar(Long id, Archivo cambios) {
        Archivo actual = archivoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado con id " + id));

        if (cambios.getNombre() != null)  actual.setNombre(cambios.getNombre());
        if (cambios.getTipo() != null)    actual.setTipo(cambios.getTipo());
        if (cambios.getCarpeta() != null) actual.setCarpeta(cambios.getCarpeta());
        if (cambios.getTamanio() != null) actual.setTamanio(cambios.getTamanio());

        return archivoRepository.save(actual);
    }

    @Override
    public void eliminarPorId(Long id) {
        if (!archivoRepository.existsById(id)) {
            throw new EntityNotFoundException("Archivo no encontrado con id " + id);
        }
        archivoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long contar() {
        return archivoRepository.count();
    }

	
}

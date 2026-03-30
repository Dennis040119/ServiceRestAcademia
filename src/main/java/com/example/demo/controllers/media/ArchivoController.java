package com.example.demo.controllers.media;



import com.example.demo.entity.Archivo;
import com.example.demo.services.media.ArchivoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/archivos")
public class ArchivoController {

    private final ArchivoService archivoService;

    public ArchivoController(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    // ---------- Crear desde JSON (solo metadata) ----------
    @PostMapping("/ArchivoSave")
    public ResponseEntity<Archivo> crear(@RequestBody Archivo body) {
        validarCrear(body);
        Archivo creado = archivoService.crear(
                body.getNombre(),
                body.getNombrepila(),
                body.getTipo(),
                body.getCarpeta(),
                body.getTamanio(),
                body.getCursoid()
                
               
        );
        return ResponseEntity
                .created(URI.create("/archivos/" + creado.getId()))
                .body(creado);
        
    }

    /*
    // ---------- Subir archivo (recibe MultipartFile) y guardar metadata ----------
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Archivo> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam(name = "carpeta") String carpeta
    ) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (!StringUtils.hasText(carpeta)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("X-Error", "El parámetro 'carpeta' es obligatorio")
                    .build();
        }

        String nombre = limpiarNombre(file.getOriginalFilename());
        String tipo = detectarTipo(file);
        Long tamanio = file.getSize();

        // (Opcional) Guardar físicamente el archivo en disco o S3 aquí:
        // Path destino = Paths.get(basePath, carpeta, nombre);
        // Files.createDirectories(destino.getParent());
        // file.transferTo(destino.toFile());

        Archivo creado = archivoService.crear(nombre, tipo, carpeta, tamanio);
        return ResponseEntity
                .created(URI.create("/api/archivos/" + creado.getId()))
                .body(creado);
    }
	*/
    // ---------- Obtener por ID ----------
    @GetMapping("/byId/{id}")
    public ResponseEntity<Archivo> obtenerPorId(@PathVariable @Min(1) Long id) {
        return archivoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------- Buscar por nombre ----------
    @GetMapping("/bynombre/{nombre}")
    public ResponseEntity<Archivo> obtenerPorNombre(@PathVariable String nombre) {
        if (!StringUtils.hasText(nombre)) {
            return ResponseEntity.badRequest().build();
        }
        return archivoService.buscarPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------- Listar todo ----------
    @GetMapping
    public ResponseEntity<List<Archivo>> listarTodos() {
        return ResponseEntity.ok(archivoService.listarTodos());
    }

    // ---------- Listar por carpeta ----------
    @GetMapping("/Listcarpeta/{carpeta}")
    public ResponseEntity<List<Archivo>> listarPorCarpeta(@PathVariable String carpeta) {
        if (!StringUtils.hasText(carpeta)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(archivoService.listarPorCarpeta(carpeta));
    }
    
 // ---------- Listar por carpeta ----------
    @GetMapping("/ListCurso/{curso}")
    public ResponseEntity<List<Archivo>> listarPorCurso(@PathVariable String curso) {
        if (!StringUtils.hasText(curso)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(archivoService.listarPorCurso(curso));
    }

    // ---------- Actualizar (parcial) ----------
    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Archivo> actualizarParcial(@PathVariable Long id, @RequestBody Archivo cambios) {
        try {
            // Solo se actualizan campos no nulos: nombre, tipo, carpeta, tamanio
            Archivo actualizado = archivoService.actualizar(id, cambios);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ---------- Reemplazo total (PUT) ----------
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Archivo> reemplazar(@PathVariable Long id, @RequestBody Archivo body) {
        try {
            validarReemplazo(body);
            // En un PUT, normalmente se forzan todos los campos relevantes
            Archivo cambios = new Archivo();
            cambios.setNombre(body.getNombre());
            cambios.setTipo(body.getTipo());
            cambios.setCarpeta(body.getCarpeta());
            cambios.setTamanio(body.getTamanio());

            Archivo actualizado = archivoService.actualizar(id, cambios);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ---------- Eliminar ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            archivoService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ---------- Contar ----------
    @GetMapping("/count")
    public ResponseEntity<Long> contar() {
        return ResponseEntity.ok(archivoService.contar());
    }

    // ---------- Utilidades privadas ----------

    private void validarCrear(Archivo body) {
        if (!StringUtils.hasText(body.getNombre())) {
            throw new IllegalArgumentException("El campo 'nombre' es obligatorio");
        }
        if (!StringUtils.hasText(body.getTipo())) {
            throw new IllegalArgumentException("El campo 'tipo' es obligatorio");
        }
        if (!StringUtils.hasText(body.getCarpeta())) {
            throw new IllegalArgumentException("El campo 'carpeta' es obligatorio");
        }
    }

    private void validarReemplazo(Archivo body) {
        // Validación básica para PUT
        validarCrear(body);
    }

    private String limpiarNombre(String originalFilename) {
        if (!StringUtils.hasText(originalFilename)) {
            return "archivo_sin_nombre";
        }
        // Normaliza y remueve path traversal, dejando solo el nombre
        String limpio = originalFilename.replace("\\", "/");
        limpio = limpio.substring(limpio.lastIndexOf('/') + 1);
        return StringUtils.trimWhitespace(limpio);
    }

    private String detectarTipo(MultipartFile file) {
        // Toma el content-type si viene; de lo contrario intenta por extensión
        if (StringUtils.hasText(file.getContentType())) {
            return file.getContentType();
        }
        String nombre = file.getOriginalFilename();
        if (nombre != null && nombre.contains(".")) {
            String ext = nombre.substring(nombre.lastIndexOf('.') + 1).toLowerCase();
            return switch (ext) {
                case "jpg", "jpeg" -> "image/jpeg";
                case "png" -> "image/png";
                case "gif" -> "image/gif";
                case "pdf" -> "application/pdf";
                case "doc", "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                default -> "application/octet-stream";
            };
        }
        return "application/octet-stream";
    }

    // (Opcional) Manejador de errores simple para IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }
}

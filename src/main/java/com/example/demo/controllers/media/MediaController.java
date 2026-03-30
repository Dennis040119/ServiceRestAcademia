package com.example.demo.controllers.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.services.media.StorageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/media")
@CrossOrigin(origins= {"http://localhost:4200"})
public class MediaController {
	
	private final StorageService storageService;
	private final HttpServletRequest request;
	
	//ESTE MÉTODO SIRVE PARA SUBIR ARCHIVOS DE CUALQUIER TIPO Y RETORNA EN UN MAP
		//LA URL FINAL DEL ARCHIVO SUBIDO PARA SU POSTERIOR CONSULTA
		//EN @REQUESTPARAM CAPTURAMOS EL ARCHIVO CON EL NAME "file" IMPORTANTE!!!!!!!
		
		@PostMapping("/upload/{id}/{tipofile}")
		public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable String id,
				@PathVariable String tipofile){
			
			//ALMACENAMOS EL ARCHIVO UTILIZANDO EL SERVICIO DE ALMACENAMIENTO...
			String path = storageService.store(multipartFile,id,tipofile);//nombre del archivo
			
			//NOS DEVUELVE LA URL DEL ARCHIVO
			String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
			
			String url = ServletUriComponentsBuilder
			.fromHttpUrl(host) //Añadimos el host
			.path("/media/file/") //Añadimos la carpeta en la que se encuentra
			.path(path) //Añadimos el nombre del archivo
			.toUriString();
			
			return Map.of("url",path);
			
			
		}
		
		
		@CrossOrigin(origins= {"http://localhost:4200"})
		@GetMapping("/file/{filename:.+}/{dirFile}") 
		public ResponseEntity<Resource> getFile(@PathVariable String filename,@PathVariable String dirFile) throws IOException{
			
			Resource file;
			
			try{
			 file = storageService.loadAsResource(filename,dirFile);
			
			if(!file.exists()) {
				Path defaultImagePath = Paths.get("mediafiles/utils/noImage.jpg");
			 file = new FileSystemResource(defaultImagePath);
			}
			
			String contentType = Files.probeContentType(file.getFile().toPath());
			

				String filenameOut = file.getFilename();
				
				return ResponseEntity.ok()
				    .header(HttpHeaders.CONTENT_TYPE, contentType)
				   				    .body(file);

			
			}catch(IOException e) {
				
				
		          // Si el archivo no existe, carga la imagen por defecto y retorna
		         Path defaultImagePath = Paths.get("mediafiles/utils/noImage.jpg");
		         file = new FileSystemResource(defaultImagePath);
		        
				String contentType = Files.probeContentType(file.getFile().toPath());

					String filenameOut = file.getFilename();
					
					return ResponseEntity.ok()
					    .header(HttpHeaders.CONTENT_TYPE, contentType)
					    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filenameOut + "\"")
					    .body(file);

			}
		}
		
		@CrossOrigin(origins= {"http://localhost:4200"})
		@DeleteMapping("/delfile/{filename:.+}/{dirFile}") 
		public  ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String filename, @PathVariable String dirFile) {
			
			Map<String, Object> salida = new HashMap<>();
	        try {
	            if (storageService.deleteFile(filename, dirFile)) {
	            	
	            	salida.put("mensaje", "Imagen eliminada correctamente");
	               
	            } else {
	            	
	            	salida.put("mensaje", "Imagen no encontrada");
	                
	            }
	        } catch (IOException e) {
	        	salida.put("mensaje", "Error al eliminar: "+e);
	            
	        }
	        
	        return ResponseEntity.ok(salida);
	    }
		
		
		@PostMapping("/uploadDoc/{id}/{tipofile}")
		public Map<String, String> uploadFileDoc(@RequestParam("file") MultipartFile multipartFile, @PathVariable String id,
				@PathVariable String tipofile){
			
			//ALMACENAMOS EL ARCHIVO UTILIZANDO EL SERVICIO DE ALMACENAMIENTO...
			String path = storageService.storeDoc(multipartFile,id,tipofile);//nombre del archivo
			
			//NOS DEVUELVE LA URL DEL ARCHIVO
			String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
			
			String url = ServletUriComponentsBuilder
			.fromHttpUrl(host) //Añadimos el host
			.path("/media/file/") //Añadimos la carpeta en la que se encuentra
			.path(path) //Añadimos el nombre del archivo
			.toUriString();
			
			return Map.of("url",path);
		}
			
}

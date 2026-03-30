package com.example.demo.services.media;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	//Método auxiliar para preparar todo lo necesario
			//para la subida de archivos
			void init() throws IOException;
			
			//Con este método almacenaremos FÍSICAMENTE 
			//el archivo en la carpeta de destino
			String store(MultipartFile file, String id,String tipofile);
			
			
			Resource loadAsResource(String filename,String dirFile);
			
			boolean deleteFile(String filename, String dirFile) throws IOException;

			String storeDoc(MultipartFile file, String id, String tipofile);
}

package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageServiceImpl implements StorageService {
	
private Path rootLocation;
	
	@Value("${media.location}")
	private String mediaLocation;

	@Override
	@jakarta.annotation.PostConstruct
	public void init() {
		//Inicializamos la ruta raiz de almacenamiento
				
		
	}

	@Override
	public String store(MultipartFile file, String id,String tipofile) {
		try {
			
			//Verificamos si el archivo no está vacío

			  if (file == null || file.isEmpty()) {
			            throw new IllegalArgumentException("No se puede almacenar un archivo vacío");
			    }

			
			
			//1-Vamos a conseguir la parte del tipo del archivo. Ejemplo: ".jpg"
			
			//Creamos las diferentes carpetas
			rootLocation = Paths.get(mediaLocation).normalize().toAbsolutePath();
			Files.createDirectories(rootLocation);
			
			Path pathDir = rootLocation.resolve(tipofile);

			  if (!pathDir.startsWith(rootLocation)) {
			            throw new SecurityException("Ruta de destino inválida");
			        }

	        Files.createDirectories(pathDir);
			
	        ///////
			String fileContentType = file.getContentType();//"(image/jpeg)" "(image/jpg)"
			String tipo = "." + fileContentType.substring(fileContentType.lastIndexOf("/")+1); //".jpeg"
			
			tipo=".jpg";
			
			
			//2-Conseguimos el nombre del archivo
			String filename = String.valueOf(Calendar.getInstance().getTimeInMillis());
			filename = filename.concat(tipo);//95202025526458484884.jpg
			String nombre = id.concat(filename);
			//3-Añadimos el string nombre del archivo a la ruta prefijada de destino 
			//de almacenamiento
			Path destinationFile = pathDir.resolve(Paths.get(nombre));
			
			//4-Movemos el archivo FÍSICAMENTE a su destino final
			
			//Esto es un try con recursos. Para utilizar un try con recursos
			//es necesario que las clases utilizadas dentro del paréntesis
			//implementen la interfaz "Autocloseable"
			try(InputStream inputStream = file.getInputStream()){
				
				//Si existe un archivo con el mismo nombre se reemplazará 
				//Lo vamos a almacenar utilizando la clase Files a partir de un inputStream
				//que maneja el archivo físico REAL
				Files.copy(inputStream,destinationFile,StandardCopyOption.REPLACE_EXISTING);
			}
			
			//5-Creamos la notación en la BBDD
			//////////////////////////////////////////////////////////////////
			//PREFIERO SUBIR PRIMERO LA IMAGEN Y POSTERIORMENTE CREAR LA
			//IMAGEN EN LA BBDD PORQUE SI LA IMAGEN NO SE CREA (ALGÚN ERROR)
			//SÓLO TENDRÉ COMO PARTE NEGATIVA UNA IMAGEN "FISICA" NO VINCULADA
			//DE LA OTRA FORMA TENDRÍA UN REGISTRO ERRÓNEO EN LA BBDD.
			//////////////////////////////////////////////////////////////////
			
			
			
			///////////////////////////////////////////////////////////////////
			
			return nombre;
			
			///////////////////////////////////////////////////////////////////
			
	}catch(IOException e) {
		
		throw new RuntimeException("Fallo al almacenar el archivo");
	}
		
	}

	@Override
	public Resource loadAsResource(String filename,String dirFile) {
		//Conseguimos el path REAL del archivo
				
				
				
				try {
					
					//Creamos las diferentes carpetas
					rootLocation = Paths.get(mediaLocation);
					Files.createDirectories(rootLocation);
					
					Path pathDir = rootLocation.resolve(dirFile);
			        Files.createDirectories(pathDir);
			        
			        Path file = pathDir.resolve(Paths.get(filename));
					
			        ///////
				
					Resource resource = new UrlResource(file.toUri());
					
					Resource resourceExtra=new UrlResource(file.toUri());
					
					if(resource.exists() || resource.isReadable()) {
						return resource;
					}else {
						return resourceExtra;
						//throw new RuntimeException("No se puede leer el archivo 1 " + filename);
					}
					
				} catch (MalformedURLException e) {
					throw new RuntimeException("No se puede leer el archivo 2 " + filename);
				}
				catch (IOException e) {
					throw new RuntimeException("Directorio no encontrado: " + dirFile);
				}
	}

		@Override
		public boolean deleteFile(String filename, String dirFile)throws IOException {
			  Path filePath = Paths.get(mediaLocation, dirFile, filename);
		       return Files.deleteIfExists(filePath);
		}
	

		@Override
		public String storeDoc(MultipartFile file, String id, String tipofile) {
		    try {
		        if (file == null || file.isEmpty()) {
		            throw new IllegalArgumentException("No se puede almacenar un archivo vacío");
		        }
		
		        // Límite de seguridad en memoria (configurable)
		        	long maxBytes = 20L * 1024 * 1024; // 20 MB
		        if (file.getSize() > maxBytes) {
		            throw new IllegalArgumentException("El archivo excede el tamaño permitido");
		        }
		
		        // 1) Rutas seguras
		        Path rootLocation = Paths.get(mediaLocation).normalize().toAbsolutePath();
		        Files.createDirectories(rootLocation);
		        Path pathDir = rootLocation.resolve(tipofile).normalize();
		        if (!pathDir.startsWith(rootLocation)) {
		            throw new SecurityException("Ruta de destino inválida");
		        }
		        Files.createDirectories(pathDir);
		
		        // 2) Lee todos los bytes (evita mark/reset)
		        byte[] data = file.getBytes();

		        // 3) Detecta extensión (primero por nombre, luego por cabecera/MIME)
		        String originalName = file.getOriginalFilename();
		        String ext = "";
		        if (originalName != null) {
		            String safe = org.springframework.util.StringUtils.cleanPath(originalName);
		            if (safe.contains("..")) throw new SecurityException("Nombre de archivo inválido");
		            int dot = safe.lastIndexOf('.');
		            if (dot > 0 && dot < safe.length() - 1) {
		                ext = safe.substring(dot).toLowerCase();
		            }
		        }

			        // Firma mágica simple
			        boolean looksPdf  = startsWithAscii(data, "%PDF-");
			        boolean looksOle  = startsWith(data, new byte[]{(byte)0xD0,(byte)0xCF,0x11,(byte)0xE0,(byte)0xA1,(byte)0xB1,0x1A,(byte)0xE1}); // DOC
			        boolean looksZip  = data.length >= 2 && data[0] == 'P' && data[1] == 'K'; // DOCX (ZIP)
			
			        // Si la extensión no es fiable, dedúcela
			        if (ext.isEmpty()) {
			            if (looksPdf)       ext = ".pdf";
			            else if (looksOle)  ext = ".doc";
			            else if (looksZip)  ext = ".docx";
			            else {
			                // última opción: MIME → extensión
			                String ct = file.getContentType();
			                ext = switch (ct == null ? "" : ct) {
			                    case "application/pdf" -> ".pdf";
			                    case "application/msword" -> ".doc";
			                    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> ".docx";
			                    default -> "";
			                };
			            }
			        }
			
			        // Lista blanca
			        java.util.Set<String> allowed = java.util.Set.of(".pdf", ".doc", ".docx");
			        if (ext.isEmpty() || !allowed.contains(ext)) {
			            throw new IllegalArgumentException("Tipo no permitido: " + ext + " (" + file.getContentType() + ")");
			        }
			
			        // Validación de firma según la extensión
			        if (".pdf".equals(ext) && !looksPdf) {
			            throw new IllegalArgumentException("El archivo no parece un PDF válido");
			        }
			        if (".doc".equals(ext) && !looksOle) {
			            throw new IllegalArgumentException("El archivo no parece un DOC (OLE) válido");
			        }
			        // Para DOCX solo comprobamos ZIP; si quieres ser estricto, valida estructura [Content_Types].xml
			
			        // 4) Nombre final y guardado
			        String filename = id + System.currentTimeMillis() + ext;
			        Path destination = pathDir.resolve(filename).normalize();
			        if (!destination.startsWith(pathDir)) {
			            throw new SecurityException("Ruta de destino fuera del directorio permitido");
			        }
			        Files.write(destination, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			
			        return filename;

					    } catch (IOException e) {
					        throw new RuntimeException("Fallo al almacenar el archivo", e);
					    }
}

		// Helpers
		private boolean startsWithAscii(byte[] data, String ascii) {
		    if (data == null || data.length < ascii.length()) return false;
		    byte[] p = ascii.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
		    for (int i = 0; i < p.length; i++) if (data[i] != p[i]) return false;
		    return true;
		}
		private boolean startsWith(byte[] data, byte[] prefix) {
		    if (data == null || data.length < prefix.length) return false;
		    for (int i = 0; i < prefix.length; i++) if (data[i] != prefix[i]) return false;
		    return true;
}


}

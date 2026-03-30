package com.example.demo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")

public class Usuario {


	@Id
    @Column(length = 36) // ajusta a tu longitud (UUID como texto = 36)
	private String userid;
	

	@NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60, unique = true)
	private String username;
	
	
	

	// ---- PASSWORD ----
	    // Recomendado: mínimo 8, máximo 100 (por si algún día guardas hashes largos)
	    // Si quieres política fuerte, añade @Pattern (ver más abajo).
	    @NotBlank(message = "La contraseña es obligatoria")
	    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
	    @Column(nullable = false, length = 100)
	    @JsonIgnore // evita exponerla al serializar la entidad
	    private String password;

	    

	    @Email(message = "Formato de email inválido")
	      @Size(max = 160, message = "El email no puede superar 160 caracteres")
	      @Column(length = 160, unique = true) // quita unique si no quieres restringir
	      private String email;


	    // ---- ESTADO ----
	       // Si manejas pocos estados, puedes validar por regex (o mejor usar Enum en opción B)
	       // Ejemplo: "A"=Activo, "I"=Inactivo (tu diagrama mostraba VARCHAR(2))
	       @Pattern(regexp = "^(A|I)$", message = "Estado debe ser 'A' (activo) o 'I' (inactivo)")
	       @Column(length = 2)
	       private String estado;


@Size(max = 50, message = "El rol no puede superar 50 caracteres")
    @Column(length = 50)
    private String rol;


	 @Size(max = 255, message = "La ruta/URL de la imagen no puede superar 255 caracteres")
	    @Column(length = 255)
	    private String imagen;


	
	

	public  static String  generarcodigo(int nro) {
		if(nro<9) {return "User00"+(nro+1);}
		if(nro>=9 && nro<99 ) {return "User0"+(nro+1);}
		if(nro>=99 && nro<999) {return "User"+nro+1;}
	return "";
	}




	public String getUserid() {
		return userid;
	}




	public void setUserid(String userid) {
		this.userid = userid;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}


	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	public String getRol() {
		return rol;
	}




	public void setRol(String rol) {
		this.rol = rol;
	}




	public String getImagen() {
		return imagen;
	}




	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	
	

	
	
}

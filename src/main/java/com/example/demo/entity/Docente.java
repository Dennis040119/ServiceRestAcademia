package com.example.demo.entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "docentes")

@EntityListeners(AuditingEntityListener.class)
public class Docente {
	
		@Id
		private String id_docentes;
	
	


	  // --- Relación 1:1 con Usuario (FK en docentes.id_usuario) ---

		@OneToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(
		    name = "id_usuario",
		    referencedColumnName = "userid",
		    nullable = false,
		    unique = true
		)
		@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	    private Usuario usuario;


	
	
	

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String nombres;
		
	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String apellidos;
		

	@Past(message = "La fecha de nacimiento debe estar en el pasado")
	@Column(name = "f_nacimiento")
	private LocalDate fechaNacimiento;


	@Size(max = 200)
    @Column(length = 200)
    private String direccion;


	 // 9 dígitos (p. ej., ES). Ajusta el patrón según tu país.
	@Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener 9 dígitos")
	 @Column(length = 15) // deja margen por si luego añades prefijo +34
	 private String telefono;


	@Email(message = "El email no tiene formato válido")
    @NotBlank
    @Size(max = 160)
    @Column(nullable = false, length = 160)
    private String email;


	@Size(max = 80)
    @Column(length = 80)
    private String area;


	
	public  static String  generarcodigo(int nro) {
		if(nro<9) {return "DOC000"+(nro+1);}
		if(nro>=9 && nro<99 ) {return "DOC0"+(nro+1);}
		if(nro>=99 && nro<999) {return "DOC"+nro+1;}
		
	return "";
	}
}

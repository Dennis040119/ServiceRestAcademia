package com.example.demo.entity;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "alumnos")
@Data
public class Alumno {
	
	@Id
	@Column(name = "id_alumnos", nullable = false)
	private String id;
	

	@NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    // Opcional: solo letras y espacios (incluye acentos y Ñ)
    @Pattern(regexp = "^[\\p{L} ]+$", message = "El nombre solo puede contener letras y espacios")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

	

	@NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 120, message = "Los apellidos no pueden superar 120 caracteres")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Los apellidos solo pueden contener letras y espacios")
    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
	    name = "Grado",
	    referencedColumnName = "id_grado",
	    nullable = false,
	    unique = true
	)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Grado grado;
	

	@Past(message = "La fecha de nacimiento debe estar en el pasado")
    @Column(name = "f_nacimiento")
    private LocalDate fNacimiento;


	@Size(max = 200, message = "La dirección no puede superar 200 caracteres")
    @Column(name = "direccion", length = 200)
    private String direccion;

    @Size(max = 100, message = "El nombre del padre no puede superar 100 caracteres")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "El nombre del padre solo puede contener letras y espacios")
    @Column(name = "padre", length = 100)
    private String padre;

    @Size(max = 100, message = "El nombre de la madre no puede superar 100 caracteres")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "El nombre de la madre solo puede contener letras y espacios")
    @Column(name = "madre", length = 100)
    private String madre;

	
	
	public  static String  generarcodigo(int nro) {
		if(nro<9) {return "ALU000"+(nro+1);}
		if(nro>=9 && nro<99 ) {return "ALU00"+(nro+1);}
		if(nro>=99 && nro<999) {return "ALU0"+nro+1;}
		if(nro>=999 && nro<9999) {return "ALU"+nro+1;}
	return "";
	}
	
}

package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "grado")
@Data
public class Grado {
	

	@Id
	@Column(name = "id_grado")
	private String idGrado;
	
	
	
    @Column(name = "nombre", length = 200)
	private String nombre;
		
	
	private String seccion;
	
	private int cantidad_alumnos;

}

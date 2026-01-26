package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private String id_grado;
	
	private String Nombre;
		
	
	private String seccion;
	
	private int cantidad_alumnos;

}

package com.example.demo.entity;

import java.sql.Time;
import java.time.LocalTime;
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
@Table(name = "curso")
@Data
public class Curso {
	
	@Id
	private String id_curso;
	
	private String nombre;
		
	
	private String area;
	
	private String id_grado;

	private String id_profesor;
	
	private int dia_semana;
	
	private LocalTime hora_inicio;
	
	private LocalTime hora_fin;
	
	private int duracion_min;
}

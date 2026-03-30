package com.example.demo.entity;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	@Column(name = "id_curso")
	private String id;
	
	private String nombre;
		
	
	private String area;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
	    name = "id_grado",
	    referencedColumnName = "id_grado",
	    nullable = false,
	    unique = true
	)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Grado grado;

	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
	    name = "id_profesor",
	    referencedColumnName = "id_docentes",
	    nullable = false,
	    unique = true
	)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Docente docente ;
	
	private int dia_semana;
	
	private LocalTime hora_inicio;
	
	private LocalTime hora_fin;
	
	private int duracion_min;
}

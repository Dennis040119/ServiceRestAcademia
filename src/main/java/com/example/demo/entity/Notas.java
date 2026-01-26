package com.example.demo.entity;


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
@Table(name = "notas")
@Data
public class Notas {
	
	@Id
	private String id_notas;
	
	private int nota_exam_parcial;
	private int nota_exam_final;
	private int nota_proyecto;
	private int nota_tareas;
		
	
	private String alumno_id;
	
	private String curso_id;
	private String docente_id;
	private String grado_id;
}

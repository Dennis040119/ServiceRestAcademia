package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
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
@Table(name = "alumnos")
@Data
public class Alumno {
	
	@Id
	
	private String id_alumnos;
	
	private String nombre;
	
	private String Apellidos;
	private String grado_cursado;
	
	private Date f_nacimiento;
	private String direccion;
	private String padre;
	private String madre;
	
	
	public  static String  generarcodigo(int nro) {
		if(nro<9) {return "ALU000"+(nro+1);}
		if(nro>=9 && nro<99 ) {return "ALU00"+(nro+1);}
		if(nro>=99 && nro<999) {return "ALU0"+nro+1;}
		if(nro>=999 && nro<9999) {return "ALU"+nro+1;}
	return "";
	}
	
}

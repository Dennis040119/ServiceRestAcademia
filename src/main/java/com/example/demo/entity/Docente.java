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
@Table(name = "docentes")
@Data
public class Docente {
	
	@Id
	private String id_docentes;
	private String id_usuario;
	private String nombres;
		
	
	private String apellidos;
	
	private Date f_nacimiento;
	private String direccion;
	private String telefono;
	private String email;
	private String area;

	
	public  static String  generarcodigo(int nro) {
		if(nro<9) {return "DOC000"+(nro+1);}
		if(nro>=9 && nro<99 ) {return "DOC0"+(nro+1);}
		if(nro>=99 && nro<999) {return "DOC"+nro+1;}
		
	return "";
	}
}

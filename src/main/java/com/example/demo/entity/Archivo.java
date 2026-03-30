package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "archivos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Archivo {
	


	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @EqualsAndHashCode.Include
	    private Long id;

	    @Column(name = "nombre", nullable = false, length = 255)
	    private String nombre;
	    
	    @Column(name = "nombrepila", length = 255)
	    private String nombrepila;

	    @Column(name = "tipo", nullable = false, length = 100)
	    private String tipo;




	    @Column(name = "carpeta", nullable = false, length = 255)
	       private String carpeta;

	       @Column(name = "tamanio")
	       private Long tamanio;
	       
	       @Column(name = "cursoid")
	       private String cursoid;

	       @Column(name = "fecha_creacion", updatable = false)
	       private LocalDateTime fechaCreacion;

	       

	       @PrePersist
	       public void prePersist() {
	           if (this.fechaCreacion == null) {
	               this.fechaCreacion = LocalDateTime.now();
	           }
	       }



}

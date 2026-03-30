package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.demo.utils.Enums.EstadoAsistencia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "asistencia")
public class Asistencia {
	
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	

	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "id_sesion", nullable = false,
	 referencedColumnName = "id_sesion")
	 private AsistenciaSesion sesion;


    // Si no tienes entidad Alumno, mantenlo como ID simple
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
	    name = "alumnos_id",
	    referencedColumnName = "id_alumnos",
	    nullable = false
	    
	)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    
    private Alumno alumno;

@	Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 12)
    private EstadoAsistencia estado = EstadoAsistencia.SIN_MARCAR;
    
   
 	@Column(name = "observacion", length = 255)
    private String observacion;
 	
		@LastModifiedBy
		@Column(name = "marcado_por")
		private String actualizadoPor;
		
		@LastModifiedDate
		@Column(name = "marcado_en")
		private LocalDateTime actualizadoEn;


 	
 	

   

   

}

package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.utils.Enums.EstadoSesion;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
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
@Table(name = "asistencia_sesion")
public class AsistenciaSesion {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long id;

	
	// Si no tienes entidad Alumno, mantenlo como ID simple
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(
		    name = "id_curso",
		    referencedColumnName = "id_curso",
		    nullable = false
		    
	)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Curso curso;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 10)
    private EstadoSesion estado = EstadoSesion.ABIERTA;

    @Column(name = "actualizada_por", nullable = false, length = 20)
    private String actualizadaPor;

    @Column(name = "actualizada_en", nullable = false)
    private LocalDateTime actualizadaEn;

    // ---- Relaciones con Asistencia (1:N) ----
    // Mapeo inverso: una sesión tiene muchas marcas de asistencia
    // No es obligatorio declararlo, pero es útil.
    // Evita fetch EAGER si no lo necesitas.
    // @OneToMany(mappedBy = "sesion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<Asistencia> asistencias = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void actualizarMarcaTemporal() {
        this.actualizadaEn = LocalDateTime.now();
    }


}

package com.ibm.academia.ruletaapirest.models.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "apuestas", schema = "ruleta")
public class Apuesta implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    @Column(name = "color")
    private String color;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    @Positive(message = "Debe ser mayor a 0")
    @Column(name = "numero")
    private Integer numero;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    @Positive(message = "Debe ser mayor a 0")
    @Column(name = "valor_apuestas")
    private Double valorApuesta;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @JsonIgnoreProperties("apuestas")
    @ManyToOne(optional = true,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "ruleta_id", foreignKey = @ForeignKey(name = "FK_RULETA_ID"))
    private Ruleta ruleta;

    public Apuesta(Long id, String color, Integer numero, Double valorApuesta) {
        super();
        this.id = id;
        this.color = color;
        this.numero = numero;
        this.valorApuesta = valorApuesta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Apuesta other = (Apuesta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @PrePersist
    private void antesPersistir()
    {
        this.fechaCreacion = new Date();
    }

    @PreUpdate
    private void antesActualizar()
    {
        this.fechaActualizacion = new Date();
    }
}

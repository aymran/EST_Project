package org.example.project_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salle")
    private Integer idSalle;

    @Column(name = "numero", nullable = false, unique = true, length = 50)
    private String numero;

    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @Column(length = 100)
    private String bloc;

    @Column(name = "boolean_disponible")
    private Boolean booleanDisponible;

    @OneToMany(mappedBy = "salle", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Examen> examens;


}
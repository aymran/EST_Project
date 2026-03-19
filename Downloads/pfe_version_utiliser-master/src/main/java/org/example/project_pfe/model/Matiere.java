package org.example.project_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/*
@Entity
@Table(name = "matiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matiere")
    private Integer idMatiere;

    @Column(nullable = false, length = 100)
    private String intitule;

    @Column(nullable = false)
    private Double coefficient;

    @Column(name = "volume_horaire")
    private Integer volumeHoraire;

    @Column(length = 50)
    private String semestre;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Examen> examens;


}
 */
@Entity
@Table(name = "matiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matiere")
    private Integer idMatiere;  // Change to 'id'

    @Column(nullable = false, length = 100)
    private String intitule;  // Change from 'intitule'

    @Column(name = "volumeHoraire")  // Change from 'volume_horaire'
    private Integer volumeHoraire;

    @Column(name = "enseignant_id")  // Add foreign key
    private Integer idEnseignant;

    @Column(name = "filiere_id")  // Add foreign key
    private Integer filiereId;

    @Column(nullable = false)
    private Double coefficient;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", insertable = false, updatable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "filiere_id", insertable = false, updatable = false)
    private Filiere filiere;

    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Examen> examens;
}
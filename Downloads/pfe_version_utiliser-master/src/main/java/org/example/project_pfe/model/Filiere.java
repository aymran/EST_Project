package org.example.project_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/*
@Entity
@Table(name = "filiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filiere")
    private Integer idFiliere;

    @Column(name = "intitule", nullable = false, unique = true, length = 100)
    private String intitule;

    @Column(name = "responsable", length = 100)
    private String responsable;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Etudiant> etudiants;


}*/

@Entity
@Table(
        name = "filiere",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"intitule", "semestre"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filiere")
    private Integer idFiliere;  // Change to 'id' for frontend compatibility

    @Column(name = "intitule", nullable = false, length = 100)  // Change 'intitule' to 'nom'
    private String intitule;

    @Column(name = "semestre", length = 10)  // Add semestre field
    private String semestre;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Etudiant> etudiants;
}
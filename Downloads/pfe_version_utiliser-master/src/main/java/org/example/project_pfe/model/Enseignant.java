package org.example.project_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("ENSEIGNANT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Enseignant extends Utilisateur {

    @Column(length = 100)
    private String grade;

    @Column(length = 100)
    private String specialite;

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Matiere> matieres;

    @OneToMany(mappedBy = "enseignant", fetch = FetchType.LAZY)
    private List<Note> notes;
    
}
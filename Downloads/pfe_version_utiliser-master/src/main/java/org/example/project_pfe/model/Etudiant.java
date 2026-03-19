package org.example.project_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("ETUDIANT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Etudiant extends Utilisateur {

    @Column(name = "numero_inscription", unique = true, length = 50)
    private String numeroInscription;

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    @ManyToOne
    @JoinColumn(name = "id_filiere")
    private Filiere filiere;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes;

}
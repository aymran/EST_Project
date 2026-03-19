package org.example.project_pfe.repository;

import java.util.Optional;

import org.example.project_pfe.model.Filiere;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepository extends JpaRepository<Filiere,Integer> {

    Optional<Filiere> findByIntitule(String intitule);
    Optional<Filiere> findByIntituleAndSemestre(String intitule, String semestre);


}

package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.NoteRequest;
import org.example.project_pfe.dto.response.NoteResponse;
import org.example.project_pfe.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    // Request -> Entity (single create)
    @Mapping(target = "idNote", ignore = true)
    @Mapping(target = "etudiant", source = "idEtudiant")
    @Mapping(target = "examen", source = "idExamen")
    @Mapping(target = "enseignant", source = "idEnseignant")
    Note requestToEntity(NoteRequest request);

    // Entity -> Response
    NoteResponse entityToResponse(Note entity);

    List<NoteResponse> entityToResponseList(List<Note> entities);

    // Helper methods
    default Etudiant mapEtudiant(Integer idEtudiant) {
        if (idEtudiant == null) return null;
        Etudiant etudiant = new Etudiant();
        etudiant.setIdUser(idEtudiant);
        return etudiant;
    }

    default Examen mapExamen(Integer idExamen) {
        if (idExamen == null) return null;
        Examen examen = new Examen();
        examen.setIdExamen(idExamen);
        return examen;
    }

    default Enseignant mapEnseignant(Integer idEnseignant) {
        if (idEnseignant == null) return null;
        Enseignant enseignant = new Enseignant();
        enseignant.setIdUser(idEnseignant);
        return enseignant;
    }
}
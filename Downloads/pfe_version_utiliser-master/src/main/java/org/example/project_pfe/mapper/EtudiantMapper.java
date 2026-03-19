package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.EtudiantRequest;
import org.example.project_pfe.dto.response.EtudiantResponse;
import org.example.project_pfe.model.Etudiant;
import org.example.project_pfe.model.Filiere;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EtudiantMapper {

    // Request -> Entity
    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "filiere", source = "idFiliere")
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "motDePasse" , ignore = true)
    Etudiant requestToEntity(EtudiantRequest request);

    // Entity -> Response
    EtudiantResponse entityToResponse(Etudiant entity);

    List<EtudiantResponse> entityToResponseList(List<Etudiant> entities);

    // Helper method pour mapper idFiliere -> Filiere
    default Filiere map(Integer idFiliere) {
        if (idFiliere == null) {
            return null;
        }
        Filiere filiere = new Filiere();
        filiere.setIdFiliere(idFiliere);
        return filiere;
    }
}

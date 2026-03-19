package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.MatiereRequest;
import org.example.project_pfe.dto.response.MatiereResponse;
import org.example.project_pfe.model.Enseignant;
import org.example.project_pfe.model.Matiere;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EnseignantMapper.class})
public interface MatiereMapper {

    // Request -> Entity
    @Mapping(target = "idMatiere", ignore = true)
    @Mapping(target = "enseignant", source = "idEnseignant")
    @Mapping(target = "examens", ignore = true)
    Matiere requestToEntity(MatiereRequest request);


    MatiereResponse entityToResponse(Matiere entity);

    List<MatiereResponse> entityToResponseList(List<Matiere> entities);

    // Helper method pour mapper idEnseignant -> Enseignant
    default Enseignant map(Integer idEnseignant) {
        if (idEnseignant == null) {
            return null;
        }
        Enseignant enseignant = new Enseignant();
        enseignant.setIdUser(idEnseignant);
        return enseignant;
    }
}
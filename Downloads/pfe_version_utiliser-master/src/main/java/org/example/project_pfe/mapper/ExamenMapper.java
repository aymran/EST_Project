package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.ExamenRequest;
import org.example.project_pfe.dto.response.ExamenResponse;
import org.example.project_pfe.model.Examen;
import org.example.project_pfe.model.Matiere;
import org.example.project_pfe.model.Salle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MatiereMapper.class, SalleMapper.class})
public interface ExamenMapper {

    // Request -> Entity (salle and duree are set manually in service)
    @Mapping(target = "idExamen", ignore = true)
    @Mapping(target = "matiere", source = "idMatiere")
    @Mapping(target = "salle", ignore = true)   // set manually in service
    @Mapping(target = "duree", ignore = true)   // calculated in service
    @Mapping(target = "notes", ignore = true)
    Examen requestToEntity(ExamenRequest request);

    // Entity -> Response
    ExamenResponse entityToResponse(Examen entity);

    List<ExamenResponse> entityToResponseList(List<Examen> entities);

    default Matiere mapMatiere(Integer idMatiere) {
        if (idMatiere == null) return null;
        Matiere matiere = new Matiere();
        matiere.setIdMatiere(idMatiere);
        return matiere;
    }

    default Salle mapSalle(Integer idSalle) {
        if (idSalle == null) return null;
        Salle salle = new Salle();
        salle.setIdSalle(idSalle);
        return salle;
    }
}
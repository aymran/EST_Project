package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.FiliereRequest;
import org.example.project_pfe.dto.response.FiliereResponse;
import org.example.project_pfe.model.Filiere;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FiliereMapper {

    // Request -> Entity
    @Mapping(target = "idFiliere", ignore = true)
    @Mapping(target = "etudiants", ignore = true)
    Filiere requestToEntity(FiliereRequest request);

    // Entity -> Response
    @Mapping(target = "idFiliere", source = "idFiliere")
    @Mapping(target = "semestre", source = "semestre")
    FiliereResponse entityToResponse(Filiere entity);

    List<FiliereResponse> entityToResponseList(List<Filiere> entities);
}
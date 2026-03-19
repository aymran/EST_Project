package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.EnseignantRequest;
import org.example.project_pfe.dto.response.EnseignantResponse;
import org.example.project_pfe.model.Enseignant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnseignantMapper {

    // Request -> Entity
    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "matieres", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "role", ignore =true)
    @Mapping(target = "motDePasse" , ignore = true)
    Enseignant requestToEntity(EnseignantRequest request);

    // Entity -> Response
    EnseignantResponse entityToResponse(Enseignant entity);

    List<EnseignantResponse> entityToResponseList(List<Enseignant> entities);
}
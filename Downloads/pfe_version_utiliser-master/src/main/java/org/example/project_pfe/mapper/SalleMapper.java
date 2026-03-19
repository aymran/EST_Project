package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.SalleRequest;
import org.example.project_pfe.dto.response.SalleResponse;
import org.example.project_pfe.model.Salle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalleMapper {

    // Request -> Entity
    @Mapping(target = "idSalle", ignore = true)
    @Mapping(target = "examens", ignore = true)
    Salle requestToEntity(SalleRequest request);

    // Entity -> Response
    SalleResponse entityToResponse(Salle entity);

    List<SalleResponse> entityToResponseList(List<Salle> entities);
}
package org.example.project_pfe.mapper;

import org.example.project_pfe.dto.request.PlanRequest;
import org.example.project_pfe.dto.response.PlanResponse;
import org.example.project_pfe.model.Planification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.*;

@Mapper(componentModel = "spring")
public interface planMapper {
 
    @Mapping(target="idPlan" , ignore = true)

    Planification requestToEntity(PlanRequest planification);

    PlanRequest entityToRequest(Planification planification);


    PlanResponse entityToResponse(Planification planification);
    
    List<PlanResponse> entityToResponseList(List<Planification> planifications);
}

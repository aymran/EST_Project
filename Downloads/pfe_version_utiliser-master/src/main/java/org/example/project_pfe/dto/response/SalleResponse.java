package org.example.project_pfe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalleResponse {

    private Integer idSalle;
    private String numero;
    private Integer capacite;
    private String bloc;
    private Boolean booleanDisponible;
}
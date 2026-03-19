package org.example.project_pfe.dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {

    private String uuid;
    private String fileName;
    private String fileType;
    private byte[] data;
    
}

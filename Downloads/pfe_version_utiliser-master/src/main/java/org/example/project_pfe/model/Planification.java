package org.example.project_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.annotation.processing.Generated;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlan;

    @Column(unique = true, nullable = false)
    private String uuid;

    private String fileName;
    private String fileType;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;
}

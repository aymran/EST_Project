package org.example.project_pfe.service;
import jakarta.persistence.EntityNotFoundException;
import org.example.project_pfe.dto.request.PlanRequest;
import org.example.project_pfe.dto.response.PlanResponse;
import org.example.project_pfe.mapper.planMapper;
import org.example.project_pfe.model.Planification;
import org.example.project_pfe.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final planMapper mapper;

    public PlanService(PlanRepository repo, planMapper mapper) {
        this.planRepository = repo;
        this.mapper = mapper;
    }

    @Transactional
    public PlanResponse UploadPlan(PlanRequest request) {
        try {
            Planification plan = new Planification();
            plan.setFileName(request.getFile().getOriginalFilename());
            plan.setFileType(request.getFile().getContentType());
            plan.setData(request.getFile().getBytes());
            plan.setUuid(UUID.randomUUID().toString());
            planRepository.save(plan);
            return mapper.entityToResponse(plan);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    // ← removed readOnly = true so the BLOB data is fully loaded before detaching
    @Transactional
    public Planification getFileByUuid(String uuid) {
        Planification plan = planRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("File not found: " + uuid));
        // Force the BLOB to load within the transaction
        if (plan.getData() != null) {
            int ignored = plan.getData().length;
        }
        return plan;
    }

    @Transactional
    public void deleteFile(String uuid) {
        if (!planRepository.existsByUuid(uuid)) {
            throw new EntityNotFoundException("Plan non trouvé");
        }
        planRepository.deleteByUuid(uuid);
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getAll() {
        return planRepository.findAll()
                .stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());
    }
}
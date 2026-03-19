package org.example.project_pfe.controller;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.model.Planification;
import org.example.project_pfe.service.PlanService;
import org.example.project_pfe.dto.request.PlanRequest;
import org.example.project_pfe.dto.response.PlanResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlanResponse> uploadPlan(@RequestParam("file") MultipartFile request) {
        PlanRequest pdf = new PlanRequest();
        pdf.setFile(request);
        PlanResponse response = planService.UploadPlan(pdf);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/view/{uuid}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String uuid) {
        Planification plan = planService.getFileByUuid(uuid);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(plan.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + plan.getFileName() + "\"")
                .body(plan.getData());
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid) {
        Planification plan = planService.getFileByUuid(uuid);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(plan.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + plan.getFileName() + "\"")
                .body(plan.getData());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteFile(@PathVariable String uuid) {
        planService.deleteFile(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<PlanResponse> getAllPlans() {
        return planService.getAll();
    }
}
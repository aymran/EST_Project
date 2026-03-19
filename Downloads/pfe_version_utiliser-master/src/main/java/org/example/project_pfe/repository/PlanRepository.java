package org.example.project_pfe.repository;

import org.example.project_pfe.model.Planification;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Planification , Integer>{


     Optional<Planification>findByUuid(String uuid);
     void deleteByUuid(String uuid);
     boolean existsByUuid(String uuid);
}

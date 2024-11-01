package io.github.hugogu.incidentmgr.service;

import io.github.hugogu.incidentmgr.domain.IncidentEntity;
import io.github.hugogu.incidentmgr.domain.IncidentRepository;
import io.github.hugogu.incidentmgr.domain.IncidentStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class IncidentService {
    private final IncidentRepository incidentRepo;

    public IncidentService(IncidentRepository incidentRepo) {
        this.incidentRepo = incidentRepo;
    }

    @Cacheable(value = "incident", key = "#incidentId")
    public IncidentEntity getIncident(@NotNull UUID incidentId) {
        log.info("Getting incident: {}", incidentId);
        return incidentRepo.findById(incidentId).orElseThrow(() -> {
            return new EntityNotFoundException("Can't find incident with id: " + incidentId);
        });
    }

    @Cacheable(value = "incident-list")
    public Page<IncidentEntity> searchIncidents(@NotNull IncidentEntity exampleEntity, Pageable pageable) {
        log.info("Searching incidents: {} (version is ignored.)", exampleEntity);
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("version")
                .withIgnoreNullValues();

        return incidentRepo.findAll(Example.of(exampleEntity, matcher), pageable);
    }

    @Transactional
    @CacheEvict(cacheNames = "incident-list", allEntries = true)
    public IncidentEntity logNewIncident(@NotNull IncidentEntity newIncident) {
        log.info("Logging new incident: {}", newIncident);
        return incidentRepo.save(newIncident);
    }

    @CacheEvict(cacheNames = "incident", key = "#incidentId")
    public IncidentEntity processIncident(@NotNull UUID incidentId) {
        return incidentRepo.updateIncidentBy(incidentId, e -> {
            if (e.getStatus() != IncidentStatus.OPEN && e.getStatus() != IncidentStatus.RESOLVING) {
                log.warn("Blocked a processing request for incident: {} with status: {}", incidentId, e.getStatus());
                throw new IllegalStateException("Incident cannot be processing because it is " + e.getStatus());
            }
            log.info("Set incident: {} to Resolving", incidentId);
            e.setStatus(IncidentStatus.RESOLVING);
        });
    }

    @CacheEvict(cacheNames = "incident", key = "#incidentId")
    public IncidentEntity closeIncident(@NotNull UUID incidentId) {
        return incidentRepo.updateIncidentBy(incidentId, e -> {
            // Allow closing an incident in any case.
            log.info("Set incident {} to Closed", incidentId);
            e.setStatus(IncidentStatus.CLOSED);
        });
    }

    @CacheEvict("incident")
    public IncidentEntity deleteIncident(@NotNull UUID incidentId) {
        return incidentRepo.updateIncidentBy(incidentId, e -> {
            log.info("Set incident: {} to Deleted", incidentId);
            e.setDeleted(true);
        });
    }
}

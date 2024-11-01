package io.github.hugogu.incidentmgr.service;

import io.github.hugogu.incidentmgr.domain.IncidentRepository;
import org.springframework.stereotype.Service;

@Service
public class IncidentService {
    private final IncidentRepository incidentRepo;

    public IncidentService(IncidentRepository incidentRepo) {
        this.incidentRepo = incidentRepo;
    }
}

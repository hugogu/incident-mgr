package io.github.hugogu.incidentmgr.api;

import io.github.hugogu.incidentmgr.service.IncidentService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/incident")
public class IncidentApiController {
    private final IncidentService incidentService;

    public IncidentApiController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }
}

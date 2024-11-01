package io.github.hugogu.incidentmgr.api;

import io.github.hugogu.incidentmgr.api.dto.IncidentView;
import io.github.hugogu.incidentmgr.api.dto.IncidentCreationRequest;
import io.github.hugogu.incidentmgr.domain.IncidentEntity;
import io.github.hugogu.incidentmgr.domain.IncidentStatus;
import io.github.hugogu.incidentmgr.service.IncidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class IncidentApiController {
    private final IncidentService incidentService;

    public IncidentApiController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping(value = {"/incident", "incident:fire"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IncidentView> fireIncident(@RequestBody IncidentCreationRequest incidentCreationRequest) {
        final IncidentEntity entity = incidentCreationRequest.createNewEntity();
        final IncidentEntity persistedEntity = incidentService.logNewIncident(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(IncidentView.from(persistedEntity));
    }

    @GetMapping(value = {"/incident/{incidentId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public IncidentView getIncident(@PathVariable UUID incidentId) {
        log.info("Getting incident: {}", incidentId);
        final IncidentEntity entity = incidentService.getIncident(incidentId);

        return IncidentView.from(entity);
    }

    @GetMapping(value = {"/incident"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<IncidentView> searchIncident(
            @RequestParam(required = false) String raiser,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            Pageable pageable
    ) {
        // TODO: Use reflection to build example entity so that all fields can be searched.
        final IncidentEntity exampleIncident = new IncidentEntity();
        exampleIncident.setRaiser(raiser);
        exampleIncident.setAssignee(assignee);
        if (status != null) {
            exampleIncident.setStatus(IncidentStatus.valueOf(status));
        }

        // TODO: Introduce Elasticsearch for full-text search
        exampleIncident.setTitle(title);
        exampleIncident.setDescription(description);
        log.info("Searching incidents like: {}", exampleIncident);
        final Page<IncidentEntity> entities = incidentService.searchIncidents(exampleIncident, pageable);

        return entities.map(IncidentView::from);
    }

    @PatchMapping(value = { "incident:close/{incidentId}" })
    public IncidentView closeIncident(@PathVariable UUID incidentId) {
        log.info("Closing incident: {}", incidentId);
        final IncidentEntity entity = incidentService.closeIncident(incidentId);

        return IncidentView.from(entity);
    }

    @PatchMapping(value = { "incident:process/{incidentId}" })
    public IncidentView processIncident(@PathVariable UUID incidentId) {
        log.info("Processing incident: {}", incidentId);
        final IncidentEntity entity = incidentService.processIncident(incidentId);

        return IncidentView.from(entity);
    }

    @DeleteMapping(value = { "incident/{incidentId}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public IncidentView deleteIncident(@PathVariable UUID incidentId) {
        log.info("Deleting incident: {}", incidentId);
        final IncidentEntity entity = incidentService.deleteIncident(incidentId);

        return IncidentView.from(entity);
    }
}

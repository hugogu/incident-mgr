package io.github.hugogu.incidentmgr.api.dto;

import io.github.hugogu.incidentmgr.domain.IncidentEntity;
import io.github.hugogu.incidentmgr.domain.IncidentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class IncidentCreationRequest {
    private String title;
    private String description;
    private String raiser;

    /**
     * TODO: Discuss
     * 1. If MapStruct is needed to do conversion between entities and dtos.
     * 2. Placement of such data conversion logic.
     */
    @NotNull
    public IncidentEntity createNewEntity() {
        IncidentEntity entity = new IncidentEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setRaiser(raiser);
        // Should not allow user to set status and occurring time in their request.
        entity.setStatus(IncidentStatus.OPEN);
        entity.setOccurringTime(Instant.now());
        // createTime, lastModifiedTime, version, and id will be set by JPA.

        return entity;
    }
}

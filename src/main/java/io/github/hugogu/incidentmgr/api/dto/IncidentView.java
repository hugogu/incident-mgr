package io.github.hugogu.incidentmgr.api.dto;

import io.github.hugogu.incidentmgr.domain.IncidentEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * External representation of an incident.
 */
@Data
public class IncidentView {
    private UUID id = null;

    private Instant occurringTime;

    private String raiser;

    private String title;

    private String description;

    private String status;

    private String resolution;

    @NotNull
    public static IncidentView from(@NotNull IncidentEntity entity) {
        IncidentView view = new IncidentView();
        view.setId(entity.getId());
        view.setOccurringTime(entity.getOccurringTime());
        view.setRaiser(entity.getRaiser());
        view.setTitle(entity.getTitle());
        view.setDescription(entity.getDescription());
        view.setStatus(entity.getStatus().name());
        if (entity.getResolution() != null) {
            view.setResolution(entity.getResolution().name());
        }
        // Other properties (especially version, creationTime and modifyTime) shall not be exposed.

        return view;
    }
}

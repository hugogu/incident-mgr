package io.github.hugogu.incidentmgr.api.dto;

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
}

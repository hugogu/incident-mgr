package io.github.hugogu.incidentmgr.api.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class IncidentSearchRequest {
    private String raiserName;
    private String titleKeyword;
    private String noteKeyword;
    private Instant creationTimeSince;
    private Instant creationTimeUntil;
}

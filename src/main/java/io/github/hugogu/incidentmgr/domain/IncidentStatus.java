package io.github.hugogu.incidentmgr.domain;

public enum IncidentStatus {
    /**
     * Incident is open and not assigned to anyone.
     */
    OPEN,
    /**
     * Incident is assigned but not yet start resolving.
     */
    ASSIGNED,
    /**
     * Incident is under resolution.
     */
    RESOLVING,
    /**
     * Incident is closed for any reason.
     */
    CLOSED,
}

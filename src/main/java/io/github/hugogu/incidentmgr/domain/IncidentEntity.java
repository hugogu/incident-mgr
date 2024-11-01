package io.github.hugogu.incidentmgr.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class IncidentEntity implements Persistable<UUID> {
    @Id
    private UUID id = null;

    private Instant occurringTime;

    @NotEmpty
    private String raiser;

    @NotEmpty
    @Size(min = 5)
    private String title;

    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    private IncidentStatus status;

    @Enumerated(value = EnumType.ORDINAL)
    private IncidentResolution resolution;

    @Version
    private int version = 0;

    @CreatedDate
    private Instant createTime;

    @LastModifiedDate
    private Instant lastModifiedTime;

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}

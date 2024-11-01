package io.github.hugogu.incidentmgr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "incident")
@SQLRestriction("deleted=false")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class IncidentEntity implements Persistable<UUID> {
    @Id
    @GeneratedValue
    private UUID id = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant occurringTime;

    @NotEmpty
    private String raiser;

    private String assignee;

    @NotEmpty
    @Size(min = 5)
    private String title;

    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    private IncidentStatus status;

    @Enumerated(value = EnumType.ORDINAL)
    private IncidentResolution resolution;

    private boolean deleted = false;

    @Version
    private int version = 0;

    @CreatedDate
    private Instant createTime;

    @Column(name = "last_update")
    @LastModifiedDate
    private Instant lastModifiedTime;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
}

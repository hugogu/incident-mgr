package io.github.hugogu.incidentmgr.domain;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Data access layer for IncidentEntity.
 *
 * Stick with single table approach for performance's sake.
 */
@Repository
@RepositoryRestResource(path = "incident")
public interface IncidentRepository extends JpaRepository<IncidentEntity, UUID> {
    @Transactional
    default IncidentEntity updateIncidentBy(@NotNull UUID incidentId, final @NotNull Consumer<IncidentEntity> updater) {
        final IncidentEntity entity = getReferenceById(incidentId);
        updater.accept(entity);
        return save(entity);
    }
}

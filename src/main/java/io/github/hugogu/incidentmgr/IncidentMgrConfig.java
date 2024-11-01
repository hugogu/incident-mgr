package io.github.hugogu.incidentmgr;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableCaching
@EnableJpaAuditing
@EnableTransactionManagement
public class IncidentMgrConfig {
}

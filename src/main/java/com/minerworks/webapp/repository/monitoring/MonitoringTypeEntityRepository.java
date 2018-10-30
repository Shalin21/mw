package com.minerworks.webapp.repository.monitoring;

import com.minerworks.webapp.model.monitoring.MonitoringTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoringTypeEntityRepository extends JpaRepository<MonitoringTypeEntity, Long> {
    MonitoringTypeEntity getMonitoringTypeEntityByMonitoringTypeName(String monitoringTypeName);
}

package com.minerworks.webapp.repository.monitoring;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.monitoring.MonitoringValueEntity;
import com.minerworks.webapp.model.monitoring.MonitoringValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringValueEntityRepository extends JpaRepository<MonitoringValueEntity, Long> {
    List<MonitoringValueEntity> findAllByFarmEntityAndAndMonitoringTypeEntity_MonitoringTypeName(FarmEntity farmEntity, String monitoringTypeName);
}

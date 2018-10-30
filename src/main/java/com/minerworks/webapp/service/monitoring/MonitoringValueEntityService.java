package com.minerworks.webapp.service.monitoring;

import com.minerworks.webapp.model.User;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

public interface MonitoringValueEntityService {
    void insertMonitoringValues(User user, String farmUUID, String monitoringName, String monitoringValuesJson) throws IOException;

    Map<Date, Integer> getMonitoringValues(User user, String farmUUID, String monitoringTypeName);
}

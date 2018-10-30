package com.minerworks.webapp.service.monitoring;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.monitoring.MonitoringTypeEntity;
import com.minerworks.webapp.model.monitoring.MonitoringValueEntity;
import com.minerworks.webapp.repository.FarmEntityRepository;
import com.minerworks.webapp.repository.monitoring.MonitoringTypeEntityRepository;
import com.minerworks.webapp.repository.monitoring.MonitoringValueEntityRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonitoringValueEntityServiceImpl implements MonitoringValueEntityService {

    @Autowired
    private MonitoringValueEntityRepository monitoringValueEntityRepository;

    @Autowired
    private MonitoringTypeEntityRepository monitoringTypeEntityRepository;

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    @Override
    public void insertMonitoringValues(User user, String farmUUID, String monitoringTypeName, String monitoringValuesJson) throws IOException {

        FarmEntity farmEntity = farmEntityRepository.getByUserAndFarmUUID(user, farmUUID);

        MonitoringTypeEntity monitoringTypeEntity = monitoringTypeEntityRepository.getMonitoringTypeEntityByMonitoringTypeName(monitoringTypeName);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(monitoringValuesJson);

        JsonNode monitoringValuesJsonNode = root.get("monitoringValues");

        Map<Date, Integer> monitoringValuesMap = mapper.convertValue(monitoringValuesJsonNode, Map.class);

        for (Map.Entry<Date, Integer> monitoringValueEntry : monitoringValuesMap.entrySet()) {
            Date monitoringDate = monitoringValueEntry.getKey();
            Integer monitoringValue = monitoringValueEntry.getValue();

            MonitoringValueEntity monitoringValueEntity = new MonitoringValueEntity();
            monitoringValueEntity.setFarmEntity(farmEntity);
            monitoringValueEntity.setMonitoringTypeEntity(monitoringTypeEntity);
            monitoringValueEntity.setMonitoringDate(monitoringDate);
            monitoringValueEntity.setMonitoringValue(monitoringValue);

            monitoringValueEntityRepository.save(monitoringValueEntity);
        }

    }

    @Override
    public Map<Date, Integer> getMonitoringValues(User user, String farmUUID, String monitoringTypeName) {
        FarmEntity farmEntity = farmEntityRepository.getByUserAndFarmUUID(user, farmUUID);

        return monitoringValueEntityRepository
                .findAllByFarmEntityAndAndMonitoringTypeEntity_MonitoringTypeName(farmEntity, monitoringTypeName)
                .stream()
                .collect(Collectors.toMap(MonitoringValueEntity::getMonitoringDate, MonitoringValueEntity::getMonitoringValue));
    }

}

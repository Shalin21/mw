package com.minerworks.webapp.controller.api.v1.client.farms.monitoring;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.monitoring.MonitoringValueEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.Date;
import java.util.Map;

/*
Decide later:
1. Leave monitoring type name as path variable
2. Create different endpoints for each monitoring type name
 */
@RestController
@RequestMapping("/api/v1/client/farms")
public class MonitoringRestControllerClient {

    private Logger logger = LoggerFactory.getLogger(MonitoringRestControllerClient.class);

    @Autowired
    private MonitoringValueEntityService monitoringValueEntityService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{farmUUID}/monitoring/{monitoringTypeName}")
    public  ResponseEntity<Map<Date, Integer>> getMonitoringForFarm(Principal principal, @PathVariable("farmUUID") String farmUUID, @PathVariable("monitoringTypeName") String monitoringTypeName) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Get monitoring info for user {} and farm (farm uuid) {}", user, farmUUID);

        return ResponseEntity.ok(monitoringValueEntityService.getMonitoringValues(user, farmUUID, monitoringTypeName));
    }


}

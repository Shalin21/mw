package com.minerworks.webapp.controller.api.v1.workstation.farms.monitoring;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.monitoring.MonitoringValueEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/workstation/farms")
public class MonitoringRestControllerWorkStation {

    private Logger logger = LoggerFactory.getLogger(MonitoringRestControllerWorkStation.class);

    @Autowired
    private MonitoringValueEntityService monitoringValueEntityService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{farmUUID}/monitoring/{monitoringTypeName}")
    public ResponseEntity<Void> postMonitoring(Principal principal, @PathVariable("farmUUID") String farmUUID, @PathVariable("monitoringTypeName") String monitoringTypeName, String monitoringValuesJson) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Add monitoring info for user {} and farm (farm uuid) {}", user, farmUUID);

        try {
            monitoringValueEntityService.insertMonitoringValues(user, farmUUID, monitoringTypeName, monitoringValuesJson);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

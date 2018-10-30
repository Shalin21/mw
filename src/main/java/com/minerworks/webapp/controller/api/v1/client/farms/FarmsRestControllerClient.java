package com.minerworks.webapp.controller.api.v1.client.farms;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.UserRepository;

import com.minerworks.webapp.service.FarmEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/client/farms")
public class FarmsRestControllerClient {

    private Logger logger = LoggerFactory.getLogger(FarmsRestControllerClient.class);

    @Autowired
    private FarmEntityService farmEntityService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Set<FarmEntity>> getAll(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        logger.debug("Get FarmEntities for user {}", user);

        return ResponseEntity.ok(farmEntityService.findAllByUser(user));
    }

    @GetMapping(value = "/{farmUUID}")
    public ResponseEntity<FarmEntity> getByFarmUUID(@PathVariable("farmUUID") String farmUUID) {
        logger.debug("Get FarmEntity by farm uuid {}", farmUUID);

        FarmEntity farmEntity = farmEntityService.findByFarmUUID(farmUUID);

        if (farmEntity == null) {
            System.out.println("FarmEntity with farm uuid " + farmUUID + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(farmEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{farmUUID}")
    public ResponseEntity<FarmEntity> delete(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        FarmEntity farmEntity = farmEntityService.getByUserAndFarmUUID(user, farmUUID);

        logger.debug("Deleting farm with farm uuid {} for user {} ", farmUUID, user);

        if (farmEntity == null) {
            logger.debug("Unable to delete. FarmEntity for user {} and farm (farm uuid) {} not found!", user, farmUUID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        farmEntityService.delete(farmEntity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

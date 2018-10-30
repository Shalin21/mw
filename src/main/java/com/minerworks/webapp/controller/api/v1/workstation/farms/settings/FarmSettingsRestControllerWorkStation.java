package com.minerworks.webapp.controller.api.v1.workstation.farms.settings;


import com.minerworks.webapp.model.FarmSettingsEntity;
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

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/v1/workstation/farms")
public class FarmSettingsRestControllerWorkStation {

    private Logger logger = LoggerFactory.getLogger(FarmSettingsRestControllerWorkStation.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FarmEntityService farmEntityService;

    @GetMapping(value = "/{farmUUID}/settings", produces = javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity<FarmSettingsEntity> getFarmSettingsEntity(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Get FarmSettingsEntity for user {} and farm (farm uuid) {}", user, farmUUID);

        FarmSettingsEntity farmSettingsEntity = farmEntityService.getFarmSettingsEntity(user, farmUUID);

        if (farmSettingsEntity == null) {
            logger.debug("FarmSettingsEntity for user {} and farm (farm uuid) {} not found!", user, farmUUID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(farmSettingsEntity, HttpStatus.OK);
    }

    /**
     * Put serves for
     * -create if not exists
     * -update existing
     * So:  use only put method for farm settings
     *
     * @param principal
     * @param farmUUID
     * @param farmSettingsEntity
     */
    @PutMapping(value = "/{farmUUID}/settings")
    @ResponseBody
    public ResponseEntity<FarmSettingsEntity> saveFarmSettingsEntity(Principal principal, @PathVariable("farmUUID") String farmUUID, @RequestBody FarmSettingsEntity farmSettingsEntity) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Saving FarmSettingsEntity for user {} and farm (farm uuid) {}", user, farmUUID);

        farmSettingsEntity = farmEntityService.updateFarmSettingsEntity(user, farmUUID, farmSettingsEntity);

        return new ResponseEntity<>(farmSettingsEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{farmUUID}/settings")
    public ResponseEntity<FarmSettingsEntity> deleteFarmSettingsEntity(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Deleting FarmSettingsEntity for user {} and farm (farm uuid) {}", user, farmUUID);

        FarmSettingsEntity farmSettingsEntity = farmEntityService.getFarmSettingsEntity(user, farmUUID);

        if (farmSettingsEntity == null) {
            logger.debug("Unable to delete! FarmSettingsEntity for user {} and farm (farm uuid) {} not found!", user, farmUUID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        farmEntityService.deleteFarmSettingsEntity(user, farmUUID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

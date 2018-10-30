package com.minerworks.webapp.controller.api.v1.workstation.farms.gpu;

import com.minerworks.webapp.dto.GpuCardParamsDTO;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.gpu.GpuProfileEntity;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.gpu.GpuCardEntityService;
import com.minerworks.webapp.service.gpu.GpuProfileEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
TODO: can merge setGpuCardCurrentParams & setCurrentGpuOverclockingProfile methods
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/v1/workstation/farms")
public class GpuRestControllerWorkStation {

    private Logger logger = LoggerFactory.getLogger(GpuRestControllerWorkStation.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GpuCardEntityService gpuCardEntityService;

    @Autowired
    private GpuProfileEntityService gpuProfileEntityService;

    /*
    workstation role
    used when user hits apply button on overclocking
     */
    @PostMapping(value = "/{farmUUID}/gpu/{gpuUUID}/params/current")
    public ResponseEntity<Void> setGpuCardCurrentParams(Principal principal,
                                                        @PathVariable("farmUUID") String farmUUID,
                                                        @PathVariable("gpuUUID") String gpuUUID,
                                                        @RequestBody GpuCardParamsDTO gpuCardParamsDTO) {

        User user = userRepository.findByEmail(principal.getName());

        logger.debug("Fetching & Setting Gpu Card Current Params. \n User: {} \n Farm (uuid): {} \n Gpu (uuid): {} \n", user, farmUUID, gpuUUID);

        GpuCardEntity gpuCardEntity = gpuCardEntityService.getGpuCardEntity(user, farmUUID, gpuUUID);

        if (gpuCardEntity == null) {
            logger.debug("GpuCardEntity not found. \n User: {} \n Farm (uuid): {} \n Gpu (uuid): {} \n", user, farmUUID, gpuUUID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpuCardEntityService.setGpuCardCurrentParams(gpuCardEntity, gpuCardParamsDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    workstation role
    used when user hits apply button on overclocking and certain profile is selected
     */
    @PostMapping(value = "/{farmUUID}/gpu/{gpuUUID}/profiles/current/{gpuProfileName}")
    public ResponseEntity<Void> setCurrentGpuOverclockingProfile(Principal principal,
                                                                 @PathVariable("farmUUID") String farmUUID,
                                                                 @PathVariable("gpuUUID") String gpuUUID,
                                                                 @PathVariable("gpuProfileName") String gpuProfileName) {

        User user = userRepository.findByEmail(principal.getName());

        logger.debug("Fetching & Setting Gpu Card Current Profile. \n " +
                "User: {} \n Gpu Profile Name: {}  \n Farm (uuid): {} \n Gpu (uuid): {}", user, gpuProfileName, farmUUID, gpuUUID);

        GpuCardEntity gpuCardEntity = gpuCardEntityService.getGpuCardEntity(user, farmUUID, gpuUUID);

        if (gpuCardEntity == null) {
            logger.debug("GpuCardEntity not found. \n User: {} \n Farm (uuid): {} \n Gpu (uuid): {} \n", user, farmUUID, gpuUUID);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpuCardEntityService.setCurrentGpuOverclockingProfile(user, gpuCardEntity, gpuProfileName);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /*
    all roles
     */
    @GetMapping(value = "/gpu/{gpuCardName}/profiles")
    public ResponseEntity<List<GpuProfileEntity>> getGpuOverclockingProfiles(Principal principal, @PathVariable("gpuCardName") String gpuCardName) {
        User user = userRepository.findByEmail(principal.getName());
        logger.debug("Get gpu profiles. \n User: {} \n Gpu card (name): {} \n", user, gpuCardName);
        return ResponseEntity.ok(gpuProfileEntityService.findAllByUserAndGpuCardName(user, gpuCardName));
    }

    /*
   workstation role
   used when used adds gpu profile
    */
    @PostMapping(value = "/gpu/profiles")
    public ResponseEntity<Void> addGpuOverclockingProfile(Principal principal, @RequestBody GpuProfileEntity gpuProfileEntity) {
        User user = userRepository.findByEmail(principal.getName());

        logger.debug("Add gpu profile. \n User: {} \n Gpu profile: {} \n", user, gpuProfileEntity);

        if (gpuProfileEntityService.userHasGpuProfile(user, gpuProfileEntity)) {
            logger.debug("Unable to add gpu profile, already has gpu profile. \n" +
                    "User: {} \n Gpu profile: {} \n", user, gpuProfileEntity);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        gpuProfileEntityService.save(user, gpuProfileEntity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
   workstation role
   used when user updates gpu profile
    */
    @PutMapping(value = "/gpu/profiles")
    public ResponseEntity<GpuProfileEntity> updateGpuOverclockingProfile(Principal principal, @RequestBody GpuProfileEntity gpuProfileEntity) {
        User user = userRepository.findByEmail(principal.getName());

        logger.debug("Update gpu profile. \n User: {} \n Gpu profile: {} \n", user, gpuProfileEntity);

        if (!gpuProfileEntityService.userHasGpuProfile(user, gpuProfileEntity)) {
            logger.debug("Unable to add gpu profile, not found. \n" +
                    "User: {} \n Gpu profile: {} \n", user, gpuProfileEntity);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpuProfileEntityService.save(user, gpuProfileEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
   workstation role
   used when user deletes gpu profile
    */
    @DeleteMapping(value = "/gpu/{gpuCardName}/profiles/{gpuProfileName}")
    public ResponseEntity<GpuProfileEntity> deleteGpuOverclockingProfile(Principal principal,
                                                                         @PathVariable("gpuCardName") String gpuCardName,
                                                                         @PathVariable("gpuProfileName") String gpuProfileName) {

        User user = userRepository.findByEmail(principal.getName());

        logger.debug("Deleting gpu profile. \n User: {} \n Gpu Card (name) \n Gpu profile (name): {} \n", gpuCardName, gpuProfileName, user);

        if (!gpuProfileEntityService.userHasGpuProfile(user, gpuCardName, gpuProfileName)) {
            logger.debug("Unable to delete, profile not found. \n User: {} \n Gpu Card (name) \n Gpu profile (name): {} \n", user, gpuCardName, gpuProfileName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gpuProfileEntityService.delete(user, gpuCardName, gpuProfileName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

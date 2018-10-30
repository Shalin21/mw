package com.minerworks.webapp.controller.api.v1.workstation.farms;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.FarmEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/workstation/farms")
public class FarmsRestControllerWorkStation {

    private Logger logger = LoggerFactory.getLogger(FarmsRestControllerWorkStation.class);

    @Autowired
    private FarmEntityService farmEntityService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Void> add(Principal principal, @RequestBody FarmEntity farmEntity, UriComponentsBuilder ucBuilder) {

        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Adding farm (farm uuid): {}. To User {} ", farmEntity.getFarmUUID(), user);

        if (farmEntityService.userHasFarm(user, farmEntity.getFarmUUID())) {
            logger.debug("User {} already has farm with farm uuid{} ", user, farmEntity.getFarmUUID());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        farmEntity.setUser(user);

        farmEntityService.save(farmEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{farmUUID}").buildAndExpand(farmEntity.getFarmUUID()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);

        /* TODO: refactor
        boolean flag = false;

        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        for (FarmEntity farmTemp : user.getFarmEntities()) {
            if (farmTemp.getName().equalsIgnoreCase(farmEntity.getName())) {
                flag = true;
            }
        }

//        FarmEntity farmEntity = new FarmEntity();
//        farmEntity.setFarmUUID(farmUUID);
//        farmEntity.setName(farmName);
        farmEntity.setUser(user);

//        Boolean farmExists = user.getFarmEntities()
//                .stream()
//                .

//        if (!user.getFarmEntities().contains(farmEntity)) {
        if (!flag) {
            farmEntityService.save(farmEntity);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        */
    }

    @RequestMapping(value = "/updateGpuCards/{farmUUID}") // TODO: move to gpu section
    public void updateGpuCards(Principal principal, @PathVariable("farmUUID") String farmUUID, Set<GpuCardEntity> gpuCardEntitySet) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        FarmEntity farmEntity = farmEntityService.findByFarmUUID(farmUUID);

        if (farmEntity != null && user.getFarmEntities().contains(farmEntity)) {
            if (!farmEntity.getGpuCardEntitySet().equals(gpuCardEntitySet)) {
                farmEntity.setGpuCardEntitySet(gpuCardEntitySet);
                farmEntityService.save(farmEntity);
            }
        }

    }

    @GetMapping(value = "/farmExists/{farmUUID}")
    public ResponseEntity<Boolean> userHasFarm(Principal principal, @PathVariable("farmUUID") String farmUUID) {
       /* TODO: refactor
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        FarmEntity farmEntity = farmEntityService.findByFarmUUID(farmUUID);

        logger.debug("Check if user {} has farm {}", user, farmEntity);

        return ResponseEntity.ok(user.getFarmEntities().contains(farmEntity));
*/

        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Check if user {} has farm with farm uuid {}", user, farmUUID);

        return ResponseEntity.ok(farmEntityService.userHasFarm(user, farmUUID));
    }

}

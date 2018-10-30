package com.minerworks.webapp.controller.api.v1.client.farms.mining;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

// TODO
@RestController
@RequestMapping("/api/v1/client/farms/mining")
public class MiningActionRestControllerClient {

    @PostMapping(value = "/{farmUUID}/mining/start")
    public ResponseEntity<Void> startMining(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{farmUUID}/mining/stop")
    public ResponseEntity<Void> stopMining(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

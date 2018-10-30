package com.minerworks.webapp.controller.api.v1.mining;

import com.minerworks.webapp.controller.api.v1.workstation.farms.benchmark.BenchmarkRestController;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.PoolEntity;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.mining.AlgorithmEntityService;
import com.minerworks.webapp.service.mining.PoolEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

/**
 * Empty responses in this rest controllers treat like errors.
 */
@RestController
@RequestMapping("/api/v1/mining")
public class MiningRestController {

    private Logger logger = LoggerFactory.getLogger(BenchmarkRestController.class);

    @Autowired
    private PoolEntityService poolEntityService;

    @Autowired
    private AlgorithmEntityService algorithmEntityService;

    @Autowired
    private UserRepository userRepository;

    /**
     * If empty - treat like error, because response must be not empty.
     * @param principal
     * @param farmUUID
     * @return
     */
    @GetMapping(value = "/coins/primary/{farmUUID}")
    public ResponseEntity<Set<String>> getPrimaryCoinList(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Get primary coin list for user {} and farm (farm uuid) {}", user, farmUUID);

        Set<String> primaryCoinSet = poolEntityService.getPrimaryCoinSet(user, farmUUID);

        if (primaryCoinSet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(primaryCoinSet, HttpStatus.OK);
    }

    @GetMapping(value = "/coins/secondary/{farmUUID}")
    public ResponseEntity<Set<String>> getSecondaryCoinList(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Get secondary coin list for user {} and farm (farm uuid) {}", user, farmUUID);

        Set<String> secondaryCoinSet = poolEntityService.getSecondaryCoinSet(user, farmUUID);

        if (secondaryCoinSet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(secondaryCoinSet, HttpStatus.OK);
    }

    @GetMapping(value = "/pools/coins/{coinName}")
    public ResponseEntity<Set<String>> getPoolListForCoin(@PathVariable("coinName") String coinName) {
        logger.debug("Get pool list for coin {}", coinName);

        Set<String> poolNamesSetForCoin = poolEntityService.getPoolNamesSetForCoin(coinName);

        if (poolNamesSetForCoin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(poolNamesSetForCoin, HttpStatus.OK);
    }

    @GetMapping(value = "/pools/{poolName}")
    public ResponseEntity<PoolEntity> getPoolEntity(@PathVariable("poolName") String poolName) {
        logger.debug("Get pool object by pool name {}", poolName);

        PoolEntity poolEntity = poolEntityService.getPoolEntity(poolName);

        if (poolEntity == null) {
            logger.debug("Pool entity by pool name {} was not found!", poolName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(poolEntity, HttpStatus.OK);
    }

    /**
     * TODO: note, exception may occur if coin name was not found, so test
     * @param coinName
     * @return
     */
    @GetMapping(value = "/coins/algorithms/{coinName}")
    public ResponseEntity<String> getCoinAlgorithm(@PathVariable("coinName") String coinName) {
        logger.debug("Get coin algorithm name by coin name {}", coinName);

        String algorithmName = algorithmEntityService.getCoinAlgorithm(coinName).getAlgorithmName();

        if (algorithmName == null || algorithmName.isEmpty()) {
            logger.debug("Coin algorithm name by coin name {} was not found!", coinName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(algorithmName, HttpStatus.OK);
    }

}

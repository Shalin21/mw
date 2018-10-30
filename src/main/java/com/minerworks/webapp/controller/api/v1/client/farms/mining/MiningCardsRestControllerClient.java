package com.minerworks.webapp.controller.api.v1.client.farms.mining;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import com.minerworks.webapp.repository.FarmEntityRepository;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.mining.MiningCardEntityService;
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

// TODO: activate/deactivate
// TODO: edit form (get)
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/v1/client/farms")
public class MiningCardsRestControllerClient {

    private Logger logger = LoggerFactory.getLogger(MiningCardsRestControllerClient.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    @Autowired
    private MiningCardEntityService miningCardEntityService;

    @GetMapping(value = "/{farmUUID}/mining/cards")
    @ResponseBody
    public ResponseEntity<Set<MiningCardEntity>> getAllMiningCardsForFarm(Principal principal, @PathVariable("farmUUID") String farmUUID) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Get Mining Cards for user {} and farm (farm uuid) {}", user, farmUUID);

        FarmEntity farmEntity = farmEntityRepository.getByUserAndFarmUUID(user, farmUUID);

        return ResponseEntity.ok(farmEntity.getMiningCardEntitySet());
    }

    @PostMapping(value = "/{farmUUID}/mining/cards")
    public ResponseEntity<Void> addMiningCard(Principal principal, @PathVariable("farmUUID") String farmUUID, @RequestBody MiningCardEntity miningCardEntity, UriComponentsBuilder ucBuilder) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Creating Mining Card for user {} and farm (farm uuid) {}", user, farmUUID);

        if (miningCardEntityService.isMiningCardExist(user, farmUUID, miningCardEntity)) {
            logger.debug("Mining Card for user {} and farm (farm uuid) {} \n" +
                            "with CoinOne {} PoolCoinOne {} \n" +
                            "and CoinTwo {} PoolCoinTwo {} \n" +
                            "already exists",
                    user, farmUUID,
                    miningCardEntity.getCoinOne(), miningCardEntity.getPoolCoinOne(),
                    miningCardEntity.getCoinTwo(), miningCardEntity.getPoolCoinTwo()
            );
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        miningCardEntityService.create(user, farmUUID, miningCardEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{farmUUID}/id").buildAndExpand(miningCardEntity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{farmUUID}/mining/cards")
    public ResponseEntity<MiningCardEntity> update(Principal principal, @PathVariable("farmUUID") String farmUUID, @RequestBody MiningCardEntity miningCardEntity) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Updating Mining Card for user {} and farm (farm uuid) {}", user, farmUUID);

        if (!miningCardEntityService.isMiningCardExist(user, farmUUID, miningCardEntity)) {
            logger.debug("Mining Card for user {} and farm (farm uuid) {} \n" +
                            "with CoinOne {} PoolCoinOne {} \n" +
                            "and CoinTwo {} PoolCoinTwo {} \n" +
                            "not found.",
                    user, farmUUID,
                    miningCardEntity.getCoinOne(), miningCardEntity.getPoolCoinOne(),
                    miningCardEntity.getCoinTwo(), miningCardEntity.getPoolCoinTwo()
            );
        }

        miningCardEntity = miningCardEntityService.update(user, farmUUID, miningCardEntity);

        return new ResponseEntity<>(miningCardEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{farmUUID}/mining/cards")
    public ResponseEntity<MiningCardEntity> delete(Principal principal, @PathVariable("farmUUID") String farmUUID, @RequestBody MiningCardEntity miningCardEntity) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        logger.debug("Deleting mining Card for user {} and farm (farm uuid) {} \n" +
                        "with CoinOne {} PoolCoinOne {} \n" +
                        "and CoinTwo {} PoolCoinTwo {}" +
                        user, farmUUID,
                miningCardEntity.getCoinOne(), miningCardEntity.getPoolCoinOne(),
                miningCardEntity.getCoinTwo(), miningCardEntity.getPoolCoinTwo()
        );

        if (!miningCardEntityService.isMiningCardExist(user, farmUUID, miningCardEntity)) {
            logger.debug("Mining Card for user {} and farm (farm uuid) {} \n" +
                            "with CoinOne {} PoolCoinOne {} \n" +
                            "and CoinTwo {} PoolCoinTwo {} \n" +
                            "not found.",
                    user, farmUUID,
                    miningCardEntity.getCoinOne(), miningCardEntity.getPoolCoinOne(),
                    miningCardEntity.getCoinTwo(), miningCardEntity.getPoolCoinTwo()
            );

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        miningCardEntityService.delete(user, farmUUID, miningCardEntity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

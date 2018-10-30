package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.CoinEntity;
import com.minerworks.webapp.model.mining.PoolEntity;
import com.minerworks.webapp.model.mining.SecondaryCoinAlgorithmEntity;
import com.minerworks.webapp.repository.mining.CoinEntityRepository;
import com.minerworks.webapp.repository.mining.PoolEntityRepository;
import com.minerworks.webapp.repository.mining.SecondaryCoinAlgorithmsEntityRepository;
import com.minerworks.webapp.repository.mining.WhatToMineEntityRepository;
import com.minerworks.webapp.service.FarmEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PoolEntityServiceImpl implements PoolEntityService {

    @Autowired
    private PoolEntityRepository poolEntityRepository;

    @Autowired
    private WhatToMineEntityRepository whatToMineEntityRepository;

    @Autowired
    private SecondaryCoinAlgorithmsEntityRepository secondaryCoinAlgorithmsEntityRepository;

    @Autowired
    private CoinEntityRepository coinEntityRepository;

    @Autowired
    private FarmEntityService farmEntityService;

    @Override
    public Set<String> getPrimaryCoinSet(User user, String farmUUID) {

        // get coin from whattomine table
        // or maybe better in memory list to exclude 1 db query
        Set<String> whatToMineCoins = whatToMineEntityRepository
                .findAll()
                .stream()
                .map(whatToMineCoinsEntity -> whatToMineCoinsEntity.getCoinEntity().getCoinName())
                .collect(Collectors.toSet());

        // get coin from pool table
        Set<String> poolCoins = poolEntityRepository
                .findAll()
                .stream()
                .flatMap(poolEntity -> poolEntity.getPoolCoinEntitySet().stream())
                .map(poolCoinEntity -> poolCoinEntity.getCoinEntity().getCoinName())
                .distinct()
                .collect(Collectors.toSet());

        // get coin from miner table
        Set<String> farmSupportedCoins = coinEntityRepository
                .findAllByAlgorithmEntityIn(farmEntityService.getSupportedAlgorithms(user, farmUUID))
                .stream()
                .map(CoinEntity::getCoinName)
                .collect(Collectors.toSet());

        // return intersection result
        return whatToMineCoins.stream()
                .filter(poolCoins::contains)
                .filter(farmSupportedCoins::contains)
                .sorted(String::compareTo)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSecondaryCoinSet(User user, String farmUUID) {
        // get coin from whattomine table
        // or maybe better in memory list to exclude 1 db query
        Set<String> whatToMineCoins = whatToMineEntityRepository
                .findAll()
                .stream()
                .map(whatToMineCoinsEntity -> whatToMineCoinsEntity.getCoinEntity().getCoinName())
                .collect(Collectors.toSet());

        // get coin from pool table
        Set<String> poolCoins = poolEntityRepository
                .findAll()
                .stream()
                .flatMap(poolEntity -> poolEntity.getPoolCoinEntitySet().stream())
                .map(poolCoinEntity -> poolCoinEntity.getCoinEntity().getCoinName())
                .distinct()
                .collect(Collectors.toSet());

        // get coin from miner table
        Set<String> farmSupportedCoins = coinEntityRepository
                .findAllByAlgorithmEntityIn(farmEntityService.getSupportedAlgorithms(user, farmUUID))
                .stream()
                .map(CoinEntity::getCoinName)
                .collect(Collectors.toSet());

        // get coin from miner table
        Set<String> secondaryCoins = coinEntityRepository
                .findAllByAlgorithmEntityIn(
                        secondaryCoinAlgorithmsEntityRepository.findAll()
                                .stream()
                                .map(SecondaryCoinAlgorithmEntity::getAlgorithmEntity)
                                .collect(Collectors.toSet())
                )
                .stream()
                .map(CoinEntity::getCoinName)
                .collect(Collectors.toSet());

        // return intersection result
        return whatToMineCoins.stream()
                .filter(poolCoins::contains)
                .filter(farmSupportedCoins::contains)
                .filter(secondaryCoins::contains)
                .sorted(String::compareTo)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getPoolNamesSetForCoin(String coinName) {
        return poolEntityRepository.findAll()
                .stream()
                .filter(poolEntity -> poolEntity.getPoolCoinEntitySet()
                        .stream()
                        .anyMatch(poolCoinEntity -> poolCoinEntity.getCoinEntity().getCoinName().equals(coinName)))
                .map(PoolEntity::getPoolName)

                .collect(Collectors.toSet());
    }

    @Override
    public PoolEntity getPoolEntity(String poolName) {
        return poolEntityRepository.getByPoolName(poolName);
    }
}

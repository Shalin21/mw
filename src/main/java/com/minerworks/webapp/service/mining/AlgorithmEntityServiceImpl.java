package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import com.minerworks.webapp.repository.mining.AlgorithmEntityRepository;
import com.minerworks.webapp.repository.mining.CoinEntityRepository;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import com.minerworks.webapp.repository.mining.AlgorithmEntityRepository;
import com.minerworks.webapp.repository.mining.CoinEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AlgorithmEntityServiceImpl implements AlgorithmEntityService {

    @Autowired
    private AlgorithmEntityRepository algorithmEntityRepository;

    @Autowired
    private CoinEntityRepository coinEntityRepository;

    @Override
    public AlgorithmEntity getCoinAlgorithm(CoinEntity coinEntity) {
        return algorithmEntityRepository.getByCoinEntitySetIsIn(coinEntity);
    }

    @Override
    public AlgorithmEntity getCoinAlgorithm(String coinName) {
        CoinEntity coinEntity = coinEntityRepository.getByCoinName(coinName);

        return getCoinAlgorithm(coinEntity);
    }
}

package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;

public interface AlgorithmEntityService {
    AlgorithmEntity getCoinAlgorithm(CoinEntity coinEntity);

    AlgorithmEntity getCoinAlgorithm(String coinName);
}

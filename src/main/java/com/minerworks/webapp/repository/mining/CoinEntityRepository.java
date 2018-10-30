package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CoinEntityRepository extends JpaRepository<CoinEntity, Long> {
    CoinEntity getByCoinName(String coinName);

    Set<CoinEntity> findAllByAlgorithmEntityIn(Set<AlgorithmEntity> algorithmEntitySet);
}

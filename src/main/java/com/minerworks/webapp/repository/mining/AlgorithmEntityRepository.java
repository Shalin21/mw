package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmEntityRepository extends JpaRepository<AlgorithmEntity, Long> {
    AlgorithmEntity getByAlgorithmName(String algorithmName);

    AlgorithmEntity getByCoinEntitySetIsIn(CoinEntity coinEntity);
}

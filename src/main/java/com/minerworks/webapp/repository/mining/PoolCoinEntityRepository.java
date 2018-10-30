package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.PoolCoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoolCoinEntityRepository extends JpaRepository<PoolCoinEntity, Long> {
}

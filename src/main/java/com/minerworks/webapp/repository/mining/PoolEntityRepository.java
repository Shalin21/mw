package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.PoolEntity;
import com.minerworks.webapp.model.mining.PortEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PoolEntityRepository extends JpaRepository<PoolEntity, Long> {
    PoolEntity getByPoolName(String poolName);
}

package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerEntityRepository extends JpaRepository<ServerEntity, Long> {
}

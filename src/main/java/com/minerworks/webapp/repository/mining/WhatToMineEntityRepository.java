package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.WhatToMineCoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhatToMineEntityRepository extends JpaRepository<WhatToMineCoinEntity, Long> {
}

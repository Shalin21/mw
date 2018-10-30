package com.minerworks.webapp.repository;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FarmEntityRepository extends JpaRepository<FarmEntity, Integer> {
    Set<FarmEntity> findAllByUser(User user);

    FarmEntity findByFarmUUID(String farmUUID);

    FarmEntity getByUserAndFarmUUID(User user, String farmUUID);
}

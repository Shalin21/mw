package com.minerworks.webapp.service;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.FarmSettingsEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.FarmSettingsEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.MinerEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FarmEntityService {
    List<FarmEntity> findAll();

    Set<FarmEntity> findAllByUser(User user);

    FarmEntity save(FarmEntity farmEntity);

    FarmEntity findByFarmUUID(String farmUUID);

    FarmEntity getByUserAndFarmUUID(User user, String farmUUID);

    Boolean userHasFarm(User user, String farmUUID);

    FarmSettingsEntity getFarmSettingsEntity(User user, String farmUUID);

    FarmSettingsEntity createFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity);

    FarmSettingsEntity updateFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity);

    FarmSettingsEntity saveFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity);

    void deleteFarmSettingsEntity(User user, String farmUUID);

    Set<MinerEntity> getSupportedMiners(User user, String farmUUID);

    Set<AlgorithmEntity> getSupportedAlgorithms(User user, String farmUUID);

    void delete(FarmEntity farmEntity);

    Set<GpuCardEntity> getGpuCardEntitySet(User user, String farmUUID);
}

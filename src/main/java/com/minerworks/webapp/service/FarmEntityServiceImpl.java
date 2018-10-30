package com.minerworks.webapp.service;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.FarmSettingsEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import com.minerworks.webapp.repository.FarmEntityRepository;
import com.minerworks.webapp.repository.FarmSettingsEntityRepository;
import com.minerworks.webapp.repository.mining.MinerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FarmEntityServiceImpl implements FarmEntityService {

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    @Autowired
    private FarmSettingsEntityRepository farmSettingsEntityRepository;

    @Autowired
    private MinerEntityRepository minerEntityRepository;

    @Override
    public List<FarmEntity> findAll() {
        return farmEntityRepository.findAll();
    }

    @Override
    public FarmEntity save(FarmEntity farmEntity) {
        return farmEntityRepository.save(farmEntity);
    }

    @Override
    public FarmEntity findByFarmUUID(String farmUUID) {
        return farmEntityRepository.findByFarmUUID(farmUUID);
    }

    @Override
    public FarmEntity getByUserAndFarmUUID(User user, String farmUUID) {
        return farmEntityRepository.getByUserAndFarmUUID(user, farmUUID);
    }

    @Override
    public FarmSettingsEntity getFarmSettingsEntity(User user, String farmUUID) {
        FarmEntity farmEntity = getByUserAndFarmUUID(user, farmUUID);
        return farmEntity.getFarmSettingsEntity();
    }

//    @Override
//    public FarmSettingsEntity createFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity) {
//        FarmEntity farmEntity = getByUserAndFarmUUID(user, farmUUID);
//
//        farmSettingsEntity.setFarmEntity(farmEntity);
//        farmEntity.setFarmSettingsEntity(farmSettingsEntity);
//
//        farmSettingsEntity = farmSettingsEntityRepository.save(farmSettingsEntity);
//
//        farmEntityRepository.save(farmEntity);
//
//        return farmSettingsEntity;
//    }

//    @Override
//    public FarmSettingsEntity updateFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity) {
//        FarmEntity farmEntity = getByUserAndFarmUUID(user, farmUUID);
//
//        FarmSettingsEntity farmSettingsEntityFetched = farmEntity.getFarmSettingsEntity();
//
//        farmSettingsEntityFetched.setTheme(farmSettingsEntity.getTheme());
//        farmSettingsEntityFetched.setLanguage(farmSettingsEntity.getLanguage());
//        farmSettingsEntityFetched.setRunWithWindows(farmSettingsEntity.getRunWithWindows());
//        farmSettingsEntityFetched.setMinimizeToTray(farmSettingsEntity.getMinimizeToTray());
//        farmSettingsEntityFetched.setHideMiningWindow(farmSettingsEntity.getHideMiningWindow());
//        farmSettingsEntityFetched.setAutostartMining(farmSettingsEntity.getAutostartMining());
//        farmSettingsEntityFetched.setIdleMining(farmSettingsEntity.getIdleMining());
//        farmSettingsEntityFetched.setIdleMiningSeconds(farmSettingsEntity.getIdleMiningSeconds());
//        farmSettingsEntityFetched.setCurrencyForDailyRevenue(farmSettingsEntity.getCurrencyForDailyRevenue());
//        farmSettingsEntityFetched.setProfitabilityCheckTimeMinutes(farmSettingsEntity.getProfitabilityCheckTimeMinutes());
//        farmSettingsEntityFetched.setChartsRefreshTimeSeconds(farmSettingsEntity.getChartsRefreshTimeSeconds());
//
//        farmSettingsEntityFetched = farmSettingsEntityRepository.save(farmSettingsEntityFetched);
//
//        return farmSettingsEntityFetched;
//    }

    @Override
    public FarmSettingsEntity saveFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity) {
        FarmEntity farmEntity = getByUserAndFarmUUID(user, farmUUID);

        FarmSettingsEntity farmSettingsEntityFetched = farmEntity.getFarmSettingsEntity();

        farmSettingsEntityFetched.setTheme(farmSettingsEntity.getTheme());
        farmSettingsEntityFetched.setLanguage(farmSettingsEntity.getLanguage());
        farmSettingsEntityFetched.setRunWithWindows(farmSettingsEntity.getRunWithWindows());
        farmSettingsEntityFetched.setMinimizeToTray(farmSettingsEntity.getMinimizeToTray());
        farmSettingsEntityFetched.setHideMiningWindow(farmSettingsEntity.getHideMiningWindow());
        farmSettingsEntityFetched.setAutostartMining(farmSettingsEntity.getAutostartMining());
        farmSettingsEntityFetched.setIdleMining(farmSettingsEntity.getIdleMining());
        farmSettingsEntityFetched.setIdleMiningSeconds(farmSettingsEntity.getIdleMiningSeconds());
        farmSettingsEntityFetched.setCurrencyForDailyRevenue(farmSettingsEntity.getCurrencyForDailyRevenue());
        farmSettingsEntityFetched.setProfitabilityCheckTimeMinutes(farmSettingsEntity.getProfitabilityCheckTimeMinutes());
        farmSettingsEntityFetched.setChartsRefreshTimeSeconds(farmSettingsEntity.getChartsRefreshTimeSeconds());

        farmSettingsEntityFetched = farmSettingsEntityRepository.save(farmSettingsEntityFetched);

        return farmSettingsEntityFetched;
    }

    @Override
    public void deleteFarmSettingsEntity(User user, String farmUUID) {
        FarmEntity farmEntity = getByUserAndFarmUUID(user, farmUUID);

        farmSettingsEntityRepository.delete(farmEntity.getFarmSettingsEntity());

        farmEntity.setFarmSettingsEntity(null);

        farmEntityRepository.save(farmEntity);
    }

    @Override
    public Set<MinerEntity> getSupportedMiners(User user, String farmUUID) {
        FarmEntity farmEntity = farmEntityRepository.getByUserAndFarmUUID(user, farmUUID);

        Set<GpuVendorEntity> farmSupportedGpuVendors = farmEntity.getGpuCardEntitySet()
                .stream()
                .map(GpuCardEntity::getGpuVendorEntity)
                .distinct()
                .collect(Collectors.toSet());

        @SuppressWarnings("UnnecessaryLocalVariable")
        Set<MinerEntity> farmSupportedMiners = minerEntityRepository
                .getAllByGpuVendorEntitySetIn(farmSupportedGpuVendors);

        return farmSupportedMiners;
    }

    @Override
    public Set<AlgorithmEntity> getSupportedAlgorithms(User user, String farmUUID) {
        return getSupportedMiners(user, farmUUID)
                .stream()
                .flatMap(minerEntity -> minerEntity.getAlgorithmEntitySet().stream())
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GpuCardEntity> getGpuCardEntitySet(User user, String farmUUID) {
        return farmEntityRepository.getByUserAndFarmUUID(user, farmUUID).getGpuCardEntitySet();
    }

    @Override
    public Set<FarmEntity> findAllByUser(User user) {
        return null;
    }

    @Override
    public Boolean userHasFarm(User user, String farmUUID) {
        return null;
    }

    @Override
    public FarmSettingsEntity createFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity) {
        return null;
    }

    @Override
    public FarmSettingsEntity updateFarmSettingsEntity(User user, String farmUUID, FarmSettingsEntity farmSettingsEntity) {
        return null;
    }

    @Override
    public void delete(FarmEntity farmEntity) {

    }
}

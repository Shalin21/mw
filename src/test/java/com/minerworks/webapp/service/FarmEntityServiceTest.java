package com.minerworks.webapp.service;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.FarmSettingsEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.repository.gpu.GpuCardEntityRepository;
import com.minerworks.webapp.repository.gpu.GpuVendorEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/*
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
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FarmEntityServiceTest {

    private Logger logger = LoggerFactory.getLogger(FarmEntityServiceTest.class);

    @Autowired
    private FarmEntityService farmEntityService;

    @Autowired
    private GpuVendorEntityRepository gpuVendorEntityRepository;

    @Autowired
    private GpuCardEntityRepository gpuCardEntityRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_FARM_CODE = "test-farm-code";

    @Test
    public void findAll_Test() {
        farmEntityService.findAll().forEach(System.out::println);
    }

    @Test
    public void findAllByUser_Test() {
        farmEntityService.findAllByUser(getTestUser()).forEach(System.out::println);
    }

    @Test
    public void findByFarmUUID_Test() {
        FarmEntity farmEntity = farmEntityService.findByFarmUUID(TEST_FARM_CODE);

        assertNotNull(farmEntity);
        System.out.println(farmEntity);
    }

    @Test
    public void getFarmSettingsEntity_Test() {
        FarmSettingsEntity farmSettingsEntity = farmEntityService.getFarmSettingsEntity(getTestUser(), TEST_FARM_CODE);

        assertNotNull(farmSettingsEntity);
        System.out.println(farmSettingsEntity);
    }

    @Test
    public void createFarmSettingsEntity_Test() {
        FarmSettingsEntity testFarmSettingsEntity = getFarmSettingsEntity();
        //noinspection UnusedAssignment
        FarmSettingsEntity farmSettingsEntity = farmEntityService.createFarmSettingsEntity(getTestUser(), TEST_FARM_CODE, testFarmSettingsEntity);

        farmSettingsEntity = farmEntityService.getFarmSettingsEntity(getTestUser(), TEST_FARM_CODE);

        assertNotNull(farmSettingsEntity);

        assertTrue(
                farmSettingsEntity.getTheme().equals(testFarmSettingsEntity.getTheme())
                        && farmSettingsEntity.getLanguage().equals(testFarmSettingsEntity.getLanguage())
                        && farmSettingsEntity.getRunWithWindows().equals(testFarmSettingsEntity.getRunWithWindows())
                        && farmSettingsEntity.getMinimizeToTray().equals(testFarmSettingsEntity.getMinimizeToTray())
                        && farmSettingsEntity.getHideMiningWindow().equals(testFarmSettingsEntity.getHideMiningWindow())
                        && farmSettingsEntity.getAutostartMining().equals(testFarmSettingsEntity.getAutostartMining())
                        && farmSettingsEntity.getIdleMining().equals(testFarmSettingsEntity.getIdleMining())
                        && farmSettingsEntity.getIdleMiningSeconds().equals(testFarmSettingsEntity.getIdleMiningSeconds())
                        && farmSettingsEntity.getCurrencyForDailyRevenue().equals(testFarmSettingsEntity.getCurrencyForDailyRevenue())
                        && farmSettingsEntity.getProfitabilityCheckTimeMinutes().equals(testFarmSettingsEntity.getProfitabilityCheckTimeMinutes())
                        && farmSettingsEntity.getChartsRefreshTimeSeconds().equals(testFarmSettingsEntity.getChartsRefreshTimeSeconds())
        );

        System.out.println(farmSettingsEntity);
    }

    @Test
    public void updateFarmSettingsEntity_Test() {
        FarmSettingsEntity testFarmSettingsEntityUpdated = getFarmSettingsEntity_Updated();
        //noinspection UnusedAssignment
        FarmSettingsEntity farmSettingsEntity = farmEntityService.updateFarmSettingsEntity(getTestUser(), TEST_FARM_CODE, getFarmSettingsEntity_Updated());

        farmSettingsEntity = farmEntityService.getFarmSettingsEntity(getTestUser(), TEST_FARM_CODE);

        assertNotNull(farmSettingsEntity);

        assertTrue(
                farmSettingsEntity.getTheme().equals(testFarmSettingsEntityUpdated.getTheme())
                        && farmSettingsEntity.getLanguage().equals(testFarmSettingsEntityUpdated.getLanguage())
                        && farmSettingsEntity.getRunWithWindows().equals(testFarmSettingsEntityUpdated.getRunWithWindows())
                        && farmSettingsEntity.getMinimizeToTray().equals(testFarmSettingsEntityUpdated.getMinimizeToTray())
                        && farmSettingsEntity.getHideMiningWindow().equals(testFarmSettingsEntityUpdated.getHideMiningWindow())
                        && farmSettingsEntity.getAutostartMining().equals(testFarmSettingsEntityUpdated.getAutostartMining())
                        && farmSettingsEntity.getIdleMining().equals(testFarmSettingsEntityUpdated.getIdleMining())
                        && farmSettingsEntity.getIdleMiningSeconds().equals(testFarmSettingsEntityUpdated.getIdleMiningSeconds())
                        && farmSettingsEntity.getCurrencyForDailyRevenue().equals(testFarmSettingsEntityUpdated.getCurrencyForDailyRevenue())
                        && farmSettingsEntity.getProfitabilityCheckTimeMinutes().equals(testFarmSettingsEntityUpdated.getProfitabilityCheckTimeMinutes())
                        && farmSettingsEntity.getChartsRefreshTimeSeconds().equals(testFarmSettingsEntityUpdated.getChartsRefreshTimeSeconds())
        );

        System.out.println(farmSettingsEntity);
    }


    @Test
    public void deleteFarmSettingsEntity_Test() {
        farmEntityService.deleteFarmSettingsEntity(getTestUser(), TEST_FARM_CODE);

        FarmSettingsEntity farmSettingsEntity = farmEntityService.getFarmSettingsEntity(getTestUser(), TEST_FARM_CODE);

        assertNull(farmSettingsEntity);
    }


    @Test
    public void getSupportedMiners_Test() {

        prepareFarmEntity();

        Set<MinerEntity> minerEntitySet = farmEntityService.getSupportedMiners(getTestUser(), TEST_FARM_CODE);

        assertFalse(minerEntitySet.isEmpty());
        System.out.println(minerEntitySet);
    }

    @Test
    public void getSupportedAlgorithms_Test() {

        prepareFarmEntity();

        Set<AlgorithmEntity> algorithmEntitySet = farmEntityService.getSupportedAlgorithms(getTestUser(), TEST_FARM_CODE);

        assertFalse(algorithmEntitySet.isEmpty());
        System.out.println(algorithmEntitySet);
    }

    @Test
    public void getGpuCardEntitySet_Test() {

        prepareFarmEntity();

        Set<GpuCardEntity> gpuCardEntitySet = farmEntityService.getGpuCardEntitySet(getTestUser(), TEST_FARM_CODE);

        assertFalse(gpuCardEntitySet.isEmpty());
        System.out.println(gpuCardEntitySet);
    }


    /*
    If test entity has no GPU - create dummy and fill gpu vendor
     */
    private void prepareFarmEntity() {
        FarmEntity farmEntity = farmEntityService.findByFarmUUID(TEST_FARM_CODE);

        if (farmEntity.getGpuCardEntitySet().isEmpty()) {
            GpuCardEntity gpuCardEntity = new GpuCardEntity();
            GpuVendorEntity gpuVendorEntity = gpuVendorEntityRepository.getByGpuVendorName("nvidia");

            gpuCardEntity.setGpuVendorEntity(gpuVendorEntity);

            farmEntity.addGpuCardEntity(gpuCardEntity);

            gpuCardEntityRepository.save(gpuCardEntity);

            farmEntityService.save(farmEntity);
        }
    }

    private User getTestUser() {
        return userRepository.findByEmail("email@email.com");
    }

    private FarmSettingsEntity getFarmSettingsEntity() {

        FarmSettingsEntity farmSettingsEntity = new FarmSettingsEntity();
        farmSettingsEntity.setTheme("dark");
        farmSettingsEntity.setLanguage("en");
        farmSettingsEntity.setRunWithWindows(false);
        farmSettingsEntity.setMinimizeToTray(false);
        farmSettingsEntity.setHideMiningWindow(false);
        farmSettingsEntity.setAutostartMining(false);
        farmSettingsEntity.setIdleMining(false);
        farmSettingsEntity.setIdleMiningSeconds(0);
        farmSettingsEntity.setCurrencyForDailyRevenue("USD");
        farmSettingsEntity.setProfitabilityCheckTimeMinutes(2);
        farmSettingsEntity.setChartsRefreshTimeSeconds(2);

        return farmSettingsEntity;
    }

    private FarmSettingsEntity getFarmSettingsEntity_Updated() {

        FarmSettingsEntity farmSettingsEntity = new FarmSettingsEntity();

        farmSettingsEntity.setTheme("light");
        farmSettingsEntity.setLanguage("ru");
        farmSettingsEntity.setRunWithWindows(false);
        farmSettingsEntity.setMinimizeToTray(false);
        farmSettingsEntity.setHideMiningWindow(false);
        farmSettingsEntity.setAutostartMining(false);
        farmSettingsEntity.setIdleMining(false);
        farmSettingsEntity.setIdleMiningSeconds(0);
        farmSettingsEntity.setCurrencyForDailyRevenue("USD");
        farmSettingsEntity.setProfitabilityCheckTimeMinutes(2);
        farmSettingsEntity.setChartsRefreshTimeSeconds(2);

        return farmSettingsEntity;
    }

}

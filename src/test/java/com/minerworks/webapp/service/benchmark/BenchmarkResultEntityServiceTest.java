package com.minerworks.webapp.service.benchmark;

import com.minerworks.webapp.dto.BenchmarkDTO;
import com.minerworks.webapp.model.benchmark.BenchmarkResultEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/*
Tests passing but need some improvement
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BenchmarkResultEntityServiceTest {

    private Logger logger = LoggerFactory.getLogger(BenchmarkResultEntityServiceTest.class);

    @Autowired
    private BenchmarkEntityService benchmarkEntityService;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_FARM_CODE = "test-farm-code";
    private static final String TEST_GPU_UUID = "test-gpu-uuid";
    private static final String TEST_ALGORITHM_NAME = "dagger_hashimoto";
    private static final String TEST_MINER_NAME = "claymore_dual";
    private static final Long TEST_HASHRATE = 11111L;
    private static final Long TEST_HASHRATE_UPDATED = 22222L;

    @Test
    public void createFromBenchmarkClientEntity_Test() {
        User testUser = getTestUser();
        BenchmarkDTO testBenchmarkDTO = getBenchmarkClientEntity();

        BenchmarkResultEntity benchmarkResultEntity = benchmarkEntityService.createFromBenchmarkClientEntity(testUser, testBenchmarkDTO);

        logger.debug("benchmarkEntityService.findAll(): " + benchmarkEntityService.findAll().toString());

        logger.debug("Created benchmark entity: " + benchmarkResultEntity.toString());

        assertTrue(
                benchmarkResultEntity.getGpuCardEntity().getGpuUUID().equals(testBenchmarkDTO.getGpuUUID())
                && benchmarkResultEntity.getAlgorithmEntity().getAlgorithmName().equals(testBenchmarkDTO.getAlgorithmName())
                && benchmarkResultEntity.getMinerEntity().getMinerName().equals(testBenchmarkDTO.getMinerName())
                && benchmarkResultEntity.getHashrate().equals(testBenchmarkDTO.getHashrate())
        );
    }

    @Test
    public void getFromBenchmarkClientEntity_Test() {
        User testUser = getTestUser();
        BenchmarkDTO testBenchmarkDTO = getBenchmarkClientEntity();

        BenchmarkResultEntity benchmarkResultEntity = benchmarkEntityService.updateFromBenchmarkClientEntity(testUser, testBenchmarkDTO);

        assertNotNull(benchmarkResultEntity);

        logger.debug("Got benchmark entity: " + benchmarkResultEntity.toString());

        assertTrue(
                benchmarkResultEntity.getGpuCardEntity().getGpuUUID().equals(testBenchmarkDTO.getGpuUUID())
                        && benchmarkResultEntity.getAlgorithmEntity().getAlgorithmName().equals(testBenchmarkDTO.getAlgorithmName())
                        && benchmarkResultEntity.getMinerEntity().getMinerName().equals(testBenchmarkDTO.getMinerName())
                        && benchmarkResultEntity.getHashrate().equals(testBenchmarkDTO.getHashrate())
        );
    }

    @Test
    public void updateFromBenchmarkClientEntity_Test() {
        User testUser = getTestUser();
        BenchmarkDTO testBenchmarkDTO = getBenchmarkClientEntity_ForUpdate();

        BenchmarkResultEntity benchmarkResultEntity = benchmarkEntityService.updateFromBenchmarkClientEntity(testUser, testBenchmarkDTO);

        logger.debug("benchmarkEntityService.findAll(): " + benchmarkEntityService.findAll().toString());

        logger.debug("Created benchmark entity: " + benchmarkResultEntity.toString());

        assertTrue(
                benchmarkResultEntity.getGpuCardEntity().getGpuUUID().equals(testBenchmarkDTO.getGpuUUID())
                        && benchmarkResultEntity.getAlgorithmEntity().getAlgorithmName().equals(testBenchmarkDTO.getAlgorithmName())
                        && benchmarkResultEntity.getMinerEntity().getMinerName().equals(testBenchmarkDTO.getMinerName())
                        && benchmarkResultEntity.getHashrate().equals(testBenchmarkDTO.getHashrate())
        );
    }

    @Test
    public void deleteBenchmarkResults_Test() {
        User testUser = getTestUser();

        benchmarkEntityService.deleteBenchmarkResults(testUser, TEST_FARM_CODE);

        logger.debug("benchmarkEntityService.findAll(): " + benchmarkEntityService.findAll().toString());
    }

    private User getTestUser() {
        return userRepository.findByEmail("email@email.com");
    }

    @SuppressWarnings("Duplicates")
    private BenchmarkDTO getBenchmarkClientEntity() {
        BenchmarkDTO benchmarkDTO = new BenchmarkDTO();

        benchmarkDTO.setGpuUUID(TEST_GPU_UUID);
        benchmarkDTO.setAlgorithmName(TEST_ALGORITHM_NAME);
        benchmarkDTO.setMinerName(TEST_MINER_NAME);
        benchmarkDTO.setHashrate(TEST_HASHRATE);

        return benchmarkDTO;
    }

    @SuppressWarnings("Duplicates")
    private BenchmarkDTO getBenchmarkClientEntity_ForUpdate() {
        BenchmarkDTO benchmarkDTO = new BenchmarkDTO();

        benchmarkDTO.setGpuUUID(TEST_GPU_UUID);
        benchmarkDTO.setAlgorithmName(TEST_ALGORITHM_NAME);
        benchmarkDTO.setMinerName(TEST_MINER_NAME);
        benchmarkDTO.setHashrate(TEST_HASHRATE_UPDATED);

        return benchmarkDTO;
    }

}

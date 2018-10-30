package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import com.minerworks.webapp.repository.gpu.GpuVendorEntityRepository;
import com.minerworks.webapp.repository.mining.MinerEntityRepository;
import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import com.minerworks.webapp.repository.gpu.GpuVendorEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/*
    MinerEntity getByMinerName(String minerName);

    Set<MinerEntity> getAllByGpuVendorEntitySetIn(Set<GpuVendorEntity> gpuVendorEntitySet);
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MinerEntityRepositoryTest {

    @Autowired
    private MinerEntityRepository minerEntityRepository;

    @Autowired
    private GpuVendorEntityRepository gpuVendorEntityRepository;

    private static final String TEST_MINER_NAME = "Claymore Dual";

    @Test
    public void getByCoinName_Test() {
        MinerEntity minerEntity = minerEntityRepository.getByMinerName(TEST_MINER_NAME);

        assertNotNull(minerEntity);
        System.out.println(minerEntity);
    }

    @Test
    public void getAllByGpuVendorEntitySetIn_Test() {
        Set<GpuVendorEntity> gpuVendorEntitySet = new HashSet<>();
        gpuVendorEntitySet.add(gpuVendorEntityRepository.getByGpuVendorName("amd"));

        Set<MinerEntity> minerEntitySet = minerEntityRepository.getAllByGpuVendorEntitySetIn(gpuVendorEntitySet);

        assertNotNull(minerEntitySet);
        System.out.println(minerEntitySet);
    }

    @Test
    public void getAllByGpuVendorEntitySetIn_multiple_Test() {
        Set<GpuVendorEntity> gpuVendorEntitySet = new HashSet<>();
        gpuVendorEntitySet.add(gpuVendorEntityRepository.getByGpuVendorName("amd"));
        gpuVendorEntitySet.add(gpuVendorEntityRepository.getByGpuVendorName("nvidia"));

        Set<MinerEntity> minerEntitySet = minerEntityRepository.getAllByGpuVendorEntitySetIn(gpuVendorEntitySet);

        assertNotNull(minerEntitySet);
        System.out.println(minerEntitySet);
    }
}

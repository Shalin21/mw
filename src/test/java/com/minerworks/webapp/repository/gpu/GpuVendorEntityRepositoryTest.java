package com.minerworks.webapp.repository.gpu;

import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpuVendorEntityRepositoryTest {

    @Autowired
    private GpuVendorEntityRepository gpuVendorEntityRepository;

    private static final String GPU_VENDOR_NAME = "nvidia";

    @Test
    public void getByGpuVendorName_Test() {
        GpuVendorEntity gpuVendorEntity = gpuVendorEntityRepository.getByGpuVendorName(GPU_VENDOR_NAME);

        assertNotNull(gpuVendorEntity);
        System.out.println(gpuVendorEntity);
    }

}

package com.minerworks.webapp.repository.gpu;

import com.minerworks.webapp.model.gpu.GpuProfileEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.repository.gpu.GpuProfileEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpuProfileEntityRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GpuProfileEntityRepository gpuProfileEntityRepository;

    @Test
    public void findByUserAndProfileNameAndGpuCardNameTest() {

        GpuProfileEntity gpuProfileEntity = gpuProfileEntityRepository
                .findFirstByUserAndGpuCardNameAndGpuProfileName(
                        getTestUser(),
                        "NVIDIA 1070",
                        "Test Profile");

        assert gpuProfileEntity != null;

        System.out.println(gpuProfileEntity);
    }

    private User getTestUser() {
        return userRepository.findByEmail("email@email.com");
    }

}

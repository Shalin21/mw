package com.minerworks.webapp.service.gpu;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpuProfileEntityServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private GpuProfileEntityService gpuProfileEntityService;
//
//    @Autowired
//    private GpuCardParamsEntityRepository gpuCardParamsEntityRepository;
//
//    @Test
//    public void saveProfile() {
//        gpuProfileEntityService.save(getTestUser(), getTestGpuProfileClientEntity());
//        System.out.println(gpuProfileEntityService.findAll());
//    }
//
//    private User getTestUser() {
//        return userRepository.findByEmail("email@email.com");
//    }
//
//    private GpuProfileClientEntity getTestGpuProfileClientEntity() {
//        GpuProfileClientEntity gpuProfileClientEntity = new GpuProfileClientEntity();
//        gpuProfileClientEntity.setGpuCardName("NVIDIA 1070");
//        gpuProfileClientEntity.setProfileName("Test profile");
//
//        GpuCardParamsEntity gpuCardParamsEntity = new GpuCardParamsEntity();
//        gpuCardParamsEntity.setCoreVoltage(10);
//        gpuCardParamsEntity.setPowerLimit(11);
//        gpuCardParamsEntity.setTempLimit(12);
//        gpuCardParamsEntity.setCoreClock(13);
//        gpuCardParamsEntity.setMemoryClock(14);
//        gpuCardParamsEntity.setFanSpeedPercentage(15);
//        gpuCardParamsEntity.setAutoFanSpeed(false);
//
//        gpuCardParamsEntity = gpuCardParamsEntityRepository.save(gpuCardParamsEntity);
//
//        gpuProfileClientEntity.setGpuCardParamsEntity(gpuCardParamsEntity);
//
//        return gpuProfileClientEntity;
//    }
}

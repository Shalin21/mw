package com.minerworks.webapp.service.gpu;

import com.minerworks.webapp.dto.GpuCardParamsDTO;
import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.gpu.GpuProfileEntity;
import com.minerworks.webapp.repository.FarmEntityRepository;
import com.minerworks.webapp.repository.gpu.GpuCardEntityRepository;
import com.minerworks.webapp.repository.gpu.GpuProfileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GpuCardEntityServiceImpl implements GpuCardEntityService {

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    @Autowired
    private GpuCardEntityRepository gpuCardEntityRepository;

    @Autowired
    private GpuProfileEntityRepository gpuProfileEntityRepository;


    @Override
    public void setGpuCardCurrentParams(GpuCardEntity gpuCardEntity, GpuCardParamsDTO gpuCardParamsDTO) {
        gpuCardEntity.setCurrentCoreVoltage(gpuCardParamsDTO.getCoreVoltage());
        gpuCardEntity.setCurrentPowerLimitPercentage(gpuCardParamsDTO.getPowerLimitPercentage());
        gpuCardEntity.setCurrentTempLimitValue(gpuCardParamsDTO.getTempLimitValue());
        gpuCardEntity.setCurrentCoreClock(gpuCardParamsDTO.getCoreClock());
        gpuCardEntity.setCurrentMemoryClock(gpuCardParamsDTO.getMemoryClock());
        gpuCardEntity.setCurrentFanSpeedPercentage(gpuCardParamsDTO.getFanSpeedPercentage());
        gpuCardEntity.setAutoFanSpeed(gpuCardParamsDTO.isAutoFanSpeed());

        gpuCardEntityRepository.save(gpuCardEntity);
    }

    @Override
    public GpuCardEntity getGpuCardEntity(User user, String farmUUID, String gpuUUID) {
        FarmEntity farmEntity = farmEntityRepository.getByUserAndFarmUUID(user, farmUUID);

        return farmEntity.getGpuCardEntitySet()
                .stream()
                .filter(gpuCardEntity1 -> gpuCardEntity1.getGpuUUID().equals(gpuUUID))
                .findFirst()
                .get();
    }

    @Override
    public void setCurrentGpuOverclockingProfile(User user, GpuCardEntity gpuCardEntity, String gpuProfileName) {
        GpuProfileEntity gpuProfileEntity = gpuProfileEntityRepository.findFirstByUserAndGpuCardNameAndGpuProfileName(user, gpuCardEntity.getGpuName(), gpuProfileName);

        gpuCardEntity.setGpuProfileEntity(gpuProfileEntity);

        gpuCardEntityRepository.save(gpuCardEntity);
    }
}

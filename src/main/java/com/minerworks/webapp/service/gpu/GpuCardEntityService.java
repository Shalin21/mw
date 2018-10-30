package com.minerworks.webapp.service.gpu;

import com.minerworks.webapp.dto.GpuCardParamsDTO;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.dto.GpuCardParamsDTO;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;

public interface GpuCardEntityService {
    void setGpuCardCurrentParams(GpuCardEntity gpuCardEntity, GpuCardParamsDTO gpuCardParamsDTO);

    GpuCardEntity getGpuCardEntity(User user, String farmUUID, String gpuUUID);

    void setCurrentGpuOverclockingProfile(User user, GpuCardEntity gpuCardEntity, String gpuProfileName);
}

package com.minerworks.webapp.service.gpu;

import com.minerworks.webapp.model.gpu.GpuProfileEntity;
import com.minerworks.webapp.model.User;

import java.util.List;

public interface GpuProfileEntityService {

    List<GpuProfileEntity> findAllByUserAndGpuCardName(User user, String gpuCardName);

    GpuProfileEntity save(User user, GpuProfileEntity gpuProfileClientEntity);

    boolean userHasGpuProfile(User user, GpuProfileEntity gpuProfileEntity);

    boolean userHasGpuProfile(User user, String gpuCardName, String gpuProfileName);

    void delete(User user, String gpuCardName, String gpuProfileName);

    void delete(GpuProfileEntity gpuProfileEntity);
}

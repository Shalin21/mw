package com.minerworks.webapp.repository.gpu;

import com.minerworks.webapp.model.gpu.GpuProfileEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GpuProfileEntityRepository extends JpaRepository<GpuProfileEntity, Integer> {
    GpuProfileEntity findFirstByUserAndGpuCardNameAndGpuProfileName(User user, String gpuProfileName, String gpuCardName);

    GpuProfileEntity findGpuProfileEntityByUserAndGpuCardNameAndGpuProfileName(User user, String gpuProfileName, String gpuCardName);

    List<GpuProfileEntity> findAllByUserAndGpuCardName(User user, String gpuCardName);
}

package com.minerworks.webapp.repository.gpu;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpuCardEntityRepository extends JpaRepository<GpuCardEntity, Integer> {
    GpuCardEntity findById(int id);

    GpuCardEntity getByFarmEntityUserAndGpuUUID(User user, String gpuUUID);
}

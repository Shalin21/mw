package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MinerEntityRepository extends JpaRepository<MinerEntity, Long> {
    MinerEntity getByMinerName(String minerName);

    Set<MinerEntity> getAllByGpuVendorEntitySetIn(Set<GpuVendorEntity> gpuVendorEntitySet);
}

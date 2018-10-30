package com.minerworks.webapp.repository.gpu;

import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpuVendorEntityRepository extends JpaRepository<GpuVendorEntity, Long> {
    GpuVendorEntity getByGpuVendorName(String gpuVendorName);
}

package com.minerworks.webapp.repository.benchmark;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.benchmark.BenchmarkResultEntity;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BenchmarkEntityRepository extends JpaRepository<BenchmarkResultEntity, Long> {

    List<BenchmarkResultEntity> findAllByGpuCardEntity_FarmEntity_UserAndGpuCardEntity_FarmEntity_FarmUUID(User user, String farmUUID);

    BenchmarkResultEntity getByGpuCardEntityAndAlgorithmEntityAndMinerEntity(GpuCardEntity gpuCardEntity, AlgorithmEntity algorithmEntity, MinerEntity minerEntity);

    void deleteAllByGpuCardEntity_FarmEntity_UserAndGpuCardEntity_FarmEntity_FarmUUID(User user, String farmUUID);
}

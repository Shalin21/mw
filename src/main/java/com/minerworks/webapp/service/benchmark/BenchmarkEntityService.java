package com.minerworks.webapp.service.benchmark;

import com.minerworks.webapp.dto.BenchmarkDTO;
import com.minerworks.webapp.model.benchmark.BenchmarkResultEntity;
import com.minerworks.webapp.model.User;

import java.util.List;

public interface BenchmarkEntityService {

    BenchmarkResultEntity createFromBenchmarkClientEntity(User user, BenchmarkDTO benchmarkDTO);

    BenchmarkResultEntity getFromBenchmarkClientEntity(User user, BenchmarkDTO benchmarkDTO);

    BenchmarkResultEntity updateFromBenchmarkClientEntity(User user, BenchmarkDTO benchmarkDTO);

    void deleteBenchmarkResults(User user, String farmUUID);

    List<BenchmarkResultEntity> findAllByGpuCardEntity_FarmEntity_UserAndGpuCardEntity_FarmEntity_FarmUUID(User user, String farmUUID);

    List<BenchmarkResultEntity> findAll();

    BenchmarkResultEntity save(BenchmarkResultEntity benchmarkResultEntity);

}

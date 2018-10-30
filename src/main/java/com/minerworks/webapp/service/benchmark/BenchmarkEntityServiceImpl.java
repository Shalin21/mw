package com.minerworks.webapp.service.benchmark;

import com.minerworks.webapp.model.*;
import com.minerworks.webapp.dto.BenchmarkDTO;
import com.minerworks.webapp.model.benchmark.BenchmarkResultEntity;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import com.minerworks.webapp.repository.mining.AlgorithmEntityRepository;
import com.minerworks.webapp.repository.benchmark.BenchmarkEntityRepository;
import com.minerworks.webapp.repository.gpu.GpuCardEntityRepository;
import com.minerworks.webapp.repository.mining.MinerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class BenchmarkEntityServiceImpl implements BenchmarkEntityService {

    @Autowired
    private BenchmarkEntityRepository benchmarkEntityRepository;

    @Autowired
    private GpuCardEntityRepository gpuCardEntityRepository;

    @Autowired
    private AlgorithmEntityRepository algorithmEntityRepository;

    @Autowired
    private MinerEntityRepository minerEntityRepository;

    @Override
    public BenchmarkResultEntity createFromBenchmarkClientEntity(User user, BenchmarkDTO benchmarkDTO) {

        BenchmarkResultEntity benchmarkResultEntity = new BenchmarkResultEntity();

        benchmarkResultEntity.setGpuCardEntity(
                gpuCardEntityRepository.getByFarmEntityUserAndGpuUUID(user, benchmarkDTO.getGpuUUID())
        );

        benchmarkResultEntity.setAlgorithmEntity(
                algorithmEntityRepository.getByAlgorithmName(benchmarkDTO.getAlgorithmName())
        );

        benchmarkResultEntity.setMinerEntity(
                minerEntityRepository.getByMinerName(benchmarkDTO.getMinerName())
        );

        benchmarkResultEntity.setHashrate(BigInteger.valueOf(benchmarkDTO.getHashrate()));

        return benchmarkEntityRepository.save(benchmarkResultEntity);
    }

    @Override
    public BenchmarkResultEntity getFromBenchmarkClientEntity(User user, BenchmarkDTO benchmarkDTO) {

        GpuCardEntity gpuCardEntity = gpuCardEntityRepository.getByFarmEntityUserAndGpuUUID(user, benchmarkDTO.getGpuUUID());

        AlgorithmEntity algorithmEntity = algorithmEntityRepository.getByAlgorithmName(benchmarkDTO.getAlgorithmName());

        MinerEntity minerEntity =  minerEntityRepository.getByMinerName(benchmarkDTO.getMinerName());

        return benchmarkEntityRepository.getByGpuCardEntityAndAlgorithmEntityAndMinerEntity(gpuCardEntity, algorithmEntity, minerEntity);
    }

    @Override
    public BenchmarkResultEntity updateFromBenchmarkClientEntity(User user, BenchmarkDTO benchmarkDTO) {
        BenchmarkResultEntity benchmarkResultEntity = getFromBenchmarkClientEntity(user, benchmarkDTO);

        benchmarkResultEntity.setHashrate(BigInteger.valueOf(benchmarkDTO.getHashrate()));

        return benchmarkEntityRepository.save(benchmarkResultEntity);
    }

    @Override
    public void deleteBenchmarkResults(User user, String farmUUID) {
        benchmarkEntityRepository.deleteAllByGpuCardEntity_FarmEntity_UserAndGpuCardEntity_FarmEntity_FarmUUID(user, farmUUID);
    }

    @Override
    public List<BenchmarkResultEntity> findAllByGpuCardEntity_FarmEntity_UserAndGpuCardEntity_FarmEntity_FarmUUID(User user, String farmUUID) {
        return benchmarkEntityRepository.findAllByGpuCardEntity_FarmEntity_UserAndGpuCardEntity_FarmEntity_FarmUUID(user, farmUUID);
    }

    @Override
    public List<BenchmarkResultEntity> findAll() {
        return benchmarkEntityRepository.findAll();
    }

    @Override
    public BenchmarkResultEntity save(BenchmarkResultEntity benchmarkResultEntity) {
        return benchmarkEntityRepository.save(benchmarkResultEntity);
    }

}

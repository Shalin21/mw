package com.minerworks.webapp.model.benchmark;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.MinerEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "benchmark_result")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "benchmarkResult")
@Data
public class BenchmarkResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "benchmark_result_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "gpu_card_id")
    private GpuCardEntity gpuCardEntity;

    @OneToOne
    @JoinColumn(name = "algorithm_id")
    private AlgorithmEntity algorithmEntity;

    @OneToOne
    @JoinColumn(name = "miner_id")
    private MinerEntity minerEntity;

    @Column(name = "hashrate")
    private BigInteger hashrate;

}

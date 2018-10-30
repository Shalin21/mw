package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.minerworks.webapp.model.gpu.GpuVendorEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "miner")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "miner")
@Data
public class MinerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "miner_id")
    private int id;

    @Column(name = "miner_name")
    private String minerName;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "miner_gpu_vendor",
            joinColumns = {@JoinColumn(name = "miner_id")},
            inverseJoinColumns = {@JoinColumn(name = "gpu_vendor_id")}
    )
    private Set<GpuVendorEntity> gpuVendorEntitySet;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "miner_algorithm",
            joinColumns = {@JoinColumn(name = "miner_id")},
            inverseJoinColumns = {@JoinColumn(name = "algorithm_id")}
    )
    private Set<AlgorithmEntity> algorithmEntitySet;

}

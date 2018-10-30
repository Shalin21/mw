package com.minerworks.webapp.model;

import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import com.minerworks.webapp.model.mining.PoolCoinEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.minerworks.webapp.model.gpu.GpuCardEntity;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "farms")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "farm")
@Data
public class FarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "farm_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "farm_uuid")
    private String farmUUID;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "farmEntity")
    private Set<GpuCardEntity> gpuCardEntitySet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "farmEntity")
    private Set<MiningCardEntity> miningCardEntitySet = new HashSet<>();

    @OneToOne(mappedBy = "farmEntity")
    private FarmSettingsEntity farmSettingsEntity;

    public void addGpuCardEntity(GpuCardEntity gpuCardEntity) {
        gpuCardEntitySet.add(gpuCardEntity);
        gpuCardEntity.setFarmEntity(this);
    }

    public void removeGpuCardEntity(GpuCardEntity gpuCardEntity) {
        gpuCardEntitySet.remove(gpuCardEntity);
        gpuCardEntity.setFarmEntity(null);
    }

    public void addMiningCardEntity(MiningCardEntity miningCardEntity) {
        miningCardEntitySet.add(miningCardEntity);
        miningCardEntity.setFarmEntity(this);
    }

    public void removeMiningCardEntity(MiningCardEntity miningCardEntity) {
        miningCardEntitySet.remove(miningCardEntity);
        miningCardEntity.setFarmEntity(null);
    }

}

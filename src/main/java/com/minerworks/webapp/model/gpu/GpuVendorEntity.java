package com.minerworks.webapp.model.gpu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.minerworks.webapp.model.mining.MinerEntity;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "gpu_vendor")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "gpuVendor")
@Data
public class GpuVendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gpu_vendor_id")
    private int id;

    @Column(name = "gpu_vendor_name")
    private String gpuVendorName;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "gpuVendorEntitySet")
    private Set<MinerEntity> minerEntitySet;

}

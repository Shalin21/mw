package com.minerworks.webapp.model.gpu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.minerworks.webapp.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gpu_profile")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "gpuProfile")
@Data
public class GpuProfileEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gpu_profile_id")
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "gpu_card_name")
    private String gpuCardName;

    @Column(name = "gpu_profile_name")
    private String gpuProfileName;

    @Column(name = "core_voltage")
    private int coreVoltage;

    @Column(name = "power_limit_percentage")
    private int powerLimitPercentage;

    @Column(name = "temp_limit_value")
    private int tempLimitValue;

    @Column(name = "core_clock")
    private int coreClock;

    @Column(name = "memory_clock")
    private int memoryClock;

    @Column(name = "fan_speed_percentage")
    private int fanSpeedPercentage;

    @Column(name = "auto_fan_speed")
    private boolean autoFanSpeed;

}

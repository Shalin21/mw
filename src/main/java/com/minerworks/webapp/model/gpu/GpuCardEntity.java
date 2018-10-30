package com.minerworks.webapp.model.gpu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.minerworks.webapp.model.FarmEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gpu_card")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "gpuCard")
@Data
public class GpuCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gpu_card_id")
    private long id;

    @Column(name = "gpu_card_uuid")
    private String gpuUUID;

    @Column(name = "gpu_card_name")
    private String gpuName;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "gpu_vendor_id")
    private GpuVendorEntity gpuVendorEntity;

    @ManyToOne
    @JoinColumn(name = "gpu_profile_id")
    private GpuProfileEntity gpuProfileEntity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private FarmEntity farmEntity;

    // CORE VOLTAGE
    @Column(name = "current_core_voltage")
    private int currentCoreVoltage;

    @Column(name = "core_voltage_range_min")
    private int coreVoltageRangeMin;

    @Column(name = "core_voltage_range_max")
    private int coreVoltageRangeMax;

    // POWER LIMIT
    @Column(name = "current_power_limit_percentage")
    private int currentPowerLimitPercentage;

    @Column(name = "power_limit_range_min_percentage")
    private int powerLimitRangeMinPercentage;

    @Column(name = "power_limit_range_max_percentage")
    private int powerLimitRangeMaxPercentage;

    // TEMP LIMIT
    @Column(name = "current_temp_limit_value")
    private int currentTempLimitValue;

    @Column(name = "temp_limit_range_min")
    private int tempLimitRangeMin;

    @Column(name = "temp_limit_range_max")
    private int tempLimitRangeMax;

    // CORE CLOCK
    @Column(name = "current_core_clock")
    private int currentCoreClock;

    @Column(name = "core_clock_range_min")
    private int coreClockRangeMin;

    @Column(name = "core_clock_range_max")
    private int coreClockRangeMax;

    // MEMORY CLOCK
    @Column(name = "current_memory_clock")
    private int currentMemoryClock;

    @Column(name = "memory_clock_range_min")
    private int memoryClockRangeMin;

    @Column(name = "memory_clock_range_max")
    private int memoryClockRangeMax;

    // FAN SPEED %
    @Column(name = "current_fan_speed_percentage")
    private int currentFanSpeedPercentage;

    @Column(name = "auto_fan_speed")
    private boolean autoFanSpeed;

    @Column(name = "fan_speed_range_min_percentage")
    private int fanSpeedRangeMinPercentage;

    @Column(name = "fan_speed_range_max_percentage")
    private int fanSpeedRangeMaxPercentage;

}

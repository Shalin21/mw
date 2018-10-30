package com.minerworks.webapp.dto;

import lombok.Data;

@Data
public class GpuCardParamsDTO {

    private int coreVoltage;

    private int powerLimitPercentage;

    private int tempLimitValue;

    private int coreClock;

    private int memoryClock;

    private int fanSpeedPercentage;

    private boolean autoFanSpeed;

}

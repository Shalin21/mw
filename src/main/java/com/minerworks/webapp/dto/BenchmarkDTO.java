package com.minerworks.webapp.dto;

import lombok.Data;

@Data
public class BenchmarkDTO {

    private String gpuUUID;

    private String algorithmName;

    private String minerName;

    private Long hashrate;

}

package com.minerworks.webapp.model.monitoring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "monitoring_type")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "monitoringType")
@Data
public class MonitoringTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "monitoring_type_id")
    private int id;

    @Column(name = "monitoring_type_name")
    private String monitoringTypeName;

}

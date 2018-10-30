package com.minerworks.webapp.model.monitoring;

import com.minerworks.webapp.model.FarmEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "monitoring_value")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "monitoringValue")
@Data
public class MonitoringValueEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "monitoring_value_id")
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private FarmEntity farmEntity;

    @ManyToOne
    @JoinColumn(name = "monitoring_type_id")
    private MonitoringTypeEntity monitoringTypeEntity;

    @Column(name = "monitoring_value")
    private int monitoringValue;

    @Column(name = "monitoring_date")
    private Date monitoringDate;

}

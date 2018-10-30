package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pool_coin_port")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "port")
@Data
public class PortEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "port_id")
    private long id;

    @Column(name = "port_name")
    private String portName;

    @Column(name = "port_address")
    private Integer portAddress;

}

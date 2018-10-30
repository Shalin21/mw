package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pool_coin_server")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "server")
@Data
public class ServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "server_id")
    private long id;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "server_address")
    private String serverAddress;

}

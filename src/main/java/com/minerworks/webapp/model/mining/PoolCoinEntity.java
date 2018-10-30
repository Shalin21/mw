package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pool_coin")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "poolCoin")
@Data
public class PoolCoinEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pool_coin_id")
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pool_id")
    private PoolEntity poolEntity;

    @OneToOne
    @JoinColumn(name = "coin_id")
    private CoinEntity coinEntity;

    @OneToMany
    @JoinColumn(name = "pool_coin_id")
    private Set<ServerEntity> servers = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "pool_coin_id")
    private Set<PortEntity> ports = new HashSet<>();

}

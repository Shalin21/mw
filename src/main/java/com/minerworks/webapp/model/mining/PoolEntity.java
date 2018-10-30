package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pool")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "pool")
@Data
public class PoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pool_id")
    private int id;

    @Column(name = "pool_name")
    private String poolName;

    @Column(name = "requires_email")
    private boolean requiresEmail;

    @Column(name = "requires_pool_username")
    private boolean requiresPoolUsername;

    @Column(name = "requires_password")
    private boolean requiresPassword;

    @Column(name = "requires_wallet_address")
    private boolean requiresWalletAddress;

    @OneToMany(mappedBy = "poolEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PoolCoinEntity> poolCoinEntitySet = new HashSet<>();

    public void addPoolCoinEntity(PoolCoinEntity poolCoinEntity) {
        poolCoinEntitySet.add(poolCoinEntity);
        poolCoinEntity.setPoolEntity(this);
    }

    public void removePoolCoinEntity(PoolCoinEntity poolCoinEntity) {
        poolCoinEntitySet.remove(poolCoinEntity);
        poolCoinEntity.setPoolEntity(null);
    }

}

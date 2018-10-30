package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.minerworks.webapp.model.FarmEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "mining_card")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "miningCard")
@Data
public class MiningCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mining_card_id")
    private long id;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private FarmEntity farmEntity;

    // UTILS

    @Column(name = "active")
    private Boolean active;

    @Column(name = "mining_now")
    private Boolean miningNow;

    @Column(name = "dual_mining")
    private Boolean dualMining;

    // COIN ONE

    @Column(name = "pool_username_coin_one")
    private String poolUsernameCoinOne;

    @Column(name = "worker_password_coin_one")
    private String workerPasswordCoinOne;

    @Column(name = "worker_name_coin_one")
    private String workerNameCoinOne;

    @Column(name = "worker_email_coin_one")
    private String workerEmailCoinOne;

    @Column(name = "coin_one")
    private String coinOne;

    @Column(name = "pool_coin_one")
    private String poolCoinOne;

    @Column(name = "algorithm_coin_one")
    private String algorithmCoinOne;

    @Column(name = "server_coin_one")
    private String serverCoinOne;

    @Column(name = "server_address_coin_one")
    private String serverAddressCoinOne;

    @Column(name = "port_coin_one")
    private String portCoinOne;

    @Column(name = "port_address_coin_one")
    private String portAddressCoinOne;

    @Column(name = "payment_id_coin_one")
    private String paymentIdCoinOne;

    @Column(name = "wallet_address_coin_one")
    private String walletAddressCoinOne;

    // COIN TWO

    @Column(name = "pool_username_coin_two")
    private String poolUsernameCoinTwo;

    @Column(name = "worker_password_coin_two")
    private String workerPasswordCoinTwo;

    @Column(name = "worker_name_coin_two")
    private String workerNameCoinTwo;

    @Column(name = "worker_email_coin_two")
    private String workerEmailCoinTwo;

    @Column(name = "coin_two")
    private String coinTwo;

    @Column(name = "pool_coin_two")
    private String poolCoinTwo;

    @Column(name = "algorithm_coin_two")
    private String algorithmCoinTwo;

    @Column(name = "server_coin_two")
    private String serverCoinTwo;

    @Column(name = "server_address_coin_two")
    private String serverAddressCoinTwo;

    @Column(name = "port_coin_two")
    private String portCoinTwo;

    @Column(name = "port_address_coin_two")
    private String portAddressCoinTwo;

    @Column(name = "payment_id_coin_two")
    private String paymentIdCoinTwo;

    @Column(name = "wallet_address_coin_two")
    private String walletAddressCoinTwo;

}

package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "whattomine_coin")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "whattomineCoin")
@Data
public class WhatToMineCoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "whattomine_coin_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "coin_id")
    private CoinEntity coinEntity;

}

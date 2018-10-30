package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "secondary_coin_algorithm")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "secondaryCoinAlgorithm")
@Data
public class SecondaryCoinAlgorithmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "secondary_algorithm_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "algorithm_id")
    private AlgorithmEntity algorithmEntity;

}

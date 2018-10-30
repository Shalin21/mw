package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "algorithm")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "algorithm")
@Data
public class AlgorithmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "algorithm_id")
    private int id;

    @Column(name = "algorithm_name")
    private String algorithmName;

    @OneToMany(mappedBy = "algorithmEntity")
    private Set<CoinEntity> coinEntitySet;

    @ManyToMany(mappedBy = "algorithmEntitySet")
    private Set<MinerEntity> minerEntitySet;

}

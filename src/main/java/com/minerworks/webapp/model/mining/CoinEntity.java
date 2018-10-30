package com.minerworks.webapp.model.mining;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coin")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(value = "coin")
@Data
public class CoinEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coin_id")
    private long id;

    @Column(name = "coin_name")
    private String coinName;

    @JsonIgnore
    @ManyToOne
    @JoinTable(
            name = "coin_algorithm",
            joinColumns = {@JoinColumn(name = "coin_id")},
            inverseJoinColumns = {@JoinColumn(name = "algorithm_id")}
    )
    private AlgorithmEntity algorithmEntity;

}

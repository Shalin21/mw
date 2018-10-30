package com.minerworks.webapp.repository.mining;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecondaryCoinAlgorithmEntityRepositoryTest {

    @Autowired
    private SecondaryCoinAlgorithmsEntityRepository secondaryCoinAlgorithmsEntityRepository;

    @Test
    public void printSecondaryCoinAlgorithms() {
        secondaryCoinAlgorithmsEntityRepository.findAll().forEach(secondaryCoinAlgorithmsEntity -> System.out.println(secondaryCoinAlgorithmsEntity.getAlgorithmEntity().getAlgorithmName()));
    }

}

package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.repository.mining.WhatToMineEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WhatToMineEntityRepositoryTest {

    @Autowired
    private WhatToMineEntityRepository whatToMineEntityRepository;

    @Test
    public void printWhatToMineCoins() {
        whatToMineEntityRepository.findAll().forEach(whatToMineCoinsEntity -> System.out.println(whatToMineCoinsEntity.getCoinEntity().getCoinName()));
    }

}

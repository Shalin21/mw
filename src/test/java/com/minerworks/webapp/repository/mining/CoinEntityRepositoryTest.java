package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import com.minerworks.webapp.repository.mining.AlgorithmEntityRepository;
import com.minerworks.webapp.repository.mining.CoinEntityRepository;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/*
    CoinEntity getByCoinName(String coinName);

    Set<CoinEntity> findAllByAlgorithmEntityIn(Set<AlgorithmEntity> algorithmEntitySet);
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinEntityRepositoryTest {

    @Autowired
    private CoinEntityRepository coinEntityRepository;

    @Autowired
    private AlgorithmEntityRepository algorithmEntityRepository;

    private static final String TEST_COIN_NAME = "Ethereum";

    @Test
    public void getByCoinName_Test() {
        CoinEntity coinEntity = coinEntityRepository.getByCoinName(TEST_COIN_NAME);

        assertNotNull(coinEntity);
        System.out.println(coinEntity);
    }

    @Test
    public void findAllByAlgorithmEntity_Test() {
        Set<AlgorithmEntity> algorithmEntitySet = new HashSet<>();
        algorithmEntitySet.add(algorithmEntityRepository.getByAlgorithmName("dagger_hashimoto"));

        Set<CoinEntity> coinEntitySet = coinEntityRepository.findAllByAlgorithmEntityIn(algorithmEntitySet);

        assertNotNull(coinEntitySet);
        System.out.println(coinEntitySet);
    }

    @Test
    public void findAllByAlgorithmEntity_multiple_Test() {
        Set<AlgorithmEntity> algorithmEntitySet = new HashSet<>();
        algorithmEntitySet.add(algorithmEntityRepository.getByAlgorithmName("dagger_hashimoto"));
        algorithmEntitySet.add(algorithmEntityRepository.getByAlgorithmName("equihash"));

        Set<CoinEntity> coinEntitySet = coinEntityRepository.findAllByAlgorithmEntityIn(algorithmEntitySet);

        assertNotNull(coinEntitySet);
        System.out.println(coinEntitySet);
    }

}

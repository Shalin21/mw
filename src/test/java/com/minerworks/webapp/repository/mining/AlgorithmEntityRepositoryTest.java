package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import com.minerworks.webapp.model.mining.AlgorithmEntity;
import com.minerworks.webapp.model.mining.CoinEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/*
    AlgorithmEntity getByAlgorithmName(String algorithmName);

    AlgorithmEntity getByCoinEntitySetIsIn(CoinEntity coinEntity);
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlgorithmEntityRepositoryTest {

    @Autowired
    private AlgorithmEntityRepository algorithmEntityRepository;

    @Autowired
    private CoinEntityRepository coinEntityRepository;

    private static final String TEST_ALGORITHM_NAME = "dagger_hashimoto";

    private static final String TEST_COIN_NAME = "Zcash";

    @Test
    public void getByPoolName_Test() {
        AlgorithmEntity algorithmEntity = algorithmEntityRepository.getByAlgorithmName(TEST_ALGORITHM_NAME);

        assertNotNull(algorithmEntity);
        System.out.println(algorithmEntity);
    }

    @Test
    public void getByCoinEntitySetIsIn_Test() {
        CoinEntity coinEntity = coinEntityRepository.getByCoinName(TEST_COIN_NAME);

        AlgorithmEntity algorithmEntity = algorithmEntityRepository.getByCoinEntitySetIsIn(coinEntity);

        assertNotNull(algorithmEntity);
        System.out.println(algorithmEntity);
    }

}

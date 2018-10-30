package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.PoolEntity;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.mining.PoolEntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/*
    Set<String> getPrimaryCoinSet(User user, String farmUUID);

    Set<String> getSecondaryCoinSet(User user, String farmUUID);

    Set<String> getPoolNamesSetForCoin(String coinName);

    PoolEntity getPoolEntity(String poolName);
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PoolEntityServiceTest {

    @Autowired
    private PoolEntityService poolEntityService;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_FARM_CODE = "test-farm-code";

    private static final String TEST_COIN_NAME = "Ethereum";

    private static final String TEST_POOL_NAME = "Nanopool";

    @Test
    public void getPrimaryCoinSet_Test() {
        Set<String> primaryCoinSet = poolEntityService.getPrimaryCoinSet(getTestUser(), TEST_FARM_CODE);

        assertFalse(primaryCoinSet.isEmpty());
        System.out.println(primaryCoinSet);
    }

    @Test
    public void getSecondaryCoinSet_Test() {
        Set<String> secondaryCoinSet = poolEntityService.getSecondaryCoinSet(getTestUser(), TEST_FARM_CODE);

        assertFalse(secondaryCoinSet.isEmpty());
        System.out.println(secondaryCoinSet);
    }

    @Test
    public void getPoolNamesSetForCoin_Test() {
        Set<String> poolSet =  poolEntityService.getPoolNamesSetForCoin(TEST_COIN_NAME);

        assertFalse(poolSet.isEmpty());
        System.out.println(poolSet);
    }

    @Test
    public void getPoolEntity_Test() {
        PoolEntity poolEntity = poolEntityService.getPoolEntity(TEST_POOL_NAME);

        assertNotNull(poolEntity);
        System.out.println(poolEntity);
    }

    private User getTestUser() {
        return userRepository.findByEmail("email@email.com");
    }

}

package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.mining.PoolEntity;
import com.minerworks.webapp.repository.mining.PoolEntityRepository;
import com.minerworks.webapp.model.mining.PoolEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/*
    PoolEntity getByPoolName(String poolName);
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PoolEntityRepositoryTest {

    @Autowired
    private PoolEntityRepository poolEntityRepository;

    private static final String TEST_POOL_NAME = "Nanopool";

    @Test
    public void getByPoolName_Test() {
        PoolEntity poolEntity = poolEntityRepository.getByPoolName(TEST_POOL_NAME);

        assertNotNull(poolEntity);
        System.out.println(poolEntity);
    }

}

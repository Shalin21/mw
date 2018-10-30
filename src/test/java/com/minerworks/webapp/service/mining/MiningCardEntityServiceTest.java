package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import com.minerworks.webapp.repository.FarmEntityRepository;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.benchmark.BenchmarkResultEntityServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiningCardEntityServiceTest {

    private Logger logger = LoggerFactory.getLogger(BenchmarkResultEntityServiceTest.class);

    @Autowired
    private MiningCardEntityService miningCardEntityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    private static final String TEST_FARM_CODE = "test-farm-code";

    @Test
    public void retainMiningCardsFromFarm_Test() {
        logger.debug(farmEntityRepository.getByUserAndFarmUUID(getTestUser(), TEST_FARM_CODE).getMiningCardEntitySet().toString());
    }

    @Test
    public void create_Test() {
        miningCardEntityService.create(getTestUser(), TEST_FARM_CODE, getMiningCardEntity());
        logger.debug(farmEntityRepository.getByUserAndFarmUUID(getTestUser(), TEST_FARM_CODE).getMiningCardEntitySet().toString());
    }

    @Test
    public void update_Test() {
        miningCardEntityService.update(getTestUser(), TEST_FARM_CODE, getMiningCardUpdated());
        logger.debug(farmEntityRepository.getByUserAndFarmUUID(getTestUser(), TEST_FARM_CODE).getMiningCardEntitySet().toString());
    }

    @Test
    public void delete_Test() {
        miningCardEntityService.delete(getTestUser(), TEST_FARM_CODE, getMiningCardEntity());
        logger.debug(farmEntityRepository.getByUserAndFarmUUID(getTestUser(), TEST_FARM_CODE).getMiningCardEntitySet().toString());
    }

    private User getTestUser() {
        return userRepository.findByEmail("email@email.com");
    }


    private Boolean active_TEST = Boolean.FALSE;
    private Boolean miningNow_TEST = Boolean.FALSE;
    private Boolean dualMining_TEST = Boolean.FALSE;
    private String poolUsernameCoinOne_TEST = null;
    private String workerPasswordCoinOne_TEST = null;
    private String workerNameCoinOne_TEST = "test-worker-name";
    private String workerEmailCoinOne_TEST = "test@test.com";
    private String coinOne_TEST = "Ethereum";
    private String poolCoinOne_TEST = "Nanopool";
    private String algorithmCoinOne_TEST = "dagger_hashimoto";
    private String serverCoinOne_TEST = "Europe";
    private String serverAddressCoinOne_TEST = "test.com";
    private String portCoinOne_TEST = "Stratum";
    private String portAddressCoinOne_TEST = "1111";
    private String paymentIdCoinOne_TEST = null;
    private String walletAddressCoinOne_TEST = "test-wallet-address";

    private String poolCoinOne_TEST_UPDATED = "DwarfPool";

    @SuppressWarnings("Duplicates")
    private MiningCardEntity getMiningCardEntity() {
        MiningCardEntity miningCardEntity = new MiningCardEntity();

//        miningCardEntity.setFarmEntity(farmEntityRepository.getByUserAndFarmUUID(getTestUser(), TEST_FARM_CODE));

        miningCardEntity.setActive(active_TEST);
        miningCardEntity.setMiningNow(miningNow_TEST);
        miningCardEntity.setDualMining(dualMining_TEST);
        miningCardEntity.setPoolUsernameCoinOne(poolUsernameCoinOne_TEST);
        miningCardEntity.setWorkerPasswordCoinOne(workerPasswordCoinOne_TEST);
        miningCardEntity.setWorkerNameCoinOne(workerNameCoinOne_TEST);
        miningCardEntity.setWorkerEmailCoinOne(workerEmailCoinOne_TEST);
        miningCardEntity.setCoinOne(coinOne_TEST);
        miningCardEntity.setPoolCoinOne(poolCoinOne_TEST);
        miningCardEntity.setAlgorithmCoinOne(algorithmCoinOne_TEST);
        miningCardEntity.setServerCoinOne(serverCoinOne_TEST);
        miningCardEntity.setServerAddressCoinOne(serverAddressCoinOne_TEST);
        miningCardEntity.setPortCoinOne(portCoinOne_TEST);
        miningCardEntity.setPortAddressCoinOne(portAddressCoinOne_TEST);
        miningCardEntity.setPaymentIdCoinOne(paymentIdCoinOne_TEST);
        miningCardEntity.setWalletAddressCoinOne(walletAddressCoinOne_TEST);

        return miningCardEntity;
    }

    private MiningCardEntity getMiningCardUpdated() {
        MiningCardEntity miningCardEntity = getMiningCardEntity();
        miningCardEntity.setPoolCoinOne(poolCoinOne_TEST_UPDATED);
        return miningCardEntity;
    }

}

package com.minerworks.webapp.utils.test;

import com.minerworks.webapp.dto.BenchmarkDTO;
import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.FarmSettingsEntity;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.MiningCardEntity;

public final class TestEntity {

    private static Boolean active_TEST = Boolean.FALSE;
    private static Boolean miningNow_TEST = Boolean.FALSE;
    private static Boolean dualMining_TEST = Boolean.FALSE;
    private static String poolUsernameCoinOne_TEST = null;
    private static String workerPasswordCoinOne_TEST = null;
    private static String workerNameCoinOne_TEST = "test-worker-name";
    private static String workerEmailCoinOne_TEST = "test@test.com";
    private static String coinOne_TEST = "Ethereum";
    private static String poolCoinOne_TEST = "Nanopool";
    private static String algorithmCoinOne_TEST = "dagger_hashimoto";
    private static String serverCoinOne_TEST = "Europe";
    private static String serverAddressCoinOne_TEST = "test.com";
    private static String portCoinOne_TEST = "Stratum";
    private static String portAddressCoinOne_TEST = "1111";
    private static String paymentIdCoinOne_TEST = null;
    private static String walletAddressCoinOne_TEST = "test-wallet-address";

    public static FarmEntity getFarmEntity(User testUser) {
        FarmEntity farmEntity = new FarmEntity();
        farmEntity.setFarmUUID("test-farm-code");
        farmEntity.setName("test-farm-name");
        farmEntity.setUser(testUser);
        return farmEntity;
    }

    public static BenchmarkDTO getBenchmarkClientEntity() {
        BenchmarkDTO benchmarkDTO = new BenchmarkDTO();
        benchmarkDTO.setGpuUUID("test-gpu-uuid");
        benchmarkDTO.setAlgorithmName("dagger_hashimoto");
        benchmarkDTO.setMinerName("claymore_dual");
        benchmarkDTO.setHashrate(11111L);
        return benchmarkDTO;
    }

    public static MiningCardEntity getMiningCardEntity() {
        MiningCardEntity miningCardEntity = new MiningCardEntity();

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

    public static FarmSettingsEntity getFarmSettingsEntity() {

        FarmSettingsEntity farmSettingsEntity = new FarmSettingsEntity();
        farmSettingsEntity.setTheme("dark");
        farmSettingsEntity.setLanguage("en");
        farmSettingsEntity.setRunWithWindows(false);
        farmSettingsEntity.setMinimizeToTray(false);
        farmSettingsEntity.setHideMiningWindow(false);
        farmSettingsEntity.setAutostartMining(false);
        farmSettingsEntity.setIdleMining(false);
        farmSettingsEntity.setIdleMiningSeconds(0);
        farmSettingsEntity.setCurrencyForDailyRevenue("USD");
        farmSettingsEntity.setProfitabilityCheckTimeMinutes(2);
        farmSettingsEntity.setChartsRefreshTimeSeconds(2);

        return farmSettingsEntity;
    }

}

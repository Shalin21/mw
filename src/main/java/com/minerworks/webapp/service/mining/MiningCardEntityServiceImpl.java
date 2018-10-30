package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import com.minerworks.webapp.repository.FarmEntityRepository;
import com.minerworks.webapp.repository.mining.MiningCardEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MiningCardEntityServiceImpl implements MiningCardEntityService {

    @Autowired
    private MiningCardEntityRepository miningCardEntityRepository;

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    @Override
    public MiningCardEntity create(User user, String farmUUID, MiningCardEntity miningCardEntity) {
        if (miningCardEntity.getFarmEntity() == null) {
            miningCardEntity.setFarmEntity(farmEntityRepository.getByUserAndFarmUUID(user, farmUUID));
        }

        return miningCardEntityRepository.save(miningCardEntity);
    }

    @Override
    public MiningCardEntity update(User user, String farmUUID, MiningCardEntity miningCardEntity) {

        // find by all equals instead of id, farm
        MiningCardEntity miningCardEntityFetched = miningCardEntityRepository.getByFarmEntity_UserAndFarmEntity_FarmUUID_AndCoinOneAndCoinTwo(
                user,
                farmUUID,
                miningCardEntity.getCoinOne(),
                miningCardEntity.getCoinTwo()
        );

        miningCardEntityFetched.setActive(miningCardEntity.getActive());
        miningCardEntityFetched.setMiningNow(miningCardEntity.getMiningNow());
        miningCardEntityFetched.setDualMining(miningCardEntity.getDualMining());
        miningCardEntityFetched.setPoolUsernameCoinOne(miningCardEntity.getPoolUsernameCoinOne());
        miningCardEntityFetched.setWorkerPasswordCoinOne(miningCardEntity.getWorkerPasswordCoinOne());
        miningCardEntityFetched.setWorkerNameCoinOne(miningCardEntity.getWorkerNameCoinOne());
        miningCardEntityFetched.setWorkerEmailCoinOne(miningCardEntity.getWorkerEmailCoinOne());
        miningCardEntityFetched.setCoinOne(miningCardEntity.getCoinOne());
        miningCardEntityFetched.setPoolCoinOne(miningCardEntity.getPoolCoinOne());
        miningCardEntityFetched.setAlgorithmCoinOne(miningCardEntity.getAlgorithmCoinOne());
        miningCardEntityFetched.setServerCoinOne(miningCardEntity.getServerCoinOne());
        miningCardEntityFetched.setServerAddressCoinOne(miningCardEntity.getServerAddressCoinOne());
        miningCardEntityFetched.setPortCoinOne(miningCardEntity.getPortCoinOne());
        miningCardEntityFetched.setPortAddressCoinOne(miningCardEntity.getPortAddressCoinOne());
        miningCardEntityFetched.setPaymentIdCoinOne(miningCardEntity.getPaymentIdCoinOne());
        miningCardEntityFetched.setWalletAddressCoinOne(miningCardEntity.getWalletAddressCoinOne());
        miningCardEntityFetched.setPoolUsernameCoinTwo(miningCardEntity.getPoolUsernameCoinTwo());
        miningCardEntityFetched.setWorkerPasswordCoinTwo(miningCardEntity.getWorkerPasswordCoinTwo());
        miningCardEntityFetched.setWorkerNameCoinTwo(miningCardEntity.getWorkerNameCoinTwo());
        miningCardEntityFetched.setWorkerEmailCoinTwo(miningCardEntity.getWorkerEmailCoinTwo());
        miningCardEntityFetched.setCoinTwo(miningCardEntity.getCoinTwo());
        miningCardEntityFetched.setPoolCoinTwo(miningCardEntity.getPoolCoinTwo());
        miningCardEntityFetched.setAlgorithmCoinTwo(miningCardEntity.getAlgorithmCoinTwo());
        miningCardEntityFetched.setServerCoinTwo(miningCardEntity.getServerCoinTwo());
        miningCardEntityFetched.setServerAddressCoinTwo(miningCardEntity.getServerAddressCoinTwo());
        miningCardEntityFetched.setPortCoinTwo(miningCardEntity.getPortCoinTwo());
        miningCardEntityFetched.setPortAddressCoinTwo(miningCardEntity.getPortAddressCoinTwo());
        miningCardEntityFetched.setPaymentIdCoinTwo(miningCardEntity.getPaymentIdCoinTwo());
        miningCardEntityFetched.setWalletAddressCoinTwo(miningCardEntity.getWalletAddressCoinTwo());

        return miningCardEntityRepository.save(miningCardEntityFetched);
    }

    @Override
    public void delete(User user, String farmUUID, MiningCardEntity miningCardEntity) {
        MiningCardEntity miningCardEntityFetched = miningCardEntityRepository.getByFarmEntity_UserAndFarmEntity_FarmUUID_AndCoinOneAndCoinTwo(
                user,
                farmUUID,
                miningCardEntity.getCoinOne(),
                miningCardEntity.getCoinTwo()
        );

        miningCardEntityRepository.delete(miningCardEntityFetched);
    }

    @Override
    public boolean isMiningCardExist(User user, String farmUUID, MiningCardEntity miningCardEntity) {
        MiningCardEntity currentMiningCardEntity = miningCardEntityRepository.getByFarmEntity_UserAndFarmEntity_FarmUUID_AndCoinOneAndCoinTwo(
                user,
                farmUUID,
                miningCardEntity.getCoinOne(),
                miningCardEntity.getCoinTwo()
        );

        return currentMiningCardEntity != null;
    }

}

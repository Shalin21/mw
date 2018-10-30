package com.minerworks.webapp.repository.mining;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MiningCardEntityRepository extends JpaRepository<MiningCardEntity, Long> {
    List<MiningCardEntity> findAllByFarmEntity_UserAndFarmEntity_FarmUUID(User user, String farmUUID);

    MiningCardEntity getByFarmEntity_UserAndFarmEntity_FarmUUID_AndCoinOneAndCoinTwo(User user, String farmUUID, String coinOne, String coinTwo);

    MiningCardEntity getByFarmEntity_UserAndFarmEntity_FarmUUID_AndCoinOneAndPoolCoinOneAndCoinTwoAndPoolCoinTwo(User user, String farmUUID, String coinOne, String poolCoinOne, String coinTwo, String poolCoinTwo);
}

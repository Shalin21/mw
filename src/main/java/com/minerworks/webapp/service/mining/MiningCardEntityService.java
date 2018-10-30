package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.MiningCardEntity;
import com.minerworks.webapp.model.mining.MiningCardEntity;

public interface MiningCardEntityService {
    MiningCardEntity create(User user, String farmUUID, MiningCardEntity miningCardEntity);

    MiningCardEntity update(User user, String farmUUID, MiningCardEntity miningCardEntity);

    void delete(User user, String farmUUID, MiningCardEntity miningCardEntity);

    boolean isMiningCardExist(User user, String farmUUID, MiningCardEntity miningCardEntity);
}

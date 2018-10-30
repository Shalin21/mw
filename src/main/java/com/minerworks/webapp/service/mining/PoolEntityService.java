package com.minerworks.webapp.service.mining;

import com.minerworks.webapp.model.User;
import com.minerworks.webapp.model.mining.PoolEntity;

import java.util.Set;

public interface PoolEntityService {
    Set<String> getPrimaryCoinSet(User user, String farmUUID);

    Set<String> getSecondaryCoinSet(User user, String farmUUID);

    Set<String> getPoolNamesSetForCoin(String coinName);

    PoolEntity getPoolEntity(String poolName);
}


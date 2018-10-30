package com.minerworks.webapp.repository;

import com.minerworks.webapp.model.FarmEntity;
import com.minerworks.webapp.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/*
    List<FarmEntity> findAllByUser(User user);

    FarmEntity findByFarmUUID(String farmUUID);

    FarmEntity getByUserAndFarmUUID(User user, String farmUUID);
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FarmEntityRepositoryTest {

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_FARM_CODE = "test-farm-code";

    @Test
    public void findAllByUser_Test() {
        Set<FarmEntity> farmEntitySet = farmEntityRepository.findAllByUser(getTestUser());

        assertFalse(farmEntitySet.isEmpty());
        System.out.println(farmEntitySet);
    }

    @Test
    public void findByFarmUUID_Test() {
        FarmEntity farmEntity = farmEntityRepository.findByFarmUUID(TEST_FARM_CODE);

        assertNotNull(farmEntity);
        System.out.println(farmEntity);
    }

    @Test
    public void getByUserAndFarmUUID_Test() {
        FarmEntity farmEntity = farmEntityRepository.getByUserAndFarmUUID(getTestUser(), TEST_FARM_CODE);

        assertNotNull(farmEntity);
        System.out.println(farmEntity);
    }

    private User getTestUser() {
        return userRepository.findByEmail("email@email.com");
    }

}

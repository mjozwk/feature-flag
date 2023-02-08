package com.mjozwk.featureflag.service.db.repo;

import com.mjozwk.featureflag.service.db.entity.FeatureFlag;
import com.mjozwk.featureflag.service.db.entity.User;
import com.mjozwk.featureflag.service.db.entity.UserFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FeatureFlagRepositoryTest {

    @Autowired
    private UserFeatureRepository userFeatureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureFlagRepository testee;

    @Test
    void getGlobalAndEnabledForUser() {
        FeatureFlag featureFlagGlobal = new FeatureFlag();
        featureFlagGlobal.setFeatureName("name");
        featureFlagGlobal.setGlobalEnabled(true);
        testee.save(featureFlagGlobal);

        FeatureFlag featureFlagUser = new FeatureFlag();
        featureFlagUser.setFeatureName("test");
        featureFlagUser.setGlobalEnabled(false);

        User user = new User();
        user = userRepository.save(user);

        UserFeature userFeature = new UserFeature();
        userFeature.setFeature(featureFlagUser);
        userFeature.setUser(user);
        userFeature.setEnabledForUser(true);

        featureFlagUser.setUserFeatures(List.of(userFeature));
        testee.save(featureFlagUser);
        userFeatureRepository.save(userFeature);



        List<FeatureFlag> globalAndEnabledForUser = testee.findAllByUserId(user.getId());

        assertThat(globalAndEnabledForUser)
                .hasSize(2)
                .contains(featureFlagGlobal, featureFlagUser);
    }
}
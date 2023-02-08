package com.mjozwk.featureflag.service.db.repo;

import com.mjozwk.featureflag.service.db.entity.FeatureFlag;
import com.mjozwk.featureflag.service.db.entity.User;
import com.mjozwk.featureflag.service.db.entity.UserFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeature, Long> {
    Optional<UserFeature> findByFeatureAndUser(FeatureFlag featureFlag, User user);
}

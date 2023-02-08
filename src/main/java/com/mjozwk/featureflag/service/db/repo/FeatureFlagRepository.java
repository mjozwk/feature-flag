package com.mjozwk.featureflag.service.db.repo;

import com.mjozwk.featureflag.service.db.entity.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {
    @Query("""
            SELECT DISTINCT f
            FROM FeatureFlag f
            LEFT JOIN f.userFeatures uf
            WHERE f.isGlobalEnabled = true OR (uf.user.id = :userId AND uf.isEnabledForUser = true)
            """)
    List<FeatureFlag> findAllByUserId(Long userId);

    Boolean existsByFeatureName(String featureName);
}

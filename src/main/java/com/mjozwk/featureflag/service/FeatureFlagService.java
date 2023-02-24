package com.mjozwk.featureflag.service;

import com.mjozwk.featureflag.api.FeatureFlagDTO;
import com.mjozwk.featureflag.api.GlobalAndEnabledForUserDTO;
import com.mjozwk.featureflag.api.UserFeatureDTO;

import java.util.List;

public interface FeatureFlagService {
    void createFeature(FeatureFlagDTO featureFlagDTO) throws FeatureFlagBadParamException;
    void switchForUser(UserFeatureDTO userFeatureDTO, Long userId) throws FeatureFlagBadParamException;
    List<GlobalAndEnabledForUserDTO> getGlobalAndEnabledForUser(Long userId);
}


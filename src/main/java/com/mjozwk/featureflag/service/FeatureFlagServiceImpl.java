package com.mjozwk.featureflag.service;

import com.mjozwk.featureflag.api.FeatureFlagDTO;
import com.mjozwk.featureflag.api.GlobalAndEnabledForUserDTO;
import com.mjozwk.featureflag.api.UserFeatureDTO;
import com.mjozwk.featureflag.service.db.entity.FeatureFlag;
import com.mjozwk.featureflag.service.db.entity.User;
import com.mjozwk.featureflag.service.db.entity.UserFeature;
import com.mjozwk.featureflag.service.db.repo.FeatureFlagRepository;
import com.mjozwk.featureflag.service.db.repo.UserFeatureRepository;
import com.mjozwk.featureflag.service.db.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureFlagServiceImpl implements FeatureFlagService {

    @Autowired
    private FeatureFlagRepository featureFlagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFeatureRepository userFeatureRepository;

    public FeatureFlagServiceImpl() {

    }

    @Override
    public void createFeature(FeatureFlagDTO featureFlagDTO) throws FeatureFlagBadParamException {
        if (Boolean.TRUE.equals(featureFlagRepository.existsByFeatureName(featureFlagDTO.featureName()))) {
            throw new FeatureFlagBadParamException("Feature already exists");
        }

        FeatureFlag featureFlag = new FeatureFlag();
        featureFlag.setFeatureName(featureFlagDTO.featureName());
        featureFlagRepository.save(featureFlag);
    }

    @Override
    public void switchForUser(UserFeatureDTO userFeatureDTO, Long userId) throws FeatureFlagBadParamException {
        FeatureFlag featureFlag = featureFlagRepository.findById(userFeatureDTO.featureFlagId()).orElseThrow(() -> new FeatureFlagBadParamException("Feature not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new FeatureFlagBadParamException("User not found"));

        UserFeature userFeature = userFeatureRepository.findByFeatureAndUser(featureFlag, user).orElse(new UserFeature());
        userFeature.setFeature(featureFlag);
        userFeature.setUser(user);
        userFeature.setEnabledForUser(userFeatureDTO.isEnabledForUser());


        userFeatureRepository.save(userFeature);
    }


    @Override
    public List<GlobalAndEnabledForUserDTO> getGlobalAndEnabledForUser(Long userId) {
        List<FeatureFlag> globalAndEnabledForUser = featureFlagRepository.findAllByUserId(userId);
        return mapToGlobalAndEnabledForUserDTO(globalAndEnabledForUser);
    }

    private List<GlobalAndEnabledForUserDTO> mapToGlobalAndEnabledForUserDTO(List<FeatureFlag> globalAndEnabledForUser) {
        return globalAndEnabledForUser.stream()
                .map(featureFlag -> new GlobalAndEnabledForUserDTO(featureFlag.getId(), featureFlag.getFeatureName(), featureFlag.isGlobalEnabled()))
                .toList();
    }

}

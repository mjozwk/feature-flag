package com.mjozwk.featureflag.service.rest;

import com.mjozwk.featureflag.api.FeatureFlagDTO;
import com.mjozwk.featureflag.api.FeatureFlagRESTService;
import com.mjozwk.featureflag.api.GlobalAndEnabledForUserDTO;
import com.mjozwk.featureflag.api.UserFeatureDTO;
import com.mjozwk.featureflag.service.FeatureFlagBadParamException;
import com.mjozwk.featureflag.service.FeatureFlagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureFlagRESTServiceImpl implements FeatureFlagRESTService {

    private final FeatureFlagService featureFlagService;

    @Override
    public ResponseEntity<Object> createFeatureFlag(FeatureFlagDTO featureFlagDTO) {
        try {
            featureFlagService.createFeature(featureFlagDTO);
        } catch (FeatureFlagBadParamException e) {
            return new ResponseEntity<>("Failed to create feature: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> switchUserFeature(UserFeatureDTO userFeatureDTO, Long userId) {
        try {
            featureFlagService.switchForUser(userFeatureDTO, userId);
        } catch (FeatureFlagBadParamException e) {
            return new ResponseEntity<>("Failed to enable feature for user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public List<GlobalAndEnabledForUserDTO> getGlobalAndEnabledForUser(Long userId) {
        return featureFlagService.getGlobalAndEnabledForUser(userId);
    }
}

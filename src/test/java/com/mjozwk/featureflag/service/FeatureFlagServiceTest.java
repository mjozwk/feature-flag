package com.mjozwk.featureflag.service;

import com.mjozwk.featureflag.api.FeatureFlagDTO;
import com.mjozwk.featureflag.api.GlobalAndEnabledForUserDTO;
import com.mjozwk.featureflag.api.UserFeatureDTO;
import com.mjozwk.featureflag.service.db.entity.FeatureFlag;
import com.mjozwk.featureflag.service.db.entity.User;
import com.mjozwk.featureflag.service.db.repo.FeatureFlagRepository;
import com.mjozwk.featureflag.service.db.repo.UserFeatureRepository;
import com.mjozwk.featureflag.service.db.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeatureFlagServiceTest {

    @Mock
    private FeatureFlagRepository featureFlagRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFeatureRepository userFeatureRepository;

    @InjectMocks
    private FeatureFlagService testee = new FeatureFlagServiceImpl();

    @Test
    void createFeature_ShouldSaveFeatureFlag() throws FeatureFlagBadParamException {
        FeatureFlagDTO featureFlagDTO = new FeatureFlagDTO();
        featureFlagDTO.setFeatureName("testFeature");

        testee.createFeature(featureFlagDTO);

        verify(featureFlagRepository, times(1)).save(any());
    }

    @Test
    void enableForUser_ShouldSaveEnabledFeatureFlagForUser() throws FeatureFlagBadParamException {
        UserFeatureDTO userFeatureDTO = new UserFeatureDTO();
        userFeatureDTO.setFeatureFlagId(1L);
        userFeatureDTO.setUserId(1L);
        userFeatureDTO.setIsEnabledForUser(true);

        when(featureFlagRepository.findById(userFeatureDTO.getFeatureFlagId())).thenReturn(Optional.of(mock(FeatureFlag.class)));
        when(userRepository.findById(userFeatureDTO.getUserId())).thenReturn(Optional.of(mock(User.class)));

        testee.switchForUser(userFeatureDTO);

        verify(userFeatureRepository, times(1)).save(any());
    }

    @Test
    void getGlobalAndEnabledForUser() {

        FeatureFlag featureFlagGlobal = new FeatureFlag();
        featureFlagGlobal.setId(1L);
        featureFlagGlobal.setGlobalEnabled(true);

        FeatureFlag featureFlagUser = new FeatureFlag();
        featureFlagUser.setId(2L);

        when(featureFlagRepository.findAllByUserId(1L)).thenReturn(List.of(featureFlagGlobal, featureFlagUser));

        List<GlobalAndEnabledForUserDTO> globalAndEnabledForUser = testee.getGlobalAndEnabledForUser(1L);

        assertNotNull(globalAndEnabledForUser);
        assertThat(globalAndEnabledForUser).isNotEmpty().hasSize(2);

    }
}
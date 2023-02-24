package com.mjozwk.featureflag;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjozwk.featureflag.api.FeatureFlagDTO;
import com.mjozwk.featureflag.api.GlobalAndEnabledForUserDTO;
import com.mjozwk.featureflag.api.UserFeatureDTO;
import com.mjozwk.featureflag.service.db.entity.FeatureFlag;
import com.mjozwk.featureflag.service.db.entity.User;
import com.mjozwk.featureflag.service.db.entity.UserFeature;
import com.mjozwk.featureflag.service.db.repo.FeatureFlagRepository;
import com.mjozwk.featureflag.service.db.repo.UserFeatureRepository;
import com.mjozwk.featureflag.service.db.repo.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FeatureFlagIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeatureFlagRepository featureFlagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFeatureRepository userFeatureRepository;

    @BeforeEach
    public void setUp() {
        userFeatureRepository.deleteAll();
        userRepository.deleteAll();
        featureFlagRepository.deleteAll();
    }

    @Test
    void shouldCreateFeatureFlag() throws Exception {
        // given
        FeatureFlagDTO featureFlagDTO = new FeatureFlagDTO("featureName");

        // when
        mockMvc.perform(post("/feature-flag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(featureFlagDTO)))
                .andExpect(status().isOk());

        // then
        List<FeatureFlag> featureFlags = featureFlagRepository.findAll();
        assertThat(featureFlags).hasSize(1);
        assertThat(featureFlags.get(0).getFeatureName()).isEqualTo("featureName");
        assertThat(featureFlags.get(0).isGlobalEnabled()).isFalse();

    }

    @Test
    void shouldSwitchForUser() throws Exception {
        // given
        FeatureFlag featureFlag = new FeatureFlag();
        featureFlag = featureFlagRepository.save(featureFlag);

        User user = new User();
        user = userRepository.save(user);

        UserFeatureDTO userFeatureDTO = new UserFeatureDTO(featureFlag.getId(), true);

        // when
        mockMvc.perform(post("/user-feature/switch/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userFeatureDTO)))
                .andExpect(status().isOk());

        // then
        assertThat(userFeatureRepository.findAll()).hasSize(1);
        assertThat(userFeatureRepository.findAll().get(0).isEnabledForUser()).isTrue();
    }

    @Test
    void shouldReturnListOfEnabledFeaturesForUser() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUserName("test_user");
        userRepository.save(user);

        FeatureFlag feature1 = new FeatureFlag();
        feature1.setId(1L);
        feature1.setFeatureName("feature1");
        feature1.setGlobalEnabled(true);
        featureFlagRepository.save(feature1);

        FeatureFlag feature2 = new FeatureFlag();
        feature2.setId(2L);
        feature2.setFeatureName("feature2");
        feature2.setGlobalEnabled(false);
        featureFlagRepository.save(feature2);

        FeatureFlag feature3 = new FeatureFlag();
        feature3.setId(3L);
        feature3.setFeatureName("feature3");
        feature3.setGlobalEnabled(false);
        featureFlagRepository.save(feature3);

        UserFeature userFeature = new UserFeature();
        userFeature.setUser(user);
        userFeature.setFeature(feature3);
        userFeature.setEnabledForUser(true);
        userFeatureRepository.save(userFeature);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/global-and-enabled/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].featureName", Matchers.equalTo("feature1")))
                .andExpect(jsonPath("$[1].featureName", Matchers.equalTo("feature3")))
                .andReturn();

        List<GlobalAndEnabledForUserDTO> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        // then
        assertThat(result).hasSize(2)
                .extracting(GlobalAndEnabledForUserDTO::featureName)
                .contains("feature1", "feature3")
                .doesNotContain("feature2");
    }

}

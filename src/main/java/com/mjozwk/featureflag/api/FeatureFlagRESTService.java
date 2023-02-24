package com.mjozwk.featureflag.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface FeatureFlagRESTService {

    @PostMapping(("/feature-flag"))
    ResponseEntity<Object> createFeatureFlag(@RequestBody @Valid FeatureFlagDTO featureFlagDTO);

    @PostMapping("/user-feature/switch/users/{user-id}")
    ResponseEntity<Object> switchUserFeature(@RequestBody @Valid UserFeatureDTO userFeatureDTO, @PathVariable("user-id") @NotNull Long userId);

    @GetMapping("/global-and-enabled/users/{user-id}")
    List<GlobalAndEnabledForUserDTO> getGlobalAndEnabledForUser(@PathVariable("user-id") @NotNull Long userId);

}

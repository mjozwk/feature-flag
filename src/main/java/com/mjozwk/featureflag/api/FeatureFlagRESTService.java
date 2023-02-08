package com.mjozwk.featureflag.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feature-flag")
public interface FeatureFlagRESTService {

    @PostMapping("/create")
    ResponseEntity<Object> createFeature(@RequestBody @Valid FeatureFlagDTO featureFlagDTO);

    @PostMapping("/switchForUser")
    ResponseEntity<Object> switchForUser(@RequestBody @Valid UserFeatureDTO userFeatureDTO);

    @GetMapping("/getGlobalAndEnabledForUser/{userId}")
    List<GlobalAndEnabledForUserDTO> getGlobalAndEnabledForUser(@PathVariable("userId") @NotNull Long userId);

}

package com.mjozwk.featureflag.api;

import jakarta.validation.constraints.NotNull;

public record UserFeatureDTO (
        @NotNull
        Long featureFlagId,
        @NotNull
        Boolean isEnabledForUser
) {}

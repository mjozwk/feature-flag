package com.mjozwk.featureflag.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FeatureFlagDTO (
        @NotNull
        @NotEmpty
        String featureName
) {}

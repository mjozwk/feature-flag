package com.mjozwk.featureflag.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureFlagDTO {
        @NotNull
        @NotEmpty
        private String featureName;
}

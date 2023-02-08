package com.mjozwk.featureflag.api;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFeatureDTO {
        @NotNull
        private Long userId;
        @NotNull
        private Long featureFlagId;
        @NotNull
        private Boolean isEnabledForUser;
}

package com.mjozwk.featureflag.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalAndEnabledForUserDTO {
    private Long id;
    private String featureName;
    private boolean isGlobal;
}

package com.mjozwk.featureflag.api;

public record GlobalAndEnabledForUserDTO (
    Long id,
    String featureName,
    boolean isGlobal
) {}

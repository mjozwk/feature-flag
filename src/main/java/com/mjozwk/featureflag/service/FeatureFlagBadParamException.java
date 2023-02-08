package com.mjozwk.featureflag.service;

public class FeatureFlagBadParamException extends Exception {
    public FeatureFlagBadParamException(String msg) {
        super(msg);
    }
}

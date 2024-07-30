package com.example.mesker;

import java.util.regex.Pattern;

public class SensitiveFieldConfig {
    private final Pattern pattern;
    private final MaskingStrategyType strategy;
    private final Object params;

    public SensitiveFieldConfig(Pattern pattern, MaskingStrategyType strategy, Object params) {
        this.pattern = pattern;
        this.strategy = strategy;
        this.params = params;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public MaskingStrategyType getStrategy() {
        return strategy;
    }

    public Object getParams() {
        return params;
    }
}


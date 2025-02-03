package com.example.mesker;

import java.util.regex.Pattern;



import java.util.regex.Pattern;

public class SensitiveFieldConfig {
    private final Pattern pattern;
    private final MaskingStrategyType strategy;
    private final Object params;
    private final char maskChar;

    public SensitiveFieldConfig(Pattern pattern, MaskingStrategyType strategy, Object params, char maskChar) {
        this.pattern = pattern;
        this.strategy = strategy;
        this.params = params;
        this.maskChar = maskChar;
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

    public char getMaskChar() {
        return maskChar;
    }
}

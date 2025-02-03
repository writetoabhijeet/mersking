package com.example.mesker;

 import java.util.List;

public class MaskingStrategyFactory {

    public static MaskingStrategy getStrategy(MaskingStrategyType strategy, Object params, char maskChar) {
        switch (strategy) {
            case REPLACE:
                return new ReplaceMaskingStrategy(maskChar);
            case HASH:
                return new HashMaskingStrategy();
            case UNMASK_LAST_N_CHARS:
                return new UnmaskLastNCharsStrategy((int) params, maskChar);
            case UNMASK_FIRST_N_CHARS:
                return new UnmaskFirstNCharsStrategy((int) params, maskChar);
            case UNMASK_FIRST_AND_LAST_N_CHARS:
                List<Integer> paramList = (List<Integer>) params;
                return new UnmaskFirstAndLastNCharsStrategy(paramList.get(0), paramList.get(1), maskChar);
            default:
                throw new IllegalArgumentException("Unsupported masking strategy: " + strategy);
        }
    }
}
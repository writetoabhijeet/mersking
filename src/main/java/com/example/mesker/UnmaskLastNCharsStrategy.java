package com.example.mesker;

public class UnmaskLastNCharsStrategy implements MaskingStrategy {
    private final int n;

    public UnmaskLastNCharsStrategy(int n) {
        this.n = n;
    }

    @Override
    public String mask(String input) {
        if (input.length() <= n) return input;
        return "***MASKED***" + input.substring(input.length() - n);
    }
}

public class UnmaskFirstNCharsStrategy implements MaskingStrategy {
    private final int n;

    public UnmaskFirstNCharsStrategy(int n) {
        this.n = n;
    }

    @Override
    public String mask(String input) {
        if (input.length() <= n) return input;
        return input.substring(0, n) + "***MASKED***";
    }
}

public class UnmaskFirstAndLastNCharsStrategy implements MaskingStrategy {
    private final int firstN;
    private final int lastN;

    public UnmaskFirstAndLastNCharsStrategy(int firstN, int lastN) {
        this.firstN = firstN;
        this.lastN = lastN;
    }

    @Override
    public String mask(String input) {
        if (input.length() <= firstN + lastN) return input;
        return input.substring(0, firstN) + "***MASKED***" + input.substring(input.length() - lastN);
    }
}


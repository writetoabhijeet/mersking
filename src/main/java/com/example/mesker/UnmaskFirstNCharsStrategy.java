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
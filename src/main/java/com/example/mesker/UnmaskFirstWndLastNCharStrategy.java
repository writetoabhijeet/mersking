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
public class ReplaceMaskingStrategy implements MaskingStrategy {
    private final char maskChar;

    public ReplaceMaskingStrategy(char maskChar) {
        this.maskChar = maskChar;
    }

    @Override
    public String mask(String input) {
        return String.valueOf(maskChar).repeat(input.length());
    }
}
package validator;

public class Lexeme {
    public String tokenValue;
    public LexemeTokenTypes tokenType; //ID of token corresponding to values in LexemeTokenTypes

    public enum LexemeTokenTypes {
        OPENING_BRACE, CLOSING_BRACE, COMMA,
        OPENING_BRACKET,
        CLOSING_BRACKET,
        COLON,
        OTHER
    }

    public Lexeme(String tokenValue, LexemeTokenTypes tokenType) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }
}

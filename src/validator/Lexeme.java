package validator;

public class Lexeme {
    public String tokenValue;
    public int tokenType; //ID of token corresponding to values in LexemeTokenTypes

    public Lexeme(String tokenValue, int tokenType) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }
}

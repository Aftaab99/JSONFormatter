package validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Lexer {

    public List<Lexeme> tokenizeString(String inputString) {

        try {
            inputString = removeWhitespaces(inputString);
        } catch (QuoteMismatchException ex) {
            return null;
        }
        StringBuilder currentToken = new StringBuilder();
        List<Lexeme> tokenList = new ArrayList<>();
        boolean insideQuotes = false;
        for (int i = 0; i < inputString.length(); i++) {
            if (insideQuotes) {
                if (inputString.charAt(i) != '\\') {
                    currentToken.append(inputString.charAt(i));
                    if (inputString.charAt(i) == '"') {
                        insideQuotes = false;
                    }
                } else {
                    currentToken.append("\\");
                    i++;
                    currentToken.append(inputString.charAt(i));

                }

            } else {

                if (inputString.charAt(i) == '{') {
                    tokenList.add(new Lexeme("{", Lexeme.LexemeTokenTypes.OPENING_BRACE));
                } else if (inputString.charAt(i) == '}') {
                    if (!currentToken.toString().equals("")) {
                        tokenList.add(new Lexeme(currentToken.toString(), Lexeme.LexemeTokenTypes.OTHER));
                        currentToken.setLength(0); //reset currentToken
                    }
                    tokenList.add(new Lexeme("}", Lexeme.LexemeTokenTypes.CLOSING_BRACE));
                } else if (inputString.charAt(i) == '[') {
                    tokenList.add(new Lexeme("[", Lexeme.LexemeTokenTypes.OPENING_BRACKET));
                } else if (inputString.charAt(i) == ']') {
                    if (!currentToken.toString().equals("")) {
                        tokenList.add(new Lexeme(currentToken.toString(), Lexeme.LexemeTokenTypes.OTHER));
                        currentToken.setLength(0); //reset currentToken
                    }
                    tokenList.add(new Lexeme("]", Lexeme.LexemeTokenTypes.CLOSING_BRACKET));
                } else if (inputString.charAt(i) == ',') {
                    if (!currentToken.toString().equals("")) {
                        tokenList.add(new Lexeme(currentToken.toString(), Lexeme.LexemeTokenTypes.OTHER));
                        currentToken.setLength(0); //reset currentToken
                    }
                    tokenList.add(new Lexeme(",", Lexeme.LexemeTokenTypes.COMMA));
                } else if (inputString.charAt(i) == ':') {
                    if (!currentToken.toString().equals("")) {
                        tokenList.add(new Lexeme(currentToken.toString(), Lexeme.LexemeTokenTypes.OTHER));
                        currentToken.setLength(0); //reset currentToken
                    }
                    tokenList.add(new Lexeme(":", Lexeme.LexemeTokenTypes.COLON));
                } else if (inputString.charAt(i) == '"') {
                    insideQuotes = true;
                    currentToken.append('"');
                } else {
                    currentToken.append(inputString.charAt(i));
                }
            }

        }
        return tokenList;
    }

    public String removeWhitespaces(String inputString) throws QuoteMismatchException {
        StringBuilder result = new StringBuilder();
        boolean insideQuotes = false;
        for (int i = 0; i < inputString.length(); i++) {

            if (inputString.charAt(i) == '\\') {
                result.append("\\");
                i++;
                result.append(inputString.charAt(i));
            } else if (inputString.charAt(i) == '"') {
                result.append('"');
                insideQuotes = !insideQuotes;
            } else if (!Character.isWhitespace(inputString.charAt(i)) || insideQuotes) {
                result.append(inputString.charAt(i));
            }
        }
        if (insideQuotes) {
            throw new QuoteMismatchException();
        }
        return result.toString();
    }

}

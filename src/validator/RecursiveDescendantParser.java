package validator;

import java.util.ArrayList;
import java.util.List;

public class RecursiveDescendantParser {

    int index;

    public boolean isValidJSON(List<Lexeme> inputTokenList) {
        index = 0;
        if (object(inputTokenList) || array(inputTokenList))
            return true;
        return false;
    }

    private boolean object(List<Lexeme> input) {
        if (index<input.size() && input.get(index).tokenType == LexemeTokenTypes.OPENING_BRACE) {
            index += 1;

            members(input);

            if (index<input.size() && input.get(index).tokenType == LexemeTokenTypes.CLOSING_BRACE) {
                index += 1;
                return true;
            }
        }
        return false;

    }

    private boolean pair(List<Lexeme> input) {
        if (index<input.size() && isValidIdentifier(input.get(index).tokenValue)) {
            index += 1;
            if (index<input.size() && input.get(index).tokenType == LexemeTokenTypes.COLON) {
                index += 1;
                return value(input);
            }
        }
        return false;
    }

    private boolean value(List<Lexeme> input) {
        if(index>=input.size())
            return false;
        String val = input.get(index).tokenValue;
        return isString(val) || isNumeric(val) || val.equals("true") || val.equals("false") || val.equals("null")
                || object(input) || array(input);
    }

    private boolean members(List<Lexeme> input) {
        if (index<input.size() && pair(input)) {
            index += 1;
            if (index<input.size() && input.get(index).tokenType == LexemeTokenTypes.COMMA) {
                index += 1;
                return members(input);
            } else
                return true;
        }
        return false;
    }

    private boolean elements(List<Lexeme> input) {
        if (index<input.size() && value(input)) {
            index += 1;
            if (index<input.size() && input.get(index).tokenType == LexemeTokenTypes.COMMA) {
                index += 1;
                return elements(input);
            } else
                return true;
        }
        return false;
    }

    private boolean array(List<Lexeme> input) {
        if (index<input.size() && input.get(index).tokenType == LexemeTokenTypes.OPENING_BRACKET) {
            index += 1;
            if (index<input.size() && elements(input)) {
                index += 1;
            }
            if (index<input.size() && input.get(index).tokenType == LexemeTokenTypes.CLOSING_BRACKET) {
                index += 1;
                return true;
            }
        }
        return false;
    }

    private boolean isString(String s) {
        if (s.charAt(0) == '\"' && s.charAt(s.length() - 1) == '\"')
            return true;
        return false;
    }

    //helper function
    private boolean isNumeric(String s) {
        try {
            if (s == null)
                return false;
            Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private boolean isValidIdentifier(String str) {
        if (str.length() < 3)
            return false;
        if (str.charAt(0) != '\"' || str.charAt(str.length() - 1) != '\"')
            return false;
        if (!(Character.isLetter(str.charAt(1)) || str.charAt(1) == '_'))
            return false;
        for (int i = 2; i < str.length() - 1; i++) {
            if (!((Character.isLetterOrDigit(str.charAt(i)))
                    || str.charAt(i) == '_'))
                return false;
        }
        return true;
    }

}

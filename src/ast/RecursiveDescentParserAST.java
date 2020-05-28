
package ast;

import validator.Lexeme;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursiveDescentParserAST {

    int index;

    public ASTNode buildAST(List<Lexeme> inputTokenList) {
        index = 0;
        ASTNode objectNode = object(inputTokenList);
        if (objectNode != null) {
            return objectNode;
        }
        return array(inputTokenList);
    }

    private static class Pair {
        String key;
        ASTNode value;

        Pair(String key, ASTNode value) {
            this.key = key;
            this.value = value;
        }

        Pair() {

        }
    }


    private Pair pair(List<Lexeme> input) {
        Pair p = new Pair();
        if (index < input.size() && isValidIdentifier(input.get(index).tokenValue)) {
            p.key = input.get(index).tokenValue;
            index += 1;
            if (index < input.size() && input.get(index).tokenType == Lexeme.LexemeTokenTypes.COLON) {
                index += 1;
                p.value = value(input);
                return p;
            }
        }
        System.out.println("False call in PAIR, i=" + index);
        return null;
    }

    private ASTNode value(List<Lexeme> input) {
        if (index >= input.size()) {
            return null;
        }
        String val = input.get(index).tokenValue;

        if (isString(val)) {
            index += 1;
            return new Value(val);
        } else if (isNumeric(val)) {
            index += 1;
            return new Value(val);
        } else if (val.equals("true") || val.equals("false")) {
            index += 1;
            return new Value(val);
        } else if (val.equals("null")) {
            index += 1;
            return new Value(val);
        }

        ASTNode objNode = object(input);
        if (objNode != null)
            return objNode;
        return array(input);


    }

    private Map<String, ASTNode> members(List<Lexeme> input, Map<String, ASTNode> membersNode) {
        Pair pairNode = pair(input);
        if (index < input.size() && pairNode != null) {
            membersNode.put(pairNode.key, pairNode.value);
            if (input.get(index).tokenType == Lexeme.LexemeTokenTypes.COMMA) {
                index += 1;
                return members(input, membersNode);
            } else
                return membersNode;
        }
        return null;
    }

    private ArrayList<ASTNode> elements(List<Lexeme> input, ArrayList<ASTNode> elementList) {
        ASTNode valueNode = value(input);
        if (index < input.size() && valueNode != null) {
            elementList.add(valueNode);
            System.out.println("Element list got updated. New size:"+elementList.size());
            if (index < input.size() && input.get(index).tokenType == Lexeme.LexemeTokenTypes.COMMA) {
                index += 1;
                return elements(input, elementList);
            } else
                return elementList;
        }
        return null;
    }

    private JsonArray array(List<Lexeme> input) {
        ArrayList<ASTNode> elementList = new ArrayList<>();
        if (index < input.size() && input.get(index).tokenType == Lexeme.LexemeTokenTypes.OPENING_BRACKET) {
            index += 1;
            if (index < input.size() && input.get(index).tokenType == Lexeme.LexemeTokenTypes.CLOSING_BRACKET) {
                index += 1;
                return new JsonArray(elementList);
            } else {
                elementList = elements(input, elementList);
                if (elementList != null) {

                    if (index < input.size() && input.get(index).tokenType == Lexeme.LexemeTokenTypes.CLOSING_BRACKET) {
                        index += 1;
                        return new JsonArray(elementList);
                    }
                }
                return null;

            }
        }

        return null;
    }

    private JsonObject object(List<Lexeme> input) {
        if (index < input.size() && input.get(index).tokenType == Lexeme.LexemeTokenTypes.OPENING_BRACE) {
            index += 1;
            Map<String, ASTNode> memberNodes = new HashMap<>();
            memberNodes = members(input, memberNodes);
            if (memberNodes == null)
                return null;
            if (index < input.size() && input.get(index).tokenType == Lexeme.LexemeTokenTypes.CLOSING_BRACE) {
                index += 1;
                return new JsonObject(memberNodes);
            }
        }
        return null;
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

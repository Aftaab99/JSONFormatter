package formatter;

public class JsonFormat {

    String indent;

    public JsonFormat(int nSpaces) {
        indent = " ".repeat(Math.max(0, nSpaces));
    }

    public String formatJSON(String inputJSON) {
        StringBuilder resultBuilder = new StringBuilder();
        int currentIndentLevel = 0;
        boolean insideString = false;
        for (int i = 0; i < inputJSON.length(); i++) {
            char curChar = inputJSON.charAt(i);
            if (curChar == '{' || curChar == '[') {
                resultBuilder.append(curChar);
                resultBuilder.append('\n');
                currentIndentLevel += 1;
                resultBuilder.append(getNIntends(currentIndentLevel));
            } else if (curChar == ',') {
                resultBuilder.append(',');
                resultBuilder.append('\n');
                resultBuilder.append(getNIntends(currentIndentLevel));
            } else if (curChar == '\"') {
                resultBuilder.append('\"');
                insideString = !insideString;
            } else if (curChar == '}' || curChar == ']') {
                currentIndentLevel -= 1;
                resultBuilder.append('\n');
                resultBuilder.append(getNIntends(currentIndentLevel));
                resultBuilder.append(curChar);
            } else if (curChar == '\\') {
                resultBuilder.append('\\');
                i++;
                resultBuilder.append(inputJSON.charAt(i));
            } else if (!(Character.isWhitespace(curChar)
                    && !insideString)) {
                resultBuilder.append(curChar);
            }
            //else do nothing, extra whitespace

        }
        return resultBuilder.toString();
    }

    private String getNIntends(int n) {
        return indent.repeat(Math.max(0, n));
    }
}

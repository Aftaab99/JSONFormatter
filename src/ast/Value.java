package ast;

public class Value extends ASTNode{

    private final ASTNode.Type nodeType = ASTNode.Type.VALUE;
    enum ValueType {NUMERIC, STRING, NULL, BOOLEAN}
    ValueType valueType;
    String text;

    public Value(String text, ValueType valueType){
        this.text = text;
        this.valueType = valueType;
    }

}

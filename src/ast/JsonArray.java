package ast;

import java.util.ArrayList;

public class JsonArray extends ASTNode{
    private final Type nodeType = Type.ARRAY;
    private ArrayList<ASTNode> arrayElements;
    public JsonArray(ArrayList<ASTNode> arrayElements){
        this.arrayElements = arrayElements;
    }
}

package ast;

import java.util.ArrayList;

public class JsonArray extends ASTNode{
    public ArrayList<ASTNode> arrayElements;
    public JsonArray(ArrayList<ASTNode> arrayElements){
        this.arrayElements = arrayElements;
    }
}

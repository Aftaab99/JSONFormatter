package ast;

import java.util.Map;

public class JsonObject extends ASTNode{
    private final Type nodeType = Type.OBJECT;
    private Map<String, ASTNode> objectPairs;
    public JsonObject(Map<String, ASTNode> objectPairs){
        this.objectPairs = objectPairs;
    }
}

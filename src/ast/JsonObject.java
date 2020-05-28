package ast;

import java.util.Map;

public class JsonObject extends ASTNode{
    public Map<String, ASTNode> objectPairs;
    public JsonObject(Map<String, ASTNode> objectPairs){
        this.objectPairs = objectPairs;
    }
}

package treeview;

import ast.*;
import javafx.scene.control.TreeItem;
import java.util.Map;

public class JsonTreeView {
    public TreeItem<String> buildTree(String name, ASTNode node) {

        TreeItem<String> item = new TreeItem<>();
        if (node instanceof JsonObject) {
            item.setValue(name);
            for (Map.Entry<String, ASTNode> entry : ((JsonObject) node).objectPairs.entrySet()) {
                String childName = entry.getKey();
                ASTNode childNode = entry.getValue();
                if (childNode != null) {
                    TreeItem<String> child = buildTree(childName, childNode);
                    item.getChildren().add(child);
                }
            }
        } else if (node instanceof JsonArray) {
            item.setValue(name);
            for (int i = 0; i < ((JsonArray) node).arrayElements.size(); i++) {
                String childName = String.valueOf(i);
                ASTNode childNode = ((JsonArray) node).arrayElements.get(i);
                if (childNode != null) {
                    TreeItem<String> child = buildTree(childName, childNode);
                    item.getChildren().add(child);
                }
            }
        } else {
            item.setValue(name + " : " + ((Value) node).text);
        }
        return item;
    }
}

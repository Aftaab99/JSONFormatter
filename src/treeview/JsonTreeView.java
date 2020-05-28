package treeview;

import ast.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.shape.Rectangle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import validator.Lexeme;
import validator.Lexer;
import validator.QuoteMismatchException;
import validator.RecursiveDescendantParser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonTreeView {

    enum SymbolType {
        OBJECT_VIEW(new Rectangle(45, 52, 16, 18)),
        ARRAY_VIEW(new Rectangle(61, 88, 16, 18)),
        PROPERTY_VIEW(new Rectangle(31, 13, 16, 18));

        private final Rectangle viewport;

        private SymbolType(Rectangle viewport) {
            this.viewport = viewport;
        }
    }


    public static TreeItem<String> parseJSON(String name, Object json) throws JSONException {
        /*
            Recursively build a TreeItem tree from our AST
            Referred: https://stackoverflow.com/questions/60953087/add-a-jsonobject-string-to-a-treeview-using-javafx
        */
        TreeItem<String> item = new TreeItem<>();
        if (json instanceof JSONObject) {
            item.setValue(name);
            JSONObject object = (JSONObject) json;

            for (Iterator<String> it = object.keys(); it.hasNext(); ) {
                String childName = it.next();
                Object childJson = object.get(childName);
                TreeItem<String> child = parseJSON(childName, childJson);
                item.getChildren().add(child);

            }

        } else if (json instanceof JSONArray) {
            item.setValue(name);
            JSONArray array = (JSONArray) json;
            for (int i = 0; i < array.length(); i++) {
                String childName = String.valueOf(i);
                Object childJson = array.get(i);
                TreeItem<String> child = parseJSON(childName, childJson);
                item.getChildren().add(child);
            }
        } else {
            item.setValue(name + " : " + json);

        }

        return item;
    }

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

package treeview;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.TreeView;
import validator.Lexeme;

import java.util.List;

public class JsonTreeView {

    enum SymbolType {
        OBJECT_VIEW(new Rectangle2D(45, 52, 16, 18)),
        ARRAY_VIEW(new Rectangle2D(61, 88, 16, 18)),
        PROPERTY_VIEW(new Rectangle2D(31, 13, 16, 18));

        private final Rectangle2D viewport;

        private SymbolType(Rectangle2D viewport) {
            this.viewport = viewport;
        }
    }

    public TreeView<JsonTreeViewItem> buildTreeView(String validJsonString){
        // TODO: Function to be completed
        return null;
    }






}

package treeview;

public class JsonTreeViewItem {
    String text;
    JsonTreeView.SymbolType symbolType;

    JsonTreeViewItem(String text, JsonTreeView.SymbolType symbolType){
        this.text = text;
        this.symbolType = symbolType;
    }
}

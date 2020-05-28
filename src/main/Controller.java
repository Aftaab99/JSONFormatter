package main;

import ast.ASTNode;
import ast.RecursiveDescentParserAST;
import formatter.JsonFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import treeview.JsonTreeView;
import validator.Lexeme;
import validator.Lexer;
import validator.QuoteMismatchException;
import validator.RecursiveDescendantParser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextArea jsonInputText;
    @FXML
    Label jsonStatusIndicator;
    @FXML
    Button copyRawTextButton, saveToFileButton;
    @FXML
    TreeView<String> treeView1;
    final String JSON_TEXT_RESET = "Paste JSON or select from file",
            COPY_RAW_TEXT_RESET = "Copy raw text",
            SAVE_TO_FILE_RESET = "Save to file";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jsonInputText.textProperty().addListener((obs, old, niu) -> {
            if (niu.equals("")) {
                jsonStatusIndicator.setText(JSON_TEXT_RESET);
            }
        });
        copyRawTextButton.focusedProperty().addListener((observable, wasFocused, isNowFocused) -> {
                    if (!isNowFocused && wasFocused) {
                        copyRawTextButton.setText(COPY_RAW_TEXT_RESET);
                    }
                }
        );
        saveToFileButton.focusedProperty().addListener((observable, wasFocused, isNowFocused) -> {
                    if (!isNowFocused && wasFocused) {
                        saveToFileButton.setText(SAVE_TO_FILE_RESET);
                    }
                }
        );
    }

    @FXML
    private void handleBrowseJsonBtn(ActionEvent actionEvent) {
        jsonStatusIndicator.setText(JSON_TEXT_RESET);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Browse for a JSON file");
        File selectedFile = fileChooser.showOpenDialog(null);

        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(selectedFile));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            jsonStatusIndicator.setText("File doesn't exist");
        } catch (IOException ex) {
            jsonStatusIndicator.setText("Invalid file, can't read");
        }

        jsonInputText.setText(stringBuffer.toString());
    }

    @FXML
    private void handleCopyRawTextButton(ActionEvent actionEvent) {
        copyRawTextButton.setText("Text Copied!");
        StringSelection selection = new StringSelection(jsonInputText.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    @FXML
    private void handleFormatJsonBtn(ActionEvent actionEvent) {
        jsonStatusIndicator.setText("Processing input");
        JsonFormat jsonFormat = new JsonFormat(2);
        Lexer lexer = new Lexer();
        String inputJson = jsonInputText.getText();
        List<Lexeme> tokenizedInput;
        if ("".equals(inputJson)) {
            jsonStatusIndicator.setText("Empty input. Paste or select JSON from file.");
            return;
        }
        try {
            inputJson = lexer.removeWhitespaces(inputJson);
            tokenizedInput = lexer.tokenizeString(inputJson);
        } catch (QuoteMismatchException quoteMismatchException) {
            jsonStatusIndicator.setText("Invalid JSON. Quotes not balanced");
            return;
        }
        RecursiveDescendantParser parser = new RecursiveDescendantParser();
        if (parser.isValidJSON(tokenizedInput)) {
            jsonStatusIndicator.setText("Valid JSON");
            String formattedJSON = jsonFormat.formatJSON(inputJson);
            jsonInputText.setText(formattedJSON);

            RecursiveDescentParserAST parserAST = new RecursiveDescentParserAST();
            ASTNode rootNode = parserAST.buildAST(tokenizedInput);
            JsonTreeView jsonTreeView = new JsonTreeView();
            TreeItem<String> root = jsonTreeView.buildTree("Preview", rootNode);
            treeView1.setRoot(root);



        } else {
            jsonStatusIndicator.setText("Invalid JSON");
            treeView1.setRoot(null);
        }
    }

    @FXML
    private void handleSaveToFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select formatted JSON file location");
        File selectedFile = fileChooser.showSaveDialog(null);
        String text = jsonInputText.getText();
        try {
            FileWriter writer = new FileWriter(selectedFile);
            writer.write(text);
            writer.flush();
            saveToFileButton.setText("Saved!");
        } catch (IOException e) {
            System.out.println("Something went wrong while writing");
            e.printStackTrace();
        }
    }

}

package main;

import formatter.JsonFormat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import validator.Lexeme;
import validator.Lexer;
import validator.QuoteMismatchException;
import validator.RecursiveDescendantParser;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        JsonFormat jsonFormat = new JsonFormat(4);
        Parent root = FXMLLoader.load(getClass().getResource("main_scene.fxml"));
        primaryStage.setTitle("JSON Formatter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws QuoteMismatchException {
        String json = "{ \"id\" :\"Rahul's \\\" birthday is 1/7/17\", jobs: null, \"B1\", \"C1+90x!\"]}";
        String json1 = "[1,2,3]";
        //Test formatter
        JsonFormat jsonFormat = new JsonFormat(2);
        System.out.println(jsonFormat.formatJSON(json));

        Lexer lexer = new Lexer();
        System.out.println(lexer.removeWhitespaces(json1));
        List<Lexeme> list = lexer.tokenizeString(json1);
        for (Lexeme l : list) {
            System.out.println(String.format("Token = <%s> | Type = <%d>", l.tokenValue, l.tokenType));
        }
        RecursiveDescendantParser parser = new RecursiveDescendantParser();
        System.out.println("The json is " + parser.isValidJSON(list));
        launch(args);
    }
}

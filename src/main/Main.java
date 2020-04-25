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
        String json = "{ \"id\" :\"Rahul's \\\" birthday is 1/7/17\", jobs: [\"A1\", \"B1\", \"C1+90x!\"]}";
        //Test formatter
        JsonFormat jsonFormat = new JsonFormat(2);
        System.out.println(jsonFormat.formatJSON(json));

        Lexer lexer = new Lexer();
        System.out.println(lexer.removeWhitespaces(json));
        List<Lexeme> list = lexer.tokenizeString(json);
        for (Lexeme l : list) {
            System.out.println(String.format("Token = <%s> | Type = <%d>", l.tokenValue, l.tokenType));
        }
        launch(args);
    }
}

package main;

import formatter.JsonFormat;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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




    public static void main(String[] args){
        launch(args);
    }
}

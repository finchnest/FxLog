package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Launch extends Application {
    
    
    public static Stage stage=null;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent fxml = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        
        Scene scene = new Scene(fxml);
        scene.getStylesheets().add("/main/Formatter.css");
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        //stage.initStyle(StageStyle.UTILITY);
        
        this.stage=stage;
        stage.show();
    }

   
    public static void main(String[] args) throws IOException {
        launch(args);
    }
    
}

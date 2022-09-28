package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	//login page size: 600w400h
    	//main page size: 600w700h
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/codebehind/MainPage.fxml"));
        primaryStage.getIcons().add(new Image("view/codebehind/test_logo.png"));
        primaryStage.setTitle("ethOS");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
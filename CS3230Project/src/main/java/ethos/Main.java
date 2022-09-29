package main.java.ethos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The Class Main.
 */
public class Main extends Application {

    public static final String LOGIN_PAGE = "view/codebehind/LoginPage.fxml";
    
    public static final String MAIN_PAGE = "view/codebehind/MainPage.fxml";
    
    public static final String ICON_PATH = "view/codebehind/test_logo.png";

    /**
     * Start.
     *
     * @param primaryStage the primary stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	//login page size: 600w400h
    	//main page size: 600w700h
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(Main.LOGIN_PAGE));
        primaryStage.getIcons().add(new Image(Main.ICON_PATH));
        primaryStage.setTitle("ethOS");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
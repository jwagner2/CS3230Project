package main.java.ethos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;
import main.java.ethos.model.PageType;
import main.java.ethos.view.LoginView;

/**
 * The Class Main.
 */
public class Main extends Application {
    
    public static final String ICON_PATH = "view/codebehind/test_logo.png";

    /**
     * Start.
     *
     * @param primaryStage the primary stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PageType.class.getResource(PageType.LOGIN.label));
        Parent root = loader.load();
        LoginView loginView = loader.<LoginView>getController();
        loginView.initialize(new ControllerManager());
        primaryStage.getIcons().add(new Image(Main.ICON_PATH));
        primaryStage.setTitle("ethos");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setY(10);
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
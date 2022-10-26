package main.java.ethos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class LabView {
    
    @FXML
    private Button backButton;

    @FXML
    private Label currentUserLabel;

    @FXML
    private Label labResultsLabel;

    @FXML
    private TableView<?> labResultsTable;

    @FXML
    private Button logoutButton;

    @FXML
    private ComboBox<?> visitComboBox;

    @FXML
    void handleGetLabs(ActionEvent event) {

    }

    @FXML
    void handleGoBack(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {

    }


}

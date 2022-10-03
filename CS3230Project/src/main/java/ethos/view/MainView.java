package main.java.ethos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;
import main.java.ethos.model.PageType;

public class MainView {

    private ControllerManager manager = new ControllerManager();

    @FXML
    private TableView<?> patientDataTableView;

    @FXML
    private Button registerPatientButton;

    @FXML
    private Label currentDateLabel;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label currentUserLabel;

    @FXML
    private TextField patientNameSearchField;

    @FXML
    private DatePicker patientDobPicker;

    @FXML
    private Button searchButton;

    @FXML
    void handleRegister(ActionEvent event) {
        this.manager.changeToPatientInfoView((Stage) this.registerPatientButton.getScene().getWindow());
    }

    @FXML
    void handleSearch(ActionEvent event) {
        //call manager to handle search DAL stuff
    }

    @FXML
    void handleViewInfo(ActionEvent event) {

    }

    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.currentUserLabel.textProperty().set(manager.getLoggedInName());
    }
}

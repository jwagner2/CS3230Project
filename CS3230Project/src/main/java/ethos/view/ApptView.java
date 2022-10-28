package main.java.ethos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ApptView {

    @FXML
    private TableView<?> apptDataTableView;

    @FXML
    private DatePicker apptDatePicker;

    @FXML
    private Button bookAppt;

    @FXML
    private Label currentDateLabel;

    @FXML
    private Label currentUserLabel;

    @FXML
    private ComboBox<?> doctorComboBox;

    @FXML
    private Button logoutButton;

    @FXML
    private Button startVisitButton;
    
    @FXML
    private ComboBox<?> timeComboBox;

    @FXML
    void handleBook(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

    @FXML
    void handleStartVisit(ActionEvent event) {

    }

}

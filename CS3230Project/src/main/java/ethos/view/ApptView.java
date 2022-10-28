package main.java.ethos.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import main.java.ethos.controller.ControllerManager;

public class ApptView {
    
    private ControllerManager manager = new ControllerManager();
    ObservableList<Map<String, Object>> appointments = FXCollections.<Map<String, Object>>observableArrayList();
    
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
    
    
    /**
     * Initialize the mainview
     *
     * @param manager the manager
     */
    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.currentUserLabel.textProperty()
                .set("Hello, " + manager.getLoggedInName() + " (" + manager.getLoggedInUserName() + ")");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E, M/d/Y");
        this.currentDateLabel.textProperty().set(format.format(calendar.getTime()));

    }
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

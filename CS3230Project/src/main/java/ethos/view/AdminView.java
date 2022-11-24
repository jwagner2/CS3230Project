package main.java.ethos.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

public class AdminView {

    private ControllerManager manager;

    @FXML
    @SuppressWarnings("rawtypes")
    private TableView<Map> queryResultsTableView;

    @FXML
    private Label currentDateLabel;

    @FXML
    private Label currentUserLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TextArea queryEntryTextArea;

    @FXML
    private Button submitButton;

    @FXML
    private Button generateReportButton;

    @FXML
    void handleGenerateReport(ActionEvent event) {
        this.manager.changeToReportView((Stage) this.generateReportButton.getScene().getWindow());
    }

    /**
     * Handle logout.
     *
     * @param event the event
     */
    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }

    @FXML
    void handleSubmit(ActionEvent event) {

    }

    /**
     * Initialize the admin view
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

}

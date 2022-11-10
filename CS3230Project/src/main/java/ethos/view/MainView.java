package main.java.ethos.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

/**
 * The Class MainView.
 */
public class MainView {

    private ControllerManager manager = new ControllerManager();
    ObservableList<Map<String, Object>> patients = FXCollections.<Map<String, Object>>observableArrayList();

    @FXML
    private TableView<Map> patientDataTableView;

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
    private Button viewPatientButton;

    @FXML
    private Button logoutButton;
    
    @FXML
    private Button bookAppointment;

    @FXML
    private Button viewPastVisitsButton;

    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }
    
    @FXML
    void handleBook(ActionEvent event) {
        this.manager.changeToApptView((Stage) this.bookAppointment.getScene().getWindow());
    }

    @FXML
    void handleRegister(ActionEvent event) {
        this.manager.clearDisplayedPatient();
        this.manager.changeToPatientInfoView((Stage) this.registerPatientButton.getScene().getWindow());
    }

    @FXML
    void handleSearch(ActionEvent event) {
        this.patientDataTableView.getItems().clear();
        this.patients.clear();
        String[] split = this.patientNameSearchField.getText().trim().split("\\s+");
        Date date = null;
        if (this.patientDobPicker.getValue() != null) {
            date = Date.valueOf(this.patientDobPicker.getValue());
        }

        if (split.length == 2) {
            this.patients.addAll(this.manager.executeSearch(split[0], split[1], date));
        } else if (date != null && this.patientNameSearchField.getText().isBlank()) {
            this.patients.addAll(this.manager.executeSearch("", "", date));
        } else {
            Alert alert = new Alert(AlertType.ERROR,
                    "Invalid Name Entry.\n" + "Must be first name and last name seperated by single space.",
                    ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                this.patientNameSearchField.clear();
            }
        }

        this.patientDataTableView.getItems().addAll(this.patients);
    }

    @FXML
    void handleViewInfo(ActionEvent event) {
        this.manager.changeToPatientInfoView((Stage) this.registerPatientButton.getScene().getWindow());
    }

    @FXML
    void handleViewPastVisits(ActionEvent event) {
        this.manager.changeToPastVisitsView((Stage) this.viewPastVisitsButton.getScene().getWindow());
    }

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

        this.patientNameListener();
        this.patientDobListener();
        this.initializeTableView();
        this.tableListener();
    }

    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.patientDataTableView.getColumns().clear();
        TableColumn<Map, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new MapValueFactory<>("firstName"));
        TableColumn<Map, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new MapValueFactory<>("lastName"));
        TableColumn<Map, String> dobColumn = new TableColumn<>("DOB");
        dobColumn.setCellValueFactory(new MapValueFactory<>("dob"));
        TableColumn<Map, String> phoneColumn = new TableColumn<>("Phone number");
        phoneColumn.setCellValueFactory(new MapValueFactory<>("phone"));
        this.patientDataTableView.getColumns().add(firstNameColumn);
        this.patientDataTableView.getColumns().add(lastNameColumn);
        this.patientDataTableView.getColumns().add(dobColumn);
        this.patientDataTableView.getColumns().add(phoneColumn);
        List<Map<String, Object>> currentResults = this.manager.buildResultsForTable();
        if (currentResults != null) {
            this.patients.addAll(currentResults);
            this.patientDataTableView.getItems().addAll(this.patients);
        }
    }

    private void patientNameListener() {
        this.patientNameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank() && this.patientDobPicker.valueProperty() == null) {
                this.searchButton.setDisable(true);
            } else {
                this.searchButton.setDisable(false);
            }
        });
    }

    private void patientDobListener() {
        this.patientDobPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null && this.patientNameSearchField.getText().isBlank()) {
                this.searchButton.setDisable(true);
            } else {
                this.searchButton.setDisable(false);
            }
        });
    }

    private void tableListener() {
        this.patientDataTableView.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        this.viewPatientButton.setDisable(false);
                        this.bookAppointment.setDisable(false);
                        this.viewPastVisitsButton.setDisable(false);
                        this.manager.setDisplayedPatient(this.patientDataTableView.getSelectionModel().getSelectedIndex());
                    } else {
                        this.searchButton.setDisable(true);
                    }
                });

    }

}

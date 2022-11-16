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

// TODO: Auto-generated Javadoc
/**
 * The Class MainView.
 */
public class MainView {

    /** The manager. */
    private ControllerManager manager = new ControllerManager();
    
    /** The patients. */
    ObservableList<Map<String, Object>> patients = FXCollections.<Map<String, Object>>observableArrayList();

    /** The patient data table view. */
    @FXML
    private TableView<Map> patientDataTableView;

    /** The register patient button. */
    @FXML
    private Button registerPatientButton;

    /** The current date label. */
    @FXML
    private Label currentDateLabel;

    /** The current time label. */
    @FXML
    private Label currentTimeLabel;

    /** The current user label. */
    @FXML
    private Label currentUserLabel;

    /** The patient name search field. */
    @FXML
    private TextField patientNameSearchField;

    /** The patient dob picker. */
    @FXML
    private DatePicker patientDobPicker;

    /** The search button. */
    @FXML
    private Button searchButton;

    /** The view patient button. */
    @FXML
    private Button viewPatientButton;

    /** The logout button. */
    @FXML
    private Button logoutButton;
    
    /** The book appointment. */
    @FXML
    private Button bookAppointment;

    /** The view past visits button. */
    @FXML
    private Button viewPastVisitsButton;

    /**
     * Handle logout.
     *
     * @param event the event
     */
    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }
    
    /**
     * Handle book.
     *
     * @param event the event
     */
    @FXML
    void handleBook(ActionEvent event) {
        this.manager.changeToApptView((Stage) this.bookAppointment.getScene().getWindow());
    }

    /**
     * Handle register.
     *
     * @param event the event
     */
    @FXML
    void handleRegister(ActionEvent event) {
        this.manager.clearDisplayedPatient();
        this.manager.changeToPatientInfoView((Stage) this.registerPatientButton.getScene().getWindow());
    }

    /**
     * Handle search.
     *
     * @param event the event
     */
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

    /**
     * Handle view info.
     *
     * @param event the event
     */
    @FXML
    void handleViewInfo(ActionEvent event) {
        this.manager.changeToPatientInfoView((Stage) this.registerPatientButton.getScene().getWindow());
    }

    /**
     * Handle view past visits.
     *
     * @param event the event
     */
    @FXML
    void handleViewPastVisits(ActionEvent event) {
        this.manager.changeToPastVisitsView((Stage) this.viewPastVisitsButton.getScene().getWindow());
    }

    /**
     * Initialize the mainview.
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

    /**
     * Initialize table view.
     */
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

    /**
     * Patient name listener.
     */
    private void patientNameListener() {
        this.patientNameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank() && this.patientDobPicker.valueProperty() == null) {
                this.searchButton.setDisable(true);
            } else {
                this.searchButton.setDisable(false);
            }
        });
    }

    /**
     * Patient dob listener.
     */
    private void patientDobListener() {
        this.patientDobPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null && this.patientNameSearchField.getText().isBlank()) {
                this.searchButton.setDisable(true);
            } else {
                this.searchButton.setDisable(false);
            }
        });
    }

    /**
     * Table listener.
     */
    private void tableListener() {
        this.patientDataTableView.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        this.viewPatientButton.setDisable(false);
                        this.bookAppointment.setDisable(false);
                        this.viewPastVisitsButton.setDisable(false);
                        this.manager.setDisplayedPatient((int) this.patientDataTableView.getSelectionModel().getSelectedItem().get("id"));
                    } else {
                        this.searchButton.setDisable(true);
                    }
                });

    }

}

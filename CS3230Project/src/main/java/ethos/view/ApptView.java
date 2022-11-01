package main.java.ethos.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;
import main.java.ethos.dal.AppointmentDal;
import main.java.ethos.model.Appointment;

public class ApptView {

    private ControllerManager manager;
    AppointmentDal apptDal;
    ObservableList<Map<String, Object>> appts = FXCollections.<Map<String, Object>>observableArrayList();
    Map<String, Integer> doctors;
    
    

    @FXML
    private TableView<Map> apptDataTableView;

    @FXML
    private DatePicker apptDatePicker;

    @FXML
    private Button bookApptButton;

    @FXML
    private Label currentDateLabel;

    @FXML
    private Label currentUserLabel;

    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private Button editAppt;

    @FXML
    private Button getTimesButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button startVisitButton;

    @FXML
    private ComboBox<LocalTime> timeComboBox;

    @FXML
    private Label bookingLabel;

    @FXML
    void getTimes(ActionEvent event) {

    }

    @FXML
    void handleBook(ActionEvent event) {
        LocalDateTime apptTime = this.apptDatePicker.valueProperty().get().atTime(this.timeComboBox.getValue());
        Label reason = new Label("");
        
        TextInputDialog inputdialog = new TextInputDialog("Enter Reason for Appointment");
        inputdialog.setContentText("Reason: ");
        inputdialog.setHeaderText("Enter Reason");
        inputdialog.showAndWait();
        reason.setText(inputdialog.getEditor().getText());
        this.manager.bookAppt(this.doctors.get(this.doctorComboBox.getSelectionModel().getSelectedItem()), apptTime, reason.getText());
    }

    @FXML
    void handleEditAppt(ActionEvent event) {
        // this.manager.setDisplayedPatient(this.apptDataTableView.getSelectionModel().getSelectedIndex());
        // this.manager.changeToPatientInfoView((Stage)
        // this.registerPatientButton.getScene().getWindow());
    }

    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }

    @FXML
    void handleStartVisit(ActionEvent event) {
        this.manager.changeToVisit((Stage) this.startVisitButton.getScene().getWindow());
    }

    /**
     * Initialize the mainview
     *
     * @param manager the manager
     */
    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.doctors = this.manager.getAllDoctors();
        this.populateDoctors();
        this.currentUserLabel.textProperty()
                .set("Hello, " + manager.getLoggedInName() + " (" + manager.getLoggedInUserName() + ")");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E, M/d/Y");
        this.currentDateLabel.textProperty().set(format.format(calendar.getTime()));
        this.manager.getAllDoctors();
        initializeTableView();
        this.tableListener();
        this.doctorSelectionListener();
        this.bookingListener();
        this.dateListener();
        this.appts.addAll(this.manager.getPatientAppts());
        this.apptDataTableView.getItems().addAll(this.appts);
        
        
    }

    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.apptDataTableView.getColumns().clear();
        TableColumn<Map, String> doctorColumn = new TableColumn<>("Doctor");
        doctorColumn.setCellValueFactory(new MapValueFactory<>("doctor"));
        TableColumn<Map, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new MapValueFactory<>("date"));
        TableColumn<Map, String> timeColumn = new TableColumn<>("time");
        timeColumn.setCellValueFactory(new MapValueFactory<>("time"));
        TableColumn<Map, String> reasonColumn = new TableColumn<>("Reason");
        reasonColumn.setCellValueFactory(new MapValueFactory<>("reason"));
        this.apptDataTableView.getColumns().add(doctorColumn);
        this.apptDataTableView.getColumns().add(dateColumn);
        this.apptDataTableView.getColumns().add(timeColumn);
        this.apptDataTableView.getColumns().add(reasonColumn);
        List<Map<String, Object>> currentResults = this.manager.buildResultsForTable();
        if (currentResults != null) {
            this.appts.addAll(currentResults);
            this.apptDataTableView.getItems().addAll(this.appts);
        }
    }

    private void tableListener() {
        this.apptDataTableView.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    
                    if (newValue != null && this.manager.compareDates((int) newValue)) {
                        this.editAppt.setDisable(false);
                        this.startVisitButton.setDisable(false);
                        
                    } 
     
                });
    }
    
    private void doctorSelectionListener() {
        this.doctorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                    this.timeComboBox.getItems().clear();
                    if (newValue != null && this.apptDatePicker.getValue() != null && this.apptDatePicker.getValue().isAfter(LocalDate.now())) {
                        java.util.Date date = Date.from(this.apptDatePicker.getValue().atStartOfDay( ZoneId.systemDefault()).toInstant());
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        List<LocalTime> times = this.manager.getApptTimes(newValue.toString(), sqlDate);
                        this.timeComboBox.getItems().addAll(times);
                    }
                });
    }
    
    private void dateListener() {
        this.apptDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                    this.timeComboBox.getItems().clear();
                    if (newValue != null && this.doctorComboBox.getValue() != null && newValue.isAfter(LocalDate.now())) {
                        java.sql.Date sqlDate = Date.valueOf(newValue);
                        List<LocalTime> times = this.manager.getApptTimes(this.doctorComboBox.getValue(), sqlDate);
                        this.timeComboBox.getItems().addAll(times);
                    }
                });
    }
    
    private void bookingListener() {
        this.timeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && this.apptDatePicker.getValue() != null && this.apptDatePicker.getValue().isAfter(LocalDate.now())) {
               this.bookApptButton.setDisable(false);
            }
        });
}

    
    
    private void populateDoctors() {
        for (String docName : this.doctors.keySet()) {
            this.doctorComboBox.getItems().add(docName);
        }
    }
}

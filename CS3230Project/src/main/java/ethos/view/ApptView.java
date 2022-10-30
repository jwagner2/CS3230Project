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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

public class ApptView {
    
    private ControllerManager manager;
    ObservableList<Map<String, Object>> appts = FXCollections.<Map<String, Object>>observableArrayList();
    
    @FXML
    private TableView<Appointment> apptDataTableView;

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
    private ComboBox<Date> timeComboBox;
   

    @FXML
    void getTimes(ActionEvent event) {

    }

    @FXML
    void handleBook(ActionEvent event) {

    }

    @FXML
    void handleEditAppt(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }

    @FXML
    void handleStartVisit(ActionEvent event) {

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

        initializeTableView();
    }
    
    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.apptDataTableView.getColumns().clear();
        TableColumn<Map, String> doctorColumn = new TableColumn<>("Doctor");
        doctorColumn.setCellValueFactory(new MapValueFactory<>("doctorName"));
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

}

package main.java.ethos.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

public class PriorVisitView {

    private ControllerManager manager;
    private ObservableList<Map<String, Object>> pastVisits = FXCollections.<Map<String, Object>>observableArrayList();

    @FXML
    private TableView<Map> priorVisitsTableView;

    @FXML
    private Label currentDateLabel;

    @FXML
    private Label currentUserLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button showVisitDetailsButton;

    @FXML
    private Button backButton;

    @FXML
    void goBack(ActionEvent event) {
        this.manager.changeToMainView((Stage) this.backButton.getScene().getWindow());
    }

    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }

    @FXML
    void handleShowDetails(ActionEvent event) {
        int doctorId = this.manager.getDoctorIdForAppt(this.priorVisitsTableView.getSelectionModel().getSelectedIndex());
        this.manager.changeToVisit((Stage) this.showVisitDetailsButton.getScene().getWindow(), doctorId);
    }

    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.currentUserLabel.textProperty()
                .set("Hello, " + manager.getLoggedInName() + " (" + manager.getLoggedInUserName() + ")");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E, M/d/Y");
        this.currentDateLabel.textProperty().set(format.format(calendar.getTime()));
        this.initializeTableView();
        this.resetTable();
        this.pastVisits.addAll(this.manager.getPatientAppts());
        this.priorVisitsTableView.getItems().addAll(this.pastVisits);
    }

    private void resetTable() {
        this.priorVisitsTableView.getItems().clear();
        this.pastVisits.clear();
    }

    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.priorVisitsTableView.getColumns().clear();
        TableColumn<Map, String> doctorColumn = new TableColumn<>("Doctor");
        doctorColumn.setCellValueFactory(new MapValueFactory<>("doctor"));
        TableColumn<Map, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new MapValueFactory<>("date"));
        TableColumn<Map, String> timeColumn = new TableColumn<>("time");
        timeColumn.setCellValueFactory(new MapValueFactory<>("time"));
        this.priorVisitsTableView.getColumns().add(doctorColumn);
        this.priorVisitsTableView.getColumns().add(dateColumn);
        this.priorVisitsTableView.getColumns().add(timeColumn);
        List<Map<String, Object>> currentResults = this.manager.getPatientVisits();
        if (currentResults != null) {
            this.pastVisits.addAll(currentResults);
            this.priorVisitsTableView.getItems().addAll(this.pastVisits);
        }
    }

}

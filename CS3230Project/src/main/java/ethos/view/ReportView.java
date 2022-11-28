package main.java.ethos.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportView.
 */
public class ReportView {

    /** The manager. */
    private ControllerManager manager;

    /** The visits table view. */
    @FXML
    @SuppressWarnings("rawtypes")
    private TableView<Map> visitsTableView;

    /** The current date label. */
    @FXML
    private Label currentDateLabel;

    /** The current user label. */
    @FXML
    private Label currentUserLabel;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The search visits button. */
    @FXML
    private Button searchVisitsButton;

    /** The start date picker. */
    @FXML
    private DatePicker startDatePicker;

    /** The end date picker. */
    @FXML
    private DatePicker endDatePicker;

    /** The back button. */
    @FXML
    private Button backButton;

    /**
     * Handle go back.
     *
     * @param event the event
     */
    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToAdminView((Stage) this.backButton.getScene().getWindow());
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

    /**
     * Handle search.
     *
     * @param event the event
     */
    @FXML
    void handleSearch(ActionEvent event) {
        this.visitsTableView.getItems().addAll(this.manager.getReport(this.startDatePicker.getValue(), this.endDatePicker.getValue()));
        TableColumn<Map, ?> test = this.visitsTableView.getColumns().get(0);
        TableColumn<Map, ?> test2 = this.visitsTableView.getColumns().get(3);
        this.visitsTableView.getSortOrder().add(test);
        this.visitsTableView.getSortOrder().add(test2);
    }

    /**
     * Initialize the admin view.
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
        this.initializeTableView();
    }


 /**
     * Initialize table view.
     */
    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.visitsTableView.getColumns().clear();
        TableColumn<Map, String> visitDateColumn = new TableColumn<>("Visit Date");
        visitDateColumn.setCellValueFactory(new MapValueFactory<>("apptDateTime"));
        TableColumn<Map, String> patientIdColumn = new TableColumn<>("Patient ID");
        patientIdColumn.setCellValueFactory(new MapValueFactory<>("patientId"));
        TableColumn<Map, String> patientFNameColumn = new TableColumn<>("Patient First Name");
        patientFNameColumn.setCellValueFactory(new MapValueFactory<>("patientFName"));
        TableColumn<Map, String> patientLNameColumn = new TableColumn<>("Patient Last Name");
        patientLNameColumn.setCellValueFactory(new MapValueFactory<>("patientLName"));
        TableColumn<Map, String> nurseNameColumn = new TableColumn<>("Nurse Name");
        nurseNameColumn.setCellValueFactory(new MapValueFactory<>("nurseName"));
        TableColumn<Map, String> drNameColumn = new TableColumn<>("Doctor Name");
        drNameColumn.setCellValueFactory(new MapValueFactory<>("doctorName"));
        TableColumn<Map, String> diagnosisColumn = new TableColumn<>("Diagnosis");
        diagnosisColumn.setCellValueFactory(new MapValueFactory<>("diagnosis"));
        TableColumn<Map, String> orderColumn = new TableColumn<>("Labs Ordered");
        orderColumn.setCellValueFactory(new MapValueFactory<>("labOrder"));
        TableColumn<Map, String> resultsColumn = new TableColumn<>("Lab Results");
        resultsColumn.setCellValueFactory(new MapValueFactory<>("labResults"));
        this.visitsTableView.getColumns().add(visitDateColumn);
        this.visitsTableView.getColumns().add(patientIdColumn);
        this.visitsTableView.getColumns().add(patientFNameColumn);
        this.visitsTableView.getColumns().add(patientLNameColumn);
        this.visitsTableView.getColumns().add(nurseNameColumn);
        this.visitsTableView.getColumns().add(drNameColumn);
        this.visitsTableView.getColumns().add(diagnosisColumn);
        this.visitsTableView.getColumns().add(orderColumn);
        this.visitsTableView.getColumns().add(resultsColumn);
        patientLNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        visitDateColumn.setSortType(TableColumn.SortType.ASCENDING);
        this.visitsTableView.getSortOrder().add(visitDateColumn);
        this.visitsTableView.getSortOrder().add(patientLNameColumn);
        //show rest of visit-specific data in a popup dialog rather than smashing it all into a tableview
    }
}

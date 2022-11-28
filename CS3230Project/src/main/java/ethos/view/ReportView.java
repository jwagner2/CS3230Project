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

public class ReportView {

    private ControllerManager manager;

    @FXML
    @SuppressWarnings("rawtypes")
    private TableView<Map> visitsTableView;

    @FXML
    private Label currentDateLabel;

    @FXML
    private Label currentUserLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button searchVisitsButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button backButton;

    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToAdminView((Stage) this.backButton.getScene().getWindow());
    }

    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }

    @FXML
    void handleSearch(ActionEvent event) {
        
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
        this.initializeTableView();
    }


 /**
     * Initialize table view.
     */
    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.visitsTableView.getColumns().clear();
        TableColumn<Map, String> visitDateColumn = new TableColumn<>("Visit Date");
        visitDateColumn.setCellValueFactory(new MapValueFactory<>("visitDate"));
        TableColumn<Map, String> patientIdColumn = new TableColumn<>("Patient ID");
        patientIdColumn.setCellValueFactory(new MapValueFactory<>("patientId"));
        TableColumn<Map, String> patientNameColumn = new TableColumn<>("Name");
        patientNameColumn.setCellValueFactory(new MapValueFactory<>("patientName"));
        this.visitsTableView.getColumns().add(visitDateColumn);
        this.visitsTableView.getColumns().add(patientIdColumn);
        this.visitsTableView.getColumns().add(patientNameColumn);
        //show rest of visit-specific data in a popup dialog rather than smashing it all into a tableview
    }
}

package main.java.ethos.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.MapValueFactory;
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
    @SuppressWarnings("unchecked")
    void handleSubmit(ActionEvent event) {
        this.queryResultsTableView.getColumns().clear();
        Map<String, Object> results = this.manager.submitAdminQuery(this.queryEntryTextArea.getText());
        if (results == null) {
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("");
                alert.setHeaderText("SQL Error");
                alert.setContentText(
                        "There was an issue processing the query you entered.\nPlease check your syntax and try again.");
                alert.showAndWait();
            return;
        }
        List<Map<String, String>> resultSet = (List<Map<String, String>>) results.get("result set");
        if (resultSet != null) {
            this.setTableCols((List<String>) results.get("columns"));
            this.queryResultsTableView.getItems().clear();
            this.queryResultsTableView.getItems().addAll(resultSet);
        } else if ((int)results.get("rows affected") >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Update Result");
                alert.setContentText(
                        "You updated " + (int)results.get("rows affected") + " rows.");
                alert.showAndWait();
        }        
    }

    @SuppressWarnings("rawtypes")
    private void setTableCols(List<String> columns) {
        this.queryResultsTableView.getColumns().clear();
        for (String currColumn : columns) {
            TableColumn<Map, String> firstNameColumn = new TableColumn<>(String.valueOf(currColumn));
            firstNameColumn.setCellValueFactory(new MapValueFactory<>(String.valueOf(currColumn)));
            this.queryResultsTableView.getColumns().add(firstNameColumn);
        }
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
        this.queryResultsTableView.getColumns().clear();
    }

}

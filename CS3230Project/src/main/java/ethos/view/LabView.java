package main.java.ethos.view;

import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

public class LabView {
    ObservableList<Map<String, Object>> visitLabs = FXCollections.<Map<String, Object>>observableArrayList();
    private ControllerManager manager;
    
    @FXML
    private Button backButton;

    @FXML
    private Label currentUserLabel;

    @FXML
    private Label labResultsLabel;

    @FXML
    private TableView<Map> labResultsTable;

    @FXML
    private Button logoutButton;

    @FXML
    private ComboBox<String> visitComboBox;

    @FXML
    void handleGetLabs(ActionEvent event) {

    }

    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToMainView((Stage) this.backButton.getScene().getWindow());
    }

    @FXML
    void handleLogout(ActionEvent event) {
        this.manager.changeToLogin((Stage) this.logoutButton.getScene().getWindow());
    }

    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.visitLabs.addAll(this.manager.getVisitLabs());
        this.initializeTableView();
        this.currentUserLabel.textProperty().set("Hello, " + manager.getLoggedInName() + " (" + manager.getLoggedInUserName() + ")");
        this.labResultsLabel.textProperty().set("Lab Results For " + manager.getPatientFirstName() + " " + manager.getPatientLastName());
    }

    private void initializeTableView() {
        this.labResultsTable.getColumns().clear();
        TableColumn<Map, String> testName = new TableColumn<>("Test Name");
        testName.setCellValueFactory(new MapValueFactory<>("testName"));
        TableColumn<Map, String> results = new TableColumn<>("Results");
        results.setCellValueFactory(new MapValueFactory<>("testResults"));
        TableColumn<Map, String> abnormal = new TableColumn<>("isAbnormal");
        abnormal.setCellValueFactory(new MapValueFactory<>("isAbnormal"));
        this.labResultsTable.getColumns().add(testName);
        this.labResultsTable.getColumns().add(results);
        this.labResultsTable.getColumns().add(abnormal);
        List<Map<String, Object>> currentResults = this.manager.buildResultsForTable();
        if (currentResults != null) {
            this.visitLabs.addAll(currentResults);
            this.labResultsTable.getItems().addAll(this.visitLabs);
        }
        
    }
}

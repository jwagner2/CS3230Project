package main.java.ethos.view;

import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

// TODO: Auto-generated Javadoc
/**
 * The Class LabView.
 */
public class LabView {

    /** The visit labs. */
    ObservableList<Map<String, Object>> visitLabs = FXCollections.<Map<String, Object>>observableArrayList();

    /** The manager. */
    private ControllerManager manager;

    /** The back button. */
    @FXML
    private Button backButton;

    /** The current user label. */
    @FXML
    private Label currentUserLabel;

    /** The lab results label. */
    @FXML
    private Label labResultsLabel;

    /** The lab results table. */
    @FXML
    @SuppressWarnings("rawtypes")
    private TableView<Map> labResultsTable;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The enter result button. */
    @FXML
    private Button enterResultButton;

    /**
     * Handle enter result.
     *
     * @param event the event
     */
    @FXML
    void handleEnterResult(ActionEvent event) {
        String description = (String) this.labResultsTable.getSelectionModel().getSelectedItem().get("testResults");
        String testName = (String) this.labResultsTable.getSelectionModel().getSelectedItem().get("testName");
        Boolean isAbnormal = (Boolean) this.labResultsTable.getSelectionModel().getSelectedItem().get("isAbnormal");
        TextInputDialog dialog = new TextInputDialog(description);

        dialog.setTitle(
                "Enter Test Results for " + testName);
        dialog.setHeaderText("Enter Results:");
        dialog.setContentText(
                "Enter Test Results for " + testName);
        CheckBox isAbnormalBox = new CheckBox("Is Result Abnormal?");
        isAbnormalBox.selectedProperty().set(isAbnormal);
        dialog.getDialogPane().setExpandableContent(isAbnormalBox);
        dialog.getDialogPane().setExpanded(true);
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            this.manager.enterTestResult(result.get(), isAbnormalBox.isSelected(),
                    testName);
            this.visitLabs.clear();
            this.visitLabs.addAll(this.manager.getVisitLabs());
            this.labResultsTable.getItems().clear();
            this.labResultsTable.getItems().addAll(this.visitLabs);
        });
    }

    /**
     * Handle go back.
     *
     * @param event the event
     */
    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToVisit((Stage) this.backButton.getScene().getWindow(), this.manager.getVisitDr(),
                this.manager.getVisitDateTime());
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
     * Initialize.
     *
     * @param manager the manager
     */
    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.visitLabs.addAll(this.manager.getVisitLabs());
        this.initializeTableView();
        this.currentUserLabel.textProperty()
                .set("Hello, " + manager.getLoggedInName() + " (" + manager.getLoggedInUserName() + ")");
        this.labResultsLabel.textProperty()
                .set("Lab Results For " + manager.getPatientFirstName() + " " + manager.getPatientLastName());
        if (this.manager.isVisitFinal()) {
            this.enterResultButton.setDisable(true);
        } else {
            this.addTableListener();
        }

    }

    /**
     * Initialize table view.
     */
    @SuppressWarnings("rawtypes")
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
        this.labResultsTable.getItems().clear();
        this.labResultsTable.getItems().clear();
        this.labResultsTable.getItems().addAll(this.visitLabs);

    }

    /**
     * Adds the table listener.
     */
    private void addTableListener() {
        this.labResultsTable.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        this.enterResultButton.setDisable(false);

                    } else {
                        this.enterResultButton.setDisable(true);
                    }
                });
    }
}

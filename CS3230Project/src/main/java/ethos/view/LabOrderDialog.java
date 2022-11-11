package main.java.ethos.view;

import java.util.ArrayList;
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
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.MapValueFactory;
import main.java.ethos.controller.ControllerManager;

public class LabOrderDialog {
    ObservableList<Map<String, Object>> currentLabs = FXCollections.<Map<String, Object>>observableArrayList();
    private ControllerManager manager;

    @FXML
    private Button cancelOrderButton;

    @FXML
    private TableView<Map> availableLabsTable;

    @FXML
    private Label orderLabsLabel;

    @FXML
    private Button setOrderButton;
    

    /**
     * Initialize.
     *
     * @param manager the manager
     */
    public void initialize(ControllerManager manager) {
        this.manager = manager;
        
    }

    @FXML
    void cancelOrderDialog(ActionEvent event) {

    }

    @FXML
    void setOrder(ActionEvent event) {

    }
    
    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.availableLabsTable.getColumns().clear();
        TableColumn<Map, String> testName = new TableColumn<>("Test Name");
        testName.setCellValueFactory(new MapValueFactory<>("testName"));
        TableColumn<Map, String> description = new TableColumn<>("description");
        description.setCellValueFactory(new MapValueFactory<>("testDescription"));
        this.availableLabsTable.getColumns().add(testName);
        this.availableLabsTable.getColumns().add(description);
        List<Map<String, Object>> currentResults = this.manager.buildResultsForTable();
        if (currentResults != null) {
            this.currentLabs.addAll(currentResults);
            this.availableLabsTable.getItems().addAll(this.currentLabs);
        }
    }

}

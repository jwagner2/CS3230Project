package main.java.ethos.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

// TODO: Auto-generated Javadoc
/**
 * The Class LabOrderDialog.
 */
public class LabOrderDialog {

    /** The current labs. */
    ObservableList<Map<String, Object>> currentLabs = FXCollections.<Map<String, Object>>observableArrayList();

    /** The manager. */
    private ControllerManager manager;

    /** The cancel order button. */
    @FXML
    private Button cancelOrderButton;

    /** The available labs table. */
    @SuppressWarnings("rawtypes")
    @FXML
    private TableView<Map> availableLabsTable;

    /** The order labs label. */
    @FXML
    private Label orderLabsLabel;

    /** The set order button. */
    @FXML
    private Button setOrderButton;

    /**
     * Initialize.
     *
     * @param manager the manager
     */
    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.availableLabsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.currentLabs.addAll(this.manager.getAvailableLabs());
        this.initializeTableView();
        this.addTableListener();


    }

    /**
     * Cancel order dialog.
     *
     * @param event the event
     */
    @FXML
    void cancelOrderDialog(ActionEvent event) {
        Stage stage = (Stage) this.cancelOrderButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Sets the order.
     *
     * @param event the new order
     */
    @FXML
    void setOrder(ActionEvent event) {

        this.manager.setLabOrder(this.availableLabsTable.getSelectionModel().getSelectedItems());
        Stage stage = (Stage) this.cancelOrderButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Initialize table view.
     */
    @SuppressWarnings("rawtypes")
    private void initializeTableView() {
        this.availableLabsTable.getColumns().clear();
        TableColumn<Map, String> testName = new TableColumn<>("Test Name");
        testName.setCellValueFactory(new MapValueFactory<>("testName"));
        testName.setSortable(false);
        TableColumn<Map, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(new MapValueFactory<>("testDescription"));
        description.setSortable(false);
        this.availableLabsTable.getColumns().add(testName);
        this.availableLabsTable.getColumns().add(description);
        this.availableLabsTable.getItems().addAll(this.currentLabs);
        
    }

    /**
     * Adds the table listener.
     */
    private void addTableListener() {
        this.availableLabsTable.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        this.setOrderButton.disableProperty().set(false);

                    } else {
                        this.setOrderButton.disableProperty().set(true);
                    }
                });
    }

}

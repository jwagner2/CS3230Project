package main.java.ethos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class VisitView {

    @FXML
    private Button backButton;

    @FXML
    private Label currentPatientField;

    @FXML
    private Label currentUserField;

    @FXML
    private TextField diastolicField;

    @FXML
    private Button endVisit;

    @FXML
    private Label invalidDataLabel;

    @FXML
    private ComboBox<?> labSelectionBox;

    @FXML
    private ListView<?> labsToOrderBox;

    @FXML
    private TextField pulseField;

    @FXML
    private TextArea symptomsTextArea;

    @FXML
    private TextField systolicField;

    @FXML
    private TextField tempField;

    @FXML
    private Button viewResults;

    @FXML
    private TextField weightField;

    @FXML
    void handleEnd(ActionEvent event) {

    }

    @FXML
    void handleGoBack(ActionEvent event) {

    }

    @FXML
    void handleViewResults(ActionEvent event) {

    }

}

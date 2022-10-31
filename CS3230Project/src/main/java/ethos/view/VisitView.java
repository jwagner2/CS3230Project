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
    private TextArea diagnosisTextArea;

    @FXML
    private TextField diastolicField;

    @FXML
    private Button endVisit;

    @FXML
    private TextField heightField;

    @FXML
    private Label heightLabel;

    @FXML
    private Label invalidDataLabel;

    @FXML
    private Label invalidDiaLabel;

    @FXML
    private Label invalidDiagLabel;

    @FXML
    private Label invalidHeightLabel;

    @FXML
    private Label invalidPulseLabel;

    @FXML
    private Label invalidSympLabel;

    @FXML
    private Label invalidSysLabel;

    @FXML
    private Label invalidTempLabel;

    @FXML
    private Label invalidWeightLabel;

    @FXML
    private ListView<?> labsToOrderBox;

    @FXML
    private Button orderLabButton;

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
    void handleOrder(ActionEvent event) {

    }
    @FXML
    void handleViewResults(ActionEvent event) {

    }

}

package main.java.ethos.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;


public class VisitView {

    private Map<String,String> visitDetails;
    ControllerManager manager;
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
    private Button backButton;

    @FXML
    void handleEnd(ActionEvent event) {
        this.populateMap();
        this.resetInvalidLabels();
        List<String> invalidInputs = this.manager.validateVitals(this.visitDetails);
        if (invalidInputs.size() == 0) {
            if (this.manager.endVisit(this.visitDetails) {
                this.manager.changeToMainView((Stage) this.endVisit.getScene().getWindow());
            }
        } else {
            this.invalidDataLabel.setVisible(true);
            this.showInvalidLabels(invalidInputs);
        }
    }

    private void showInvalidLabels(List<String> invalidInputs) {
        for (String result : invalidInputs) {
            if (result.equals("systolic")) {
                this.systolicField.setStyle("-fx-text-box-border:red");
                this.invalidSysLabel.setVisible(true);
            } else if (result.equals("diastolic")) {
                this.diastolicField.setStyle("-fx-text-box-border:red");
                this.invalidDiaLabel.setVisible(true);
            } else if (result.equals("pulse")) {
                this.pulseField.setStyle("-fx-text-box-border:red");
                this.invalidPulseLabel.setVisible(true);
            } else if (result.equals("weight")) {
                this.weightField.setStyle("-fx-text-box-border:red");
                this.invalidWeightLabel.setVisible(true);
            } else if (result.equals("temperature")) {
                this.tempField.setStyle("-fx-text-box-border:red");
                this.invalidTempLabel.setVisible(true);
            } else if (result.equals("height")) {
                this.heightField.setStyle("-fx-text-box-border:red");
                this.invalidHeightLabel.setVisible(true);
            }
        }
    }

    private void resetInvalidLabels() {
        this.systolicField.setStyle("-fx-border-width: 0px");
        this.invalidSysLabel.setVisible(false);

        this.diastolicField.setStyle("-fx-border-width: 0px");
        this.invalidDiaLabel.setVisible(false);

        this.pulseField.setStyle("-fx-border-width: 0px");
        this.invalidPulseLabel.setVisible(false);

        this.weightField.setStyle("-fx-border-width: 0px");
        this.invalidWeightLabel.setVisible(false);

        this.tempField.setStyle("--fx-border-width: 0px");
        this.invalidTempLabel.setVisible(false);

        this.heightField.setStyle("-fx-border-width: 0px");
        this.invalidHeightLabel.setVisible(false);
        
        this.invalidDataLabel.setVisible(false);

    }
    private void populateMap() {
        this.invalidDataLabel.setVisible(false);
        try {
            this.visitDetails.put("systolic", this.systolicField.getText());
            this.visitDetails.put("diastolic", this.diastolicField.getText());
            this.visitDetails.put("weight", this.weightField.getText());
            this.visitDetails.put("pulse", this.pulseField.getText());
            this.visitDetails.put("temperature", this.tempField.getText());
            this.visitDetails.put("height", this.heightField.getText());
        } catch (Exception e) {
            this.invalidDataLabel.setVisible(true);
        }

    }

    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToMainView((Stage) this.backButton.getScene().getWindow());
    }
    
    @FXML
    void handleOrder(ActionEvent event) {

    }
    @FXML
    void handleViewResults(ActionEvent event) {

    }

    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.visitDetails = new HashMap<String, String>();
        
    }

}

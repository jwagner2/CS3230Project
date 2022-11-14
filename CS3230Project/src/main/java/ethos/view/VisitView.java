package main.java.ethos.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

public class VisitView {

    private ControllerManager manager;
    private List<TextInputControl> editableControls = new ArrayList<TextInputControl>();
    private Map<String, String> visitDetails = new HashMap<String, String>();
    ObservableList<String> labsToOrder = FXCollections.<String>observableArrayList();
    private int doctorId;
    private LocalDateTime appDateTime;
    private boolean readOnly;


    @FXML
    private Button backButton;

    @FXML
    private Label currentPatientField;
    
    @FXML
    private Label drNameLabel;

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
    private ListView<String> labsToOrderBox;

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
    private CheckBox finalDiagnosisChkBx;

    @FXML
    void handleEnd(ActionEvent event) {
        if (!this.readOnly) {
            this.populateMap();
            this.resetInvalidLabels();
            List<String> invalidInputs = this.manager.validateVisitInfo(this.visitDetails);
            if (invalidInputs.size() == 0) {
                if (this.manager.enterVisitInfo(this.visitDetails)) {
                    this.manager.changeToMainView((Stage) this.endVisit.getScene().getWindow());
                }
            } else {
                this.invalidDataLabel.setVisible(true);
                this.showInvalidLabels(invalidInputs);
            }
        } else {
            if (this.finalDiagnosisChkBx.isSelected()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirm final diagnosis");
                alert.setContentText("You are about to sumbit the final diagnosis.\nThis action cannot be undone.\nDo you wish to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.CANCEL) {
                    return;
                }
            }
            this.manager.updateDiagnosis(this.doctorId, this.appDateTime, this.diagnosisTextArea.getText(), this.finalDiagnosisChkBx.isSelected());
            this.manager.changeToMainView((Stage) this.endVisit.getScene().getWindow());
        }
    }

    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToMainView((Stage) this.backButton.getScene().getWindow());
    }

    @FXML
    void handleOrder(ActionEvent event) {
         this.manager.clearLabOrder();
         this.labsToOrder.clear();
         this.manager.launchLabDialog((Stage) this.orderLabButton.getScene().getWindow());
         this.labsToOrder.addAll(this.manager.getCurrentOrderNames());
         this.labsToOrderBox.setItems(this.labsToOrder);
        
    }

    @FXML
    void handleViewResults(ActionEvent event) {
        // lab result viewing to come later
    }

    public void initialize(ControllerManager manager, int doctorId, LocalDateTime appDateTime) {
        this.manager = manager;
        this.doctorId = doctorId;
        this.appDateTime = appDateTime;
        this.readOnly = false;
        this.currentPatientField.textProperty().set("Patient: " + this.manager.getPatientFirstName() + " " + this.manager.getPatientLastName());
        this.drNameLabel.textProperty().set("Attending Physician: " + this.manager.getDoctorName(doctorId));
        this.currentUserField.textProperty().set(this.manager.getLoggedInName() + " (" + this.manager.getLoggedInUserName() + ")");
        this.invalidDataLabel.disableProperty().set(true);
        this.addEditableControls();
        this.enableControls();
        if (this.manager.visitExists(doctorId, appDateTime)) {
            this.populateFields();
            this.readOnly = true;
            this.disableInputs();
        }
    }

    private void enableControls() {
        this.orderLabButton.disableProperty().set(false);
        for (TextInputControl control : this.editableControls) {
            control.setEditable(true);
        }
    }

    private void addEditableControls() {
        this.editableControls.add(this.systolicField);
        this.editableControls.add(this.diastolicField);
        this.editableControls.add(this.weightField);
        this.editableControls.add(this.tempField);
        this.editableControls.add(this.heightField);
        this.editableControls.add(this.pulseField);
        this.editableControls.add(this.symptomsTextArea);
        this.editableControls.add(this.diagnosisTextArea);
    }

    private void populateMap() {
        this.invalidDataLabel.setVisible(false);
        try {
            this.visitDetails.put("systolic", this.systolicField.getText());
            this.visitDetails.put("diastolic", this.diastolicField.getText());
            this.visitDetails.put("weight", this.weightField.getText());
            this.visitDetails.put("temperature", this.tempField.getText());
            this.visitDetails.put("height", this.heightField.getText());
            this.visitDetails.put("pulse", this.pulseField.getText());
            this.visitDetails.put("symptoms", this.symptomsTextArea.getText());
            this.visitDetails.put("diagnosis", this.diagnosisTextArea.getText());
            this.visitDetails.put("doctorId", String.valueOf(this.doctorId));
            this.visitDetails.put("apptDatetime", this.appDateTime.toString());
            this.visitDetails.put("isFinal", String.valueOf(this.finalDiagnosisChkBx.isSelected()));
            // add lab order later
        } catch (Exception e) {
            this.invalidDataLabel.setVisible(true);
        }
    }

    private void resetInvalidLabels() {
        this.systolicField.setStyle("-fx-border-width: 0px");
        this.invalidSysLabel.setVisible(false);

        this.diastolicField.setStyle("-fx-border-width: 0px");
        this.invalidDiaLabel.setVisible(false);

        this.weightField.setStyle("-fx-border-width: 0px");
        this.invalidWeightLabel.setVisible(false);

        this.tempField.setStyle("-fx-border-width: 0px");
        this.invalidTempLabel.setVisible(false);

        this.heightField.setStyle("-fx-border-width: 0px");
        this.invalidHeightLabel.setVisible(false);

        this.pulseField.setStyle("-fx-border-width: 0px");
        this.invalidPulseLabel.setVisible(false);
    }

    private void showInvalidLabels(List<String> invalidInputs) {
        for (String result : invalidInputs) {
            if (result.equals("systolic")) {
                this.systolicField.setStyle("-fx-text-box-border:red");
                this.invalidSysLabel.setVisible(true);
            } else if (result.equals("diastolic")) {
                this.diastolicField.setStyle("-fx-text-box-border:red");
                this.invalidDiaLabel.setVisible(true);
            } else if (result.equals("weight")) {
                this.weightField.setStyle("-fx-text-box-border:red");
                this.invalidWeightLabel.setVisible(true);
            } else if (result.equals("temperature")) {
                this.tempField.setStyle("-fx-text-box-border:red");
                this.invalidTempLabel.setVisible(true);
            } else if (result.equals("height")) {
                this.heightField.setStyle("-fx-text-box-border:red");
                this.invalidHeightLabel.setVisible(true);
            } else if (result.equals("pulse")) {
                this.pulseField.setStyle("-fx-text-box-border:red");
                this.invalidPulseLabel.setVisible(true);
            }
        }
    }

    private void populateFields() {
        Map<String, String> visitInfo = this.manager.getVisitInfo(this.doctorId, this.appDateTime);
        this.systolicField.textProperty().set(visitInfo.get("systolic"));
        this.diastolicField.textProperty().set(visitInfo.get("diastolic"));
        this.weightField.textProperty().set(visitInfo.get("weight"));
        this.tempField.textProperty().set(visitInfo.get("temperature"));
        this.heightField.textProperty().set(visitInfo.get("height"));
        this.pulseField.textProperty().set(visitInfo.get("pulse"));
        this.symptomsTextArea.textProperty().set(visitInfo.get("symptoms"));
        this.diagnosisTextArea.textProperty().set(visitInfo.get("diagnosis"));
        this.finalDiagnosisChkBx.selectedProperty().set(Boolean.parseBoolean(visitInfo.get("isFinal")));
    }

    //doesn't disable diagnosis field
    private void disableInputs() {
        this.systolicField.disableProperty().set(true);
        this.diastolicField.disableProperty().set(true);
        this.weightField.disableProperty().set(true);
        this.tempField.disableProperty().set(true);
        this.heightField.disableProperty().set(true);
        this.pulseField.disableProperty().set(true);
        this.symptomsTextArea.disableProperty().set(true);

        this.systolicField.setStyle("-fx-opacity:0.8");
        this.diastolicField.setStyle("-fx-opacity:0.8");
        this.weightField.setStyle("-fx-opacity:0.8");
        this.tempField.setStyle("-fx-opacity:0.8");
        this.heightField.setStyle("-fx-opacity:0.8");
        this.pulseField.setStyle("-fx-opacity:0.8");
        this.symptomsTextArea.setStyle("-fx-opacity:0.8");

        if (this.finalDiagnosisChkBx.isSelected()) {
            this.finalDiagnosisChkBx.disableProperty().set(true);
            this.diagnosisTextArea.disableProperty().set(true);
            this.diagnosisTextArea.setStyle("-fx-opacity:0.8");
            this.endVisit.disableProperty().set(true);
        }
    }
}

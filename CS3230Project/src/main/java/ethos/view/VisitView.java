package main.java.ethos.view;

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
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

// TODO: Auto-generated Javadoc
/**
 * The Class VisitView.
 */
public class VisitView {

    /** The manager. */
    private ControllerManager manager;

    /** The editable controls. */
    private List<TextInputControl> editableControls = new ArrayList<TextInputControl>();

    /** The visit details. */
    private Map<String, String> visitDetails = new HashMap<String, String>();

    /** The labs to order. */
    ObservableList<String> labsToOrder = FXCollections.<String>observableArrayList();

    /** The doctor id. */
    private int doctorId;

    /** The app date time. */
    private LocalDateTime appDateTime;

    /** The read only. */
    private boolean readOnly;

    /** The back button. */
    @FXML
    private Button backButton;

    /** The current patient field. */
    @FXML
    private Label currentPatientField;

    /** The dr name label. */
    @FXML
    private Label drNameLabel;

    /** The current user field. */
    @FXML
    private Label currentUserField;

    /** The diagnosis text area. */
    @FXML
    private TextArea diagnosisTextArea;

    /** The diastolic field. */
    @FXML
    private TextField diastolicField;

    /** The end visit. */
    @FXML
    private Button endVisit;

    /** The height field. */
    @FXML
    private TextField heightField;

    /** The height label. */
    @FXML
    private Label heightLabel;

    /** The invalid data label. */
    @FXML
    private Label invalidDataLabel;

    /** The invalid dia label. */
    @FXML
    private Label invalidDiaLabel;

    /** The invalid diag label. */
    @FXML
    private Label invalidDiagLabel;

    /** The invalid height label. */
    @FXML
    private Label invalidHeightLabel;

    /** The invalid pulse label. */
    @FXML
    private Label invalidPulseLabel;

    /** The invalid symp label. */
    @FXML
    private Label invalidSympLabel;

    /** The invalid sys label. */
    @FXML
    private Label invalidSysLabel;

    /** The invalid temp label. */
    @FXML
    private Label invalidTempLabel;

    /** The invalid weight label. */
    @FXML
    private Label invalidWeightLabel;

    /** The labs to order box. */
    @FXML
    private ListView<String> labsToOrderBox;

    /** The order lab button. */
    @FXML
    private Button orderLabButton;

    /** The pulse field. */
    @FXML
    private TextField pulseField;

    /** The symptoms text area. */
    @FXML
    private TextArea symptomsTextArea;

    /** The systolic field. */
    @FXML
    private TextField systolicField;

    /** The temp field. */
    @FXML
    private TextField tempField;

    /** The view results. */
    @FXML
    private Button viewResults;

    /** The weight field. */
    @FXML
    private TextField weightField;

    /** The final diagnosis chk bx. */
    @FXML
    private CheckBox finalDiagnosisChkBx;

    /**
     * Handle end.
     *
     * @param event the event
     */
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
                alert.setContentText(
                        "You are about to sumbit the final diagnosis.\nThis action cannot be undone.\nDo you wish to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.CANCEL) {
                    return;
                }
            }
            this.manager.updateDiagnosis(this.doctorId, this.appDateTime, this.diagnosisTextArea.getText(),
                    this.finalDiagnosisChkBx.isSelected());
            this.manager.changeToMainView((Stage) this.endVisit.getScene().getWindow());
        }
    }

    /**
     * Handle go back.
     *
     * @param event the event
     */
    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToMainView((Stage) this.backButton.getScene().getWindow());
    }

    /**
     * Handle order.
     *
     * @param event the event
     */
    @FXML
    void handleOrder(ActionEvent event) {
        this.manager.clearLabOrder();
        this.labsToOrder.clear();
        this.manager.launchLabDialog((Stage) this.orderLabButton.getScene().getWindow());
        this.labsToOrder.addAll(this.manager.getCurrentOrderNames());
        this.labsToOrderBox.setItems(this.labsToOrder);
    }

    /**
     * Handle view results.
     *
     * @param event the event
     */
    @FXML
    void handleViewResults(ActionEvent event) {
        this.manager.changeToLabView((Stage) this.viewResults.getScene().getWindow());
    }

    /**
     * Initialize.
     *
     * @param manager     the manager
     * @param doctorId    the doctor id
     * @param appDateTime the app date time
     */
    public void initialize(ControllerManager manager, int doctorId, LocalDateTime appDateTime) {
        this.manager = manager;
        this.doctorId = doctorId;
        this.appDateTime = appDateTime;
        this.readOnly = false;
        this.currentPatientField.textProperty()
                .set("Patient: " + this.manager.getPatientFirstName() + " " + this.manager.getPatientLastName());
        this.drNameLabel.textProperty().set("Attending Physician: " + this.manager.getDoctorName(doctorId));
        this.currentUserField.textProperty()
                .set(this.manager.getLoggedInName() + " (" + this.manager.getLoggedInUserName() + ")");
        this.invalidDataLabel.disableProperty().set(true);
        this.addEditableControls();
        this.enableControls();
        if (this.manager.visitExists(doctorId, appDateTime)) {
            this.populateFields();
            this.readOnly = true;
            this.disableInputs();
            this.orderLabButton.disableProperty().set(true);
            this.viewResults.disableProperty().set(false);
        }
    }

    /**
     * Enable controls.
     */
    private void enableControls() {
        this.orderLabButton.disableProperty().set(false);
        this.viewResults.disableProperty().set(true);
        for (TextInputControl control : this.editableControls) {
            control.setEditable(true);
        }
    }

    /**
     * Adds the editable controls.
     */
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

    /**
     * Populate map.
     */
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

    /**
     * Reset invalid labels.
     */
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

    /**
     * Show invalid labels.
     *
     * @param invalidInputs the invalid inputs
     */
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

    /**
     * Populate fields.
     */
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

        this.labsToOrder.clear();
        List<String> labs = new ArrayList<String>();
        for (Map<String, Object> currLab : this.manager.getVisitLabs()) {
            labs.add((String) currLab.get("testName"));
        }
        this.labsToOrder.setAll(labs);
        this.labsToOrderBox.setItems(this.labsToOrder);
    }

    /**
     * Disable inputs.
     */
    // doesn't disable diagnosis field
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

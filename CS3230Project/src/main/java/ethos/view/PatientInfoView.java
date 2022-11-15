package main.java.ethos.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientInfoView.
 */
public class PatientInfoView {

    /** The manager. */
    private ControllerManager manager;
    
    /** The patient details. */
    private Map<String, String> patientDetails = new HashMap<String, String>();
    
    /** The editable controls. */
    private List<TextInputControl> editableControls;

    /** The a 1 invalid label. */
    @FXML
    private Label a1InvalidLabel;

    /** The a 2 invalid label. */
    @FXML
    private Label a2InvalidLabel;

    /** The back button. */
    @FXML
    private Button backButton;

    /** The current user field. */
    @FXML
    private Label currentUserField;

    /** The dob invalid label. */
    @FXML
    private Label dobInvalidLabel;

    /** The edit button. */
    @FXML
    private Button editButton;

    /** The fname invalid label. */
    @FXML
    private Label fnameInvalidLabel;

    /** The gender invalid label. */
    @FXML
    private Label genderInvalidLabel;

    /** The invalid data label. */
    @FXML
    private Label invalidDataLabel;

    /** The lname invalid label. */
    @FXML
    private Label lnameInvalidLabel;

    /** The patient active ch box. */
    @FXML
    private CheckBox patientActiveChBox;

    /** The patient addr 1 field. */
    @FXML
    private TextField patientAddr1Field;

    /** The patient addr 2 field. */
    @FXML
    private TextField patientAddr2Field;

    /** The patient addr state combo box. */
    @FXML
    private ComboBox<String> patientAddrStateComboBox;

    /** The patient addr zipcode field. */
    @FXML
    private TextField patientAddrZipcodeField;

    /** The patient dob picker. */
    @FXML
    private DatePicker patientDobPicker;

    /** The patient fname field. */
    @FXML
    private TextField patientFnameField;

    /** The patient gender combo box. */
    @FXML
    private ComboBox<String> patientGenderComboBox;

    /** The patient lname field. */
    @FXML
    private TextField patientLnameField;

    /** The patient phone field. */
    @FXML
    private TextField patientPhoneField;

    /** The patient ssn field. */
    @FXML
    private TextField patientSsnField;

    /** The phone invalid label. */
    @FXML
    private Label phoneInvalidLabel;

    /** The ssn invalid label. */
    @FXML
    private Label ssnInvalidLabel;

    /** The state invalid label. */
    @FXML
    private Label stateInvalidLabel;

    /** The submit button. */
    @FXML
    private Button submitButton;

    /** The zip invalid label. */
    @FXML
    private Label zipInvalidLabel;

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
     * Enable editing.
     *
     * @param event the event
     */
    @FXML
    void enableEditing(ActionEvent event) {
        this.enableControls();
    }

    /**
     * Handle submit.
     *
     * @param event the event
     */
    @FXML
    void handleSubmit(ActionEvent event) {
        this.populateMap();
        this.resetInvalidLabels();
        List<String> invalidInputs = this.manager.validatePatientInfo(this.patientDetails);
        if (invalidInputs.size() == 0) {
            if (this.manager.patientRegister(this.patientDetails, this.patientActiveChBox.isSelected())) {
                this.manager.changeToMainView((Stage) this.submitButton.getScene().getWindow());
            }
        } else {
            this.invalidDataLabel.setVisible(true);
            this.showInvalidLabels(invalidInputs);
        }
    }

    /**
     * Show invalid labels.
     *
     * @param invalidInputs the invalid inputs
     */
    private void showInvalidLabels(List<String> invalidInputs) {
        for (String result : invalidInputs) {
            if (result.equals("fname")) {
                this.patientFnameField.setStyle("-fx-text-box-border:red");
                this.fnameInvalidLabel.setVisible(true);
            } else if (result.equals("lname")) {
                this.patientLnameField.setStyle("-fx-text-box-border:red");
                this.lnameInvalidLabel.setVisible(true);
            } else if (result.equals("ssn")) {
                this.patientSsnField.setStyle("-fx-text-box-border:red");
                this.ssnInvalidLabel.setVisible(true);
            } else if (result.equals("dob")) {
                this.patientDobPicker.setStyle("-fx-text-box-border:red");
                this.dobInvalidLabel.setVisible(true);
            } else if (result.equals("phone")) {
                this.patientPhoneField.setStyle("-fx-text-box-border:red");
                this.phoneInvalidLabel.setVisible(true);
            } else if (result.equals("addressOne")) {
                this.patientAddr1Field.setStyle("-fx-text-box-border:red");
                this.a1InvalidLabel.setVisible(true);
            } else if (result.equals("addressTwo")) {
                this.patientAddr2Field.setStyle("-fx-text-box-border:red");
                this.a2InvalidLabel.setVisible(true);
            } else if (result.equals("zip")) {
                this.patientAddrZipcodeField.setStyle("-fx-text-box-border:red");
                this.zipInvalidLabel.setVisible(true);
            } else if (result.equals("state")) {
                this.patientAddrStateComboBox.setStyle("-fx-text-box-border:red");
                this.stateInvalidLabel.setVisible(true);
            } else if (result.equals("gender")) {
                this.patientGenderComboBox.setStyle("-fx-text-box-border:red");
                this.genderInvalidLabel.setVisible(true);
            }
        }
    }

    /**
     * Reset invalid labels.
     */
    private void resetInvalidLabels() {
        this.patientFnameField.setStyle("-fx-border-width: 0px");
        this.fnameInvalidLabel.setVisible(false);

        this.patientLnameField.setStyle("-fx-border-width: 0px");
        this.lnameInvalidLabel.setVisible(false);

        this.patientSsnField.setStyle("-fx-border-width: 0px");
        this.ssnInvalidLabel.setVisible(false);

        this.patientDobPicker.setStyle("-fx-border-width: 0px");
        this.dobInvalidLabel.setVisible(false);

        this.patientPhoneField.setStyle("--fx-border-width: 0px");
        this.phoneInvalidLabel.setVisible(false);

        this.patientAddr1Field.setStyle("-fx-border-width: 0px");
        this.a1InvalidLabel.setVisible(false);

        this.patientAddr2Field.setStyle("-fx-border-width: 0px");
        this.a2InvalidLabel.setVisible(false);

        this.patientAddrZipcodeField.setStyle("-fx-border-width: 0px");
        this.zipInvalidLabel.setVisible(false);

        this.patientAddrStateComboBox.setStyle("-fx-border-width: 0px");
        this.stateInvalidLabel.setVisible(false);

        this.patientGenderComboBox.setStyle("-fx-border-width: 0px");
        this.genderInvalidLabel.setVisible(false);

    }

    /**
     * Initialize.
     *
     * @param manager the manager
     */
    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.manager.populateStatesComboBox(this.patientAddrStateComboBox);
        this.patientGenderComboBox.getItems().addAll("M", "F");
        this.currentUserField.textProperty()
                .set(manager.getLoggedInName() + " (" + manager.getLoggedInUserName() + ")");
        this.editableControls = new ArrayList<TextInputControl>();
        this.addEditableControls();
        if (manager.hasSelectedPatient()) {
            this.patientActiveChBox.disableProperty().set(true);
            this.patientActiveChBox.setStyle("-fx-opacity:1");
            this.invalidDataLabel.setVisible(false);
            this.populateFields();
        } else {
            this.editButton.disableProperty().set(true);
            this.invalidDataLabel.setVisible(false);
            this.enableControls();
        }
    }

    /**
     * Enable controls.
     */
    public void enableControls() {
        if (!this.patientFnameField.editableProperty().get()) {
            this.patientDobPicker.disableProperty().set(false);
            this.patientGenderComboBox.disableProperty().set(false);
            this.patientAddrStateComboBox.disableProperty().set(false);
            this.patientActiveChBox.disableProperty().set(false);
            for (TextInputControl control : this.editableControls) {
                control.setEditable(true);
            }
        }
    }

    /**
     * Adds the editable controls.
     */
    private void addEditableControls() {
        this.editableControls.add(this.patientFnameField);
        this.editableControls.add(this.patientLnameField);
        this.editableControls.add(this.patientSsnField);
        this.editableControls.add(this.patientPhoneField);
        this.editableControls.add(this.patientAddr1Field);
        this.editableControls.add(this.patientAddr2Field);
        this.editableControls.add(this.patientAddrZipcodeField);
    }

    /**
     * Populate map.
     */
    private void populateMap() {
        this.invalidDataLabel.setVisible(false);
        try {
            this.patientDetails.put("fname", this.patientFnameField.getText());
            this.patientDetails.put("lname", this.patientLnameField.getText());
            this.patientDetails.put("ssn", this.patientSsnField.getText());
            this.patientDetails.put("dob", this.patientDobPicker.getValue().toString());
            this.patientDetails.put("phone", this.patientPhoneField.getText());
            this.patientDetails.put("addressOne", this.patientAddr1Field.getText());
            this.patientDetails.put("addressTwo", this.patientAddr2Field.getText());
            this.patientDetails.put("zip", this.patientAddrZipcodeField.getText());
            this.patientDetails.put("state", this.patientAddrStateComboBox.getValue());
            this.patientDetails.put("gender", this.patientGenderComboBox.getValue());
        } catch (Exception e) {
            this.invalidDataLabel.setVisible(true);
        }

    }

    /**
     * Populate fields.
     */
    private void populateFields() {
        this.patientFnameField.textProperty().set(this.manager.getPatientFirstName());
        this.patientLnameField.textProperty().set(this.manager.getPatientLastName());
        this.patientSsnField.textProperty().set(this.manager.getPatientSsn());
        this.patientDobPicker.setValue(this.manager.getPatientDob().toLocalDate());
        this.patientAddr1Field.textProperty().set(this.manager.getPatientAddressOne());
        this.patientAddr2Field.textProperty().set(this.manager.getPatientAddressTwo());
        this.patientAddrZipcodeField.textProperty().set(this.manager.getPatientZip());
        this.patientAddrStateComboBox.getSelectionModel().select(this.manager.getPatientState());
        this.patientGenderComboBox.getSelectionModel().select(this.manager.getPatientGender());
        this.patientActiveChBox.selectedProperty().set(this.manager.getPatientIsActive());
        this.patientPhoneField.textProperty().set(this.manager.getPatientContactNumber());
    }
}

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

/**
 * The Class PatientInfoView.
 */
public class PatientInfoView {
    
    private ControllerManager manager;
    private Map<String, String> patientDetails = new HashMap<String, String>();
    private List<TextInputControl> editableControls;

    @FXML
    private TextField patientFnameField;

    @FXML
    private TextField patientLnameField;

    @FXML
    private TextField patientSsnField;

    @FXML
    private DatePicker patientDobPicker;

    @FXML
    private TextField patientPhoneField;

    @FXML
    private TextField patientAddr1Field;

    @FXML
    private TextField patientAddr2Field;

    @FXML
    private TextField patientAddrZipcodeField;

    @FXML
    private ComboBox<String> patientAddrStateComboBox;

    @FXML
    private CheckBox patientActiveChBox;

    @FXML
    private ComboBox<String> patientGenderComboBox;

    @FXML
    private Button submitButton;

    @FXML
    private Label currentUserField;

    @FXML
    private Button editButton;

    @FXML
    private Label invalidDataLabel;

    @FXML
    private Button backButton;
    
    @FXML
    void handleGoBack(ActionEvent event) {
        this.manager.changeToMainView((Stage) this.backButton.getScene().getWindow());
    }

    @FXML
    void enableEditing(ActionEvent event) {
        this.enableControls();
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        this.populateMap();
        if (this.manager.validateFields(this.patientDetails)) {
            if (this.manager.patientRegister(this.patientDetails, this.patientActiveChBox.isSelected())) {
                this.manager.changeToMainView((Stage) this.submitButton.getScene().getWindow());
            } else {
                this.invalidDataLabel.setVisible(true);
            }
            
        } else {
            this.invalidDataLabel.setVisible(true);
        }
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

    private void addEditableControls() {
        this.editableControls.add(this.patientFnameField);
        this.editableControls.add(this.patientLnameField);
        this.editableControls.add(this.patientSsnField);
        this.editableControls.add(this.patientPhoneField);
        this.editableControls.add(this.patientAddr1Field);
        this.editableControls.add(this.patientAddr2Field);
        this.editableControls.add(this.patientAddrZipcodeField);
    }

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

    private void populateFields() {
        this.patientFnameField.textProperty().set(this.manager.getPatientFirstName());
        this.patientLnameField.textProperty().set(this.manager.getPatientLastName());
        this.patientSsnField.textProperty().set(this.manager.getPatientSsn());
        this.patientDobPicker.setValue(this.manager.getPatientDob().toLocalDate());
        this.patientAddr1Field.textProperty().set(this.manager.getPatientAddressOne());
        this.patientAddr2Field.textProperty().set(this.manager.getPatientFirstName());
        this.patientAddrZipcodeField.textProperty().set(this.manager.getPatientZip());
        this.patientAddrStateComboBox.getSelectionModel().select(this.manager.getPatientState());
        this.patientGenderComboBox.getSelectionModel().select(this.manager.getPatientGender());
        this.patientActiveChBox.selectedProperty().set(this.manager.getPatientIsActive());
        this.patientPhoneField.textProperty().set(this.manager.getPatientContactNumber()); 
    }
}

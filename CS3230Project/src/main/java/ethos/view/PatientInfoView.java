package main.java.ethos.view;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;
import main.java.ethos.model.PageType;

public class PatientInfoView {

    private ControllerManager manager = new ControllerManager();
    private Map<String, String> patientDetails = new HashMap<String, String>();

    @FXML
    private TextField patientIdField;

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
    void handleSubmit(ActionEvent event) {
        this.manager.patientRegister(this.patientDetails, this.patientActiveChBox.isSelected());
        this.manager.changeToMainView((Stage) this.submitButton.getScene().getWindow());
    }

    public void initialize(ControllerManager manager) {
        this.manager = manager;
        this.manager.populateStatesComboBox(this.patientAddrStateComboBox);
        this.patientGenderComboBox.getItems().addAll("M", "F");
        this.currentUserField.textProperty().set(manager.getLoggedInName() + " (" + manager.getLoggedInUserName() + ")");
    }
    
    private void populateMap() {
        this.patientDetails.put("fname", this.patientFnameField.getText());
        this.patientDetails.put("lname", this.patientLnameField.getText());
        this.patientDetails.put("patientId", this.patientIdField.getText());
        this.patientDetails.put("ssn", this.patientSsnField.getText());
        this.patientDetails.put("dob", this.patientDobPicker.getValue().toString());
        this.patientDetails.put("phone", this.patientPhoneField.getText());
        this.patientDetails.put("addressOne", this.patientAddr1Field.getText());
        this.patientDetails.put("addressTwo", this.patientAddr2Field.getText());
        this.patientDetails.put("zip", this.patientAddrZipcodeField.getText());
        this.patientDetails.put("state", this.patientAddrStateComboBox.getValue());
        this.patientDetails.put("gender", this.patientGenderComboBox.getValue());
    }
}
